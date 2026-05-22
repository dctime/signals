package github.dctime.dctimesignals.payload;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.block.NetlistEditorBlockEntity;
import github.dctime.dctimesignals.lib.NgSpiceRunner;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RunSimulationPayload(BlockPos pos, String netlist) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<RunSimulationPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "run_simulation"));

    public static final StreamCodec<ByteBuf, RunSimulationPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            RunSimulationPayload::pos,
            ByteBufCodecs.STRING_UTF8,
            RunSimulationPayload::netlist,
            RunSimulationPayload::new
    );

    public static void handleDataOnMain(final RunSimulationPayload payload, final IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) return;

//        context.enqueueWork(() -> {
            // 確認 BlockEntity 存在
            if (!(player.level().getBlockEntity(payload.pos) instanceof NetlistEditorBlockEntity be)) return;
            if (!be.hasDisc()) {
                player.displayClientMessage(Component.literal("請先插入訊號碟片"), true);
                return;
            }

            // 儲存 netlist 到 BlockEntity
            be.setNetlist(payload.netlist);

            // 通知玩家開始模擬
            player.displayClientMessage(Component.literal("模擬中..."), true);

            // 非同步執行 NgSpice
            NgSpiceRunner.runSimulationAsync(payload.netlist)
                    .thenAcceptAsync(result -> {
                        if (result == null || result.times.isEmpty()) {
                            player.displayClientMessage(
                                    Component.literal("§c[模擬失敗]§r 無法從輸出中解析任何數據點！"), true);
                            return;
                        }

                        try {
                            // 3. 轉成 double[] 陣列以符合你的 BlockEntity 碟片寫入格式
                            double[] xData = result.times.stream().mapToDouble(Double::doubleValue).toArray();
                            double[] yData = result.values.stream().mapToDouble(Double::doubleValue).toArray();

                            // 4. 覆寫碟片
                            be.writeToDisc("v(out)", xData, yData);

                            player.displayClientMessage(
                                    Component.literal("§a[模擬完成]§r 已成功寫入 §e" + xData.length + "§r 個資料點！"), true);

                        } catch (Exception e) {
                            player.displayClientMessage(
                                    Component.literal("§c[系統錯誤]§r 碟片寫入失敗：" + e.getMessage()), true);
                        }
                    })
                    .exceptionally(ex -> {
                        player.getServer().execute(() ->
                                player.displayClientMessage(
                                        Component.literal("NgSpice 錯誤：" + ex.getCause().getMessage()), false)
                        );
                        return null;
                    });
//        });

    }

    // ── 解析 NgSpice 輸出（兩欄格式）───────────────────────────────────────

    private static double[][] parseOutput(String output) {
        java.util.List<Double> times  = new java.util.ArrayList<>();
        java.util.List<Double> values = new java.util.ArrayList<>();
        boolean headerSkipped = false;

        for (String raw : output.split("\n")) {
            String line = raw.trim();
            if (line.isEmpty()) continue;

            if (!headerSkipped) {
                if (!Character.isDigit(line.charAt(0)) && line.charAt(0) != '-') {
                    continue;
                }
                headerSkipped = true;
            }

            String[] parts = line.split("[\\s,]+");
            if (parts.length < 2) continue;

            try {
                times.add(Double.parseDouble(parts[0]));
                values.add(Double.parseDouble(parts[1]));
            } catch (NumberFormatException ignored) {}
        }

        if (times.isEmpty()) return null;

        double[] t = times.stream().mapToDouble(Double::doubleValue).toArray();
        double[] v = values.stream().mapToDouble(Double::doubleValue).toArray();
        return new double[][]{ t, v };
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


}
