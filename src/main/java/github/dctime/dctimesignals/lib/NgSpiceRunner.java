package github.dctime.dctimesignals.lib;

import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NgSpiceRunner {

    private static Path extractedExePath = null;
    // 用執行緒池避免卡主執行緒
    private static final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
        Thread t = new Thread(r, "ngspice-worker");
        t.setDaemon(true);
        return t;
    });

    public static void extractNatives() throws IOException {
        System.out.println("Extracting Spice Exe to mods folder");
        Path nativesDir = FMLPaths.GAMEDIR.get().resolve("signals-natives");
        Files.createDirectories(nativesDir);

        extractResource("/native/windows/ngspice_con.exe", nativesDir.resolve("ngspice_con.exe"));
        extractResource("/native/windows/libomp140.x86_64.dll", nativesDir.resolve("libomp140.x86_64.dll"));

        extractedExePath = nativesDir.resolve("ngspice_con.exe");
        extractedExePath.toFile().setExecutable(true);
    }

    private static void extractResource(String resourcePath, Path dest) throws IOException {
        if (Files.exists(dest)) return;
        try (InputStream is = NgSpiceRunner.class.getResourceAsStream(resourcePath)) {
            if (is == null) throw new IOException("Resource not found: " + resourcePath);
            Files.copy(is, dest, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * 非同步執行模擬，回傳 CompletableFuture
     */
    public static CompletableFuture<ParseResult> runSimulationAsync(String netlistContent) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return runSimulation(netlistContent);
            } catch (Exception e) {
                throw new RuntimeException("ngspice simulation failed", e);
            }
        }, executor);
    }

    private static String injectWrdataCommand(String originalNetlist, String targetDataPath) {
        // 💡 自動幫玩家的網表加上 wrdata 指令，這樣玩家在遊戲內只要寫 ac 或 dc 模擬就好，不用管輸出！
        // 我們把它安插在 .control 區塊的 quit 之前
        String command = "\n  wrdata " + targetDataPath + " v(out)\n";

        if (originalNetlist.contains("quit")) {
            return originalNetlist.replace("quit", command + "  quit");
        } else if (originalNetlist.contains(".control")) {
            // 如果有 .control 但沒寫 quit，塞在 .endc 前面
            return originalNetlist.replace(".endc", command + ".endc");
        } else {
            // 如果玩家連 .control 都沒寫，我們乾脆幫他包一個
            return originalNetlist + "\n.control\n" + command + ".endc\n";
        }
    }

    private static ParseResult runSimulation(String netlistContent) throws IOException, InterruptedException {
        Path netlistFile = Files.createTempFile("ngspice_", ".cir");
        Path dataOutputFile = Files.createTempFile("ngspice_out_", ".txt");

        System.out.println("Java 預期數據要寫入的位置: " + dataOutputFile.toAbsolutePath());

        try {
            String targetDataPath = dataOutputFile.toString().replace("\\", "/");

            // 💡 修改：不要用 replace 了，直接用剛剛寫的組合拳方法強制植入正確的輸出路徑！
            String updatedContent = injectWrdataCommand(netlistContent, targetDataPath);

            // 除錯用：印出最終餵給 ngspice 的網表到底長怎樣
            System.out.println("--- 最終網表內容 ---");
            System.out.println(updatedContent);
            System.out.println("--------------------");

            Files.writeString(netlistFile, updatedContent);

            ProcessBuilder pb = new ProcessBuilder(
                    extractedExePath.toString(),
                    "-b",
                    netlistFile.toString()
            );
            pb.redirectErrorStream(true);
            pb.directory(extractedExePath.getParent().toFile());

            Process process = pb.start();
            String logOutput = new String(process.getInputStream().readAllBytes());
            int exitCode = process.waitFor();

            if (exitCode != 0) {
                throw new RuntimeException("ngspice exited with code " + exitCode + ": " + logOutput);
            }

            if (!Files.exists(dataOutputFile) || Files.size(dataOutputFile) == 0) {
                throw new RuntimeException("模擬結束，但未生成任何數據！後台日誌：\n" + logOutput);
            }

            return parseWrdata(dataOutputFile);

        } finally {
            // 除錯階段先不刪除，方便你去 C:\...\Temp 檢查有沒有檔案
             Files.deleteIfExists(netlistFile);
             Files.deleteIfExists(dataOutputFile);
        }
    }

    // -----------------------------------------------------------------------
// 解析 wrdata 產生的兩欄文字檔（修正 Bug 版）
// -----------------------------------------------------------------------
    private static ParseResult parseWrdata(Path file) throws IOException {
        ParseResult result = new ParseResult();

        for (String raw : Files.readAllLines(file)) {
            String line = raw.trim();
            if (line.isEmpty()) continue;

            // 分割（支援 tab 或空格）
            String[] parts = line.split("[\\s,]+");

            // 💡 核心修正：wrdata 的數據行【必定剛好是兩欄數字】
            // 如果遇到了非兩欄的文字（像是標題、日誌等），直接跳過，不再依賴不穩定的 headerSkipped 旗標
            if (parts.length != 2) continue;

            try {
                // 如果 parts[0] 或 parts[1] 不是合法的數字（例如裡面有英文字母標題），
                // Double.parseDouble 會直接拋出 NumberFormatException，並安全跳到 catch
                double t = Double.parseDouble(parts[0]);
                double v = Double.parseDouble(parts[1]);
                result.times.add(t);
                result.values.add(v);
            } catch (NumberFormatException ignored) {
                // 這裡會默默吞掉所有無法解析成數字的雜質文字行，安全過濾
            }
        }
        return result;
    }

    public static class ParseResult {
        public final List<Double> times  = new ArrayList<>();
        public final List<Double> values = new ArrayList<>();
    }

    public static void shutdown() {
        executor.shutdownNow();
    }
}