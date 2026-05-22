package github.dctime.dctimesignals.screen;

import github.dctime.dctimesignals.menu.NetlistEditorMenu;
import github.dctime.dctimesignals.payload.RunSimulationPayload;
import github.dctime.dctimesignals.payload.SaveNetlistPayload;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class NetlistEditorScreen extends AbstractContainerScreen<NetlistEditorMenu> {

    // ── 面板尺寸 ────────────────────────────────────────────────────────────
    private static final int GUI_W      = 300;
    private static final int GUI_H      = 220;
    private static final int HEADER_H   = 24;
    private static final int FOOTER_H   = 32;
    private static final int PAD        = 8;
    private static final int LINE_H     = 10;
    private static final int LINE_NUM_W = 24;
    private static final int SCROLLBAR_W = 4;

    private static final int EDIT_X = PAD;
    private static final int EDIT_Y = HEADER_H;
    private static final int EDIT_W = GUI_W - PAD * 2;
    private static final int EDIT_H = GUI_H - HEADER_H - FOOTER_H;

    // 文字可用寬度（去掉行號欄和捲軸）
    private static final int TEXT_W = EDIT_W - LINE_NUM_W - 4 - SCROLLBAR_W - 2;

    // ── 顏色 ────────────────────────────────────────────────────────────────
    private static final int C_BG           = 0xFF0F1117;
    private static final int C_HEADER_BG    = 0xFF1A1D27;
    private static final int C_EDIT_BG      = 0xFF080B10;
    private static final int C_BORDER       = 0xFF2A2D3A;
    private static final int C_ACCENT       = 0xFF00D4FF;
    private static final int C_TEXT         = 0xFFD0D8F0;
    private static final int C_CURSOR       = 0xFFFFFFFF;
    private static final int C_LINE_NUM     = 0xFF4A5270;
    private static final int C_CURRENT_LINE = 0x1500D4FF;
    private static final int C_SELECTION    = 0x5500D4FF;
    private static final int C_TITLE        = 0xFFE0E6FF;

    // ── 編輯器狀態 ──────────────────────────────────────────────────────────
    private final List<StringBuilder> lines = new ArrayList<>();

    private int cursorRow  = 0;
    private int cursorCol  = 0;
    private int selectRow  = -1;
    private int selectCol  = -1;
    private int scrollTop  = 0;
    private int scrollLeft = 0;   // 水平捲動（像素）

    private boolean isDragging = false;

    // 記住上下移動時的「理想 X 位置」，避免短行把 col 壓縮後回不去
    private int preferredCursorX = -1;

    private final net.minecraft.core.BlockPos blockPos;

    public NetlistEditorScreen(NetlistEditorMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth  = GUI_W;
        this.imageHeight = GUI_H;
        this.blockPos    = menu.blockPos;

        String[] split = menu.initialNetlist.split("\n", -1);
        for (String s : split) lines.add(new StringBuilder(s));
        if (lines.isEmpty()) lines.add(new StringBuilder());
    }

    private int visibleLines() { return EDIT_H / LINE_H; }

    // ── 選取工具 ────────────────────────────────────────────────────────────

    private boolean hasSelection() {
        return selectRow != -1 && !(selectRow == cursorRow && selectCol == cursorCol);
    }

    /** 回傳 [r1,c1,r2,c2]，保證 (r1,c1) <= (r2,c2) */
    private int[] normalizedSelection() {
        if (selectRow < cursorRow || (selectRow == cursorRow && selectCol <= cursorCol)) {
            return new int[]{ selectRow, selectCol, cursorRow, cursorCol };
        } else {
            return new int[]{ cursorRow, cursorCol, selectRow, selectCol };
        }
    }

    private void startSelection() {
        if (selectRow == -1) {
            selectRow = cursorRow;
            selectCol = cursorCol;
        }
    }

    private void clearSelection() {
        selectRow = -1;
        selectCol = -1;
    }

    private String getSelectedText() {
        if (!hasSelection()) return "";
        int[] s = normalizedSelection();
        int r1 = s[0], c1 = s[1], r2 = s[2], c2 = s[3];
        if (r1 == r2) return lines.get(r1).substring(c1, c2);
        StringBuilder sb = new StringBuilder();
        sb.append(lines.get(r1).substring(c1)).append('\n');
        for (int r = r1 + 1; r < r2; r++) sb.append(lines.get(r)).append('\n');
        sb.append(lines.get(r2).substring(0, c2));
        return sb.toString();
    }

    private void deleteSelection() {
        if (!hasSelection()) return;
        int[] s = normalizedSelection();
        int r1 = s[0], c1 = s[1], r2 = s[2], c2 = s[3];
        String after = lines.get(r2).substring(c2);
        for (int r = r2; r > r1; r--) lines.remove(r);
        lines.get(r1).delete(c1, lines.get(r1).length());
        lines.get(r1).append(after);
        cursorRow = r1;
        cursorCol = c1;
        clearSelection();
        resetPreferredX();
        clampScrollAfterEdit();
    }

    // ── init ────────────────────────────────────────────────────────────────

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = GUI_H + 100;
        this.titleLabelX     = GUI_W + 100;

        int ox = (width  - GUI_W) / 2;
        int oy = (height - GUI_H) / 2;

        this.addRenderableWidget(Button.builder(
                Component.literal("▶  Simulate"),
                btn -> onSimulate()
        ).bounds(ox + GUI_W - 100 - PAD, oy + GUI_H - FOOTER_H + 6, 100, 20).build());
    }

    private void onSimulate() {
        PacketDistributor.sendToServer(new RunSimulationPayload(blockPos, buildNetlist()));
    }

    private String buildNetlist() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            if (i > 0) sb.append('\n');
            sb.append(lines.get(i));
        }
        return sb.toString();
    }

    // ── 渲染 ────────────────────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partial) {
        renderBackground(gfx, mouseX, mouseY, partial   );
        super.render(gfx, mouseX, mouseY, partial);
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partial, int mouseX, int mouseY) {
        int ox = (width  - GUI_W) / 2;
        int oy = (height - GUI_H) / 2;
        drawPanel(gfx, ox, oy);
        drawHeader(gfx, ox, oy);
        drawEditor(gfx, ox, oy);
        drawFooter(gfx, ox, oy);
    }

    private void drawPanel(GuiGraphics gfx, int ox, int oy) {
        gfx.fill(ox - 1, oy - 1, ox + GUI_W + 1, oy + GUI_H + 1, 0xFF000000);
        gfx.fill(ox, oy, ox + GUI_W, oy + GUI_H, C_BG);
        gfx.fill(ox, oy, ox + 1, oy + GUI_H, C_BORDER);
        gfx.fill(ox + GUI_W - 1, oy, ox + GUI_W, oy + GUI_H, C_BORDER);
        gfx.fill(ox, oy + GUI_H - 1, ox + GUI_W, oy + GUI_H, C_BORDER);
    }

    private void drawHeader(GuiGraphics gfx, int ox, int oy) {
        gfx.fill(ox, oy, ox + GUI_W, oy + HEADER_H, C_HEADER_BG);
        gfx.fill(ox, oy + HEADER_H - 1, ox + GUI_W, oy + HEADER_H, 0xFF2A3550);
        gfx.fill(ox, oy, ox + 3, oy + HEADER_H, C_ACCENT);
        gfx.drawString(font, "NETLIST EDITOR", ox + 10, oy + 7, C_TITLE, false);
        String info = lines.size() + " lines";
        gfx.drawString(font, info, ox + GUI_W - font.width(info) - PAD, oy + 7, C_LINE_NUM, false);
    }

    private void drawEditor(GuiGraphics gfx, int ox, int oy) {
        int ex = ox + EDIT_X;
        int ey = oy + EDIT_Y;
        int textStartX = ex + LINE_NUM_W + 4;

        gfx.fill(ex, ey, ex + EDIT_W, ey + EDIT_H, C_EDIT_BG);
        gfx.fill(ex, ey, ex + LINE_NUM_W, ey + EDIT_H, 0xFF0D1018);
        gfx.fill(ex + LINE_NUM_W, ey, ex + LINE_NUM_W + 1, ey + EDIT_H, 0xFF1E2235);

        // scissor 裁剪到整個編輯區
        gfx.enableScissor(ex + LINE_NUM_W + 1, ey, ex + EDIT_W - SCROLLBAR_W, ey + EDIT_H);

        int visible = visibleLines();
        boolean cursorVisible = (System.currentTimeMillis() / 500) % 2 == 0;
        int[] sel = hasSelection() ? normalizedSelection() : null;

        for (int i = 0; i < visible; i++) {
            int lineIdx = scrollTop + i;
            if (lineIdx >= lines.size()) break;
            String text = lines.get(lineIdx).toString();
            int ly = ey + i * LINE_H;

            // 當前行高亮
            if (lineIdx == cursorRow) {
                gfx.fill(ex + LINE_NUM_W + 1, ly, ex + EDIT_W - SCROLLBAR_W, ly + LINE_H, C_CURRENT_LINE);
            }

            // 選取高亮
            if (sel != null) {
                int r1 = sel[0], c1 = sel[1], r2 = sel[2], c2 = sel[3];
                if (lineIdx >= r1 && lineIdx <= r2) {
                    int selX0, selX1;
                    if (r1 == r2) {
                        selX0 = textStartX + font.width(text.substring(0, c1)) - scrollLeft;
                        selX1 = textStartX + font.width(text.substring(0, c2)) - scrollLeft;
                    } else if (lineIdx == r1) {
                        selX0 = textStartX + font.width(text.substring(0, c1)) - scrollLeft;
                        selX1 = textStartX + font.width(text) - scrollLeft + 4;
                    } else if (lineIdx == r2) {
                        selX0 = textStartX - scrollLeft;
                        selX1 = textStartX + font.width(text.substring(0, c2)) - scrollLeft;
                    } else {
                        selX0 = textStartX - scrollLeft;
                        selX1 = textStartX + font.width(text) - scrollLeft + 4;
                    }
                    if (selX1 > selX0) gfx.fill(selX0, ly, selX1, ly + LINE_H, C_SELECTION);
                }
            }

            // 文字（套用水平捲動）
            gfx.drawString(font, text, textStartX - scrollLeft, ly + 1, C_TEXT, false);

            // 游標
            if (lineIdx == cursorRow && cursorVisible) {
                int safeCol = Math.min(cursorCol, text.length());
                int curX = textStartX + font.width(text.substring(0, safeCol)) - scrollLeft;
                gfx.fill(curX, ly + 1, curX + 1, ly + LINE_H - 1, C_CURSOR);
            }
        }

        gfx.disableScissor();

        // 行號（不受水平捲動影響，在 scissor 外畫）
        for (int i = 0; i < visible; i++) {
            int lineIdx = scrollTop + i;
            if (lineIdx >= lines.size()) break;
            int ly = ey + i * LINE_H;
            String lineNum = String.valueOf(lineIdx + 1);
            gfx.drawString(font, lineNum,
                    ex + LINE_NUM_W - font.width(lineNum) - 3, ly + 1, C_LINE_NUM, false);
        }

        drawScrollbars(gfx, ex, ey);
    }

    private void drawScrollbars(GuiGraphics gfx, int ex, int ey) {
        // 捲軸不顯示
    }

    /** 計算所有行中最寬的寬度（px） */
    private int maxLineWidth() {
        int max = 0;
        for (StringBuilder line : lines) max = Math.max(max, font.width(line.toString()));
        return max;
    }

    private void drawFooter(GuiGraphics gfx, int ox, int oy) {
        int fy = oy + GUI_H - FOOTER_H;
        gfx.fill(ox, fy, ox + GUI_W, fy + 1, 0xFF2A3550);
        gfx.fill(ox, fy + 1, ox + GUI_W, oy + GUI_H, C_HEADER_BG);
        String pos = "Ln " + (cursorRow + 1) + "  Col " + (cursorCol + 1);
        if (hasSelection()) {
            String sel = getSelectedText();
            int chars = sel.length() - (int) sel.chars().filter(c -> c == '\n').count();
            pos += "  [" + chars + " selected]";
        }
        gfx.drawString(font, pos, ox + PAD, fy + (FOOTER_H - font.lineHeight) / 2, C_LINE_NUM, false);
    }

    // ── 鍵盤 ────────────────────────────────────────────────────────────────

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean ctrl  = (modifiers & GLFW.GLFW_MOD_CONTROL) != 0;
        boolean shift = (modifiers & GLFW.GLFW_MOD_SHIFT)   != 0;

        // Ctrl 組合鍵
        if (ctrl) {
            switch (keyCode) {
                case GLFW.GLFW_KEY_C -> {
                    minecraft.keyboardHandler.setClipboard(hasSelection() ? getSelectedText() : currentLine().toString());
                    return true;
                }
                case GLFW.GLFW_KEY_X -> {
                    if (hasSelection()) { minecraft.keyboardHandler.setClipboard(getSelectedText()); deleteSelection(); }
                    else { minecraft.keyboardHandler.setClipboard(currentLine().toString()); deleteLine(); }
                    return true;
                }
                case GLFW.GLFW_KEY_V -> {
                    if (hasSelection()) deleteSelection();
                    String cb = minecraft.keyboardHandler.getClipboard();
                    if (cb != null && !cb.isEmpty()) pasteText(cb);
                    return true;
                }
                case GLFW.GLFW_KEY_A -> {
                    selectRow = 0; selectCol = 0;
                    cursorRow = lines.size() - 1;
                    cursorCol = lines.get(cursorRow).length();
                    ensureCursorVisible();
                    return true;
                }
            }
        }

        // 方向鍵
        switch (keyCode) {
            case GLFW.GLFW_KEY_LEFT -> {
                if (shift) startSelection(); else if (hasSelection()) { jumpToSelectionStart(); return true; } else clearSelection();
                moveCursorLeft();
                resetPreferredX();
                return true;
            }
            case GLFW.GLFW_KEY_RIGHT -> {
                if (shift) startSelection(); else if (hasSelection()) { jumpToSelectionEnd(); return true; } else clearSelection();
                moveCursorRight();
                resetPreferredX();
                return true;
            }
            case GLFW.GLFW_KEY_UP -> {
                if (shift) startSelection(); else clearSelection();
                moveCursorUp();
                return true;
            }
            case GLFW.GLFW_KEY_DOWN -> {
                if (shift) startSelection(); else clearSelection();
                moveCursorDown();
                return true;
            }
            case GLFW.GLFW_KEY_HOME -> {
                if (shift) startSelection(); else clearSelection();
                cursorCol = 0; resetPreferredX(); ensureCursorVisible();
                return true;
            }
            case GLFW.GLFW_KEY_END -> {
                if (shift) startSelection(); else clearSelection();
                cursorCol = currentLine().length(); resetPreferredX(); ensureCursorVisible();
                return true;
            }
            case GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER -> {
                if (hasSelection()) deleteSelection();
                insertNewline();
                return true;
            }
            case GLFW.GLFW_KEY_BACKSPACE -> {
                if (hasSelection()) deleteSelection(); else backspace();
                return true;
            }
            case GLFW.GLFW_KEY_DELETE -> {
                if (hasSelection()) deleteSelection(); else delete();
                return true;
            }
            case GLFW.GLFW_KEY_ESCAPE -> { return super.keyPressed(keyCode, scanCode, modifiers); }
        }

        // 吃掉所有其他鍵（防止 E、WASD 等觸發遊戲行為）
        return true;
    }

    @Override
    public boolean charTyped(char c, int modifiers) {
        if (c == '\t') {
            if (hasSelection()) deleteSelection();
            for (int i = 0; i < 4; i++) insertChar(' ');
            return true;
        }
        if (c >= 32) {
            if (hasSelection()) deleteSelection();
            insertChar(c);
            return true;
        }
        return true;  // 吃掉，不讓 super 處理
    }

    // ── 滑鼠 ────────────────────────────────────────────────────────────────

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double deltaX, double deltaY) {
        // 有水平 delta 就水平捲，否則垂直捲
        if (deltaX != 0) {
            scrollLeft -= (int)(deltaX * 10);
            clampScroll();
        } else {
            scrollTop -= (int) deltaY;
            clampScroll();
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int ox = (width - GUI_W) / 2;
        int oy = (height - GUI_H) / 2;
        int ex = ox + EDIT_X;
        int ey = oy + EDIT_Y;

        if (mouseX >= ex + LINE_NUM_W && mouseX < ex + EDIT_W - SCROLLBAR_W
                && mouseY >= ey && mouseY < ey + EDIT_H) {
            positionCursorAt(mouseX, mouseY);
            clearSelection();
            isDragging = true;
            resetPreferredX();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button,
                                double dragX, double dragY) {
        if (isDragging) {
            if (!hasSelection()) { selectRow = cursorRow; selectCol = cursorCol; }
            positionCursorAt(mouseX, mouseY);
            resetPreferredX();
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private void positionCursorAt(double mouseX, double mouseY) {
        int ox = (width - GUI_W) / 2;
        int oy = (height - GUI_H) / 2;
        int ex = ox + EDIT_X;
        int ey = oy + EDIT_Y;

        int lineIdx = scrollTop + (int)((mouseY - ey) / LINE_H);
        lineIdx = Math.max(0, Math.min(lineIdx, lines.size() - 1));
        cursorRow = lineIdx;

        String text = lines.get(cursorRow).toString();
        int relX = (int)(mouseX - ex - LINE_NUM_W - 4) + scrollLeft;
        cursorCol = 0;
        for (int i = 1; i <= text.length(); i++) {
            if (font.width(text.substring(0, i)) > relX) break;
            cursorCol = i;
        }
        ensureCursorVisible();
    }

    // ── 編輯器操作 ──────────────────────────────────────────────────────────

    private StringBuilder currentLine() { return lines.get(cursorRow); }

    private void insertChar(char c) {
        currentLine().insert(cursorCol, c);
        cursorCol++;
        resetPreferredX();
        ensureCursorVisible();
    }

    private void insertNewline() {
        String after = currentLine().substring(cursorCol);
        currentLine().delete(cursorCol, currentLine().length());
        lines.add(cursorRow + 1, new StringBuilder(after));
        cursorRow++;
        cursorCol = 0;
        resetPreferredX();
        ensureCursorVisible();
    }

    private void backspace() {
        if (cursorCol > 0) {
            currentLine().deleteCharAt(cursorCol - 1);
            cursorCol--;
        } else if (cursorRow > 0) {
            String current = currentLine().toString();
            lines.remove(cursorRow);
            cursorRow--;
            cursorCol = currentLine().length();
            currentLine().append(current);
        }
        resetPreferredX();
        clampScrollAfterEdit();
    }

    private void delete() {
        if (cursorCol < currentLine().length()) {
            currentLine().deleteCharAt(cursorCol);
        } else if (cursorRow < lines.size() - 1) {
            String next = lines.get(cursorRow + 1).toString();
            lines.remove(cursorRow + 1);
            currentLine().append(next);
        }
        resetPreferredX();
        clampScrollAfterEdit();
    }

    private void deleteLine() {
        if (lines.size() == 1) { lines.get(0).setLength(0); cursorCol = 0; }
        else {
            lines.remove(cursorRow);
            if (cursorRow >= lines.size()) cursorRow = lines.size() - 1;
            cursorCol = Math.min(cursorCol, currentLine().length());
        }
        resetPreferredX();
        clampScrollAfterEdit();
    }

    private void pasteText(String text) {
        String[] parts = text.split("\n", -1);
        if (parts.length == 1) {
            for (char c : parts[0].toCharArray()) insertChar(c);
        } else {
            String after = currentLine().substring(cursorCol);
            currentLine().delete(cursorCol, currentLine().length());
            for (char c : parts[0].toCharArray()) currentLine().append(c);
            for (int i = 1; i < parts.length - 1; i++) {
                cursorRow++;
                lines.add(cursorRow, new StringBuilder(parts[i]));
            }
            cursorRow++;
            lines.add(cursorRow, new StringBuilder(parts[parts.length - 1] + after));
            cursorCol = parts[parts.length - 1].length();
            resetPreferredX();
            ensureCursorVisible();
        }
    }

    // ── 游標移動 ────────────────────────────────────────────────────────────

    /** 沒有選取時按左跳到選取起點 */
    private void jumpToSelectionStart() {
        int[] s = normalizedSelection();
        cursorRow = s[0]; cursorCol = s[1];
        clearSelection();
        resetPreferredX();
        ensureCursorVisible();
    }

    /** 沒有選取時按右跳到選取終點 */
    private void jumpToSelectionEnd() {
        int[] s = normalizedSelection();
        cursorRow = s[2]; cursorCol = s[3];
        clearSelection();
        resetPreferredX();
        ensureCursorVisible();
    }

    private void moveCursorLeft() {
        if (cursorCol > 0) cursorCol--;
        else if (cursorRow > 0) { cursorRow--; cursorCol = currentLine().length(); }
        ensureCursorVisible();
    }

    private void moveCursorRight() {
        if (cursorCol < currentLine().length()) cursorCol++;
        else if (cursorRow < lines.size() - 1) { cursorRow++; cursorCol = 0; }
        ensureCursorVisible();
    }

    private void moveCursorUp() {
        if (cursorRow > 0) {
            cursorRow--;
            cursorCol = colFromPreferredX(cursorRow);
        }
        ensureCursorVisible();
    }

    private void moveCursorDown() {
        if (cursorRow < lines.size() - 1) {
            cursorRow++;
            cursorCol = colFromPreferredX(cursorRow);
        }
        ensureCursorVisible();
    }

    /** 重設「理想 X」為當前游標的實際 X */
    private void resetPreferredX() {
        String text = currentLine().toString();
        int safeCol = Math.min(cursorCol, text.length());
        preferredCursorX = font.width(text.substring(0, safeCol));
    }

    /** 根據「理想 X」算出指定行的最佳 col */
    private int colFromPreferredX(int row) {
        if (preferredCursorX < 0) resetPreferredX();
        String text = lines.get(row).toString();
        int col = 0;
        for (int i = 1; i <= text.length(); i++) {
            if (font.width(text.substring(0, i)) > preferredCursorX) break;
            col = i;
        }
        return col;
    }

    // ── 捲動 ────────────────────────────────────────────────────────────────

    private void ensureCursorVisible() {
        // 垂直
        if (cursorRow < scrollTop) scrollTop = cursorRow;
        if (cursorRow >= scrollTop + visibleLines()) scrollTop = cursorRow - visibleLines() + 1;

        // 水平
        String text = currentLine().toString();
        int safeCol = Math.min(cursorCol, text.length());
        int cursorX = font.width(text.substring(0, safeCol));
        if (cursorX - scrollLeft > TEXT_W) scrollLeft = cursorX - TEXT_W;
        if (cursorX - scrollLeft < 0)      scrollLeft = cursorX;

        clampScroll();
    }

    private void clampScroll() {
        scrollTop  = Math.max(0, Math.min(scrollTop,  Math.max(0, lines.size() - visibleLines())));
        scrollLeft = Math.max(0, Math.min(scrollLeft, Math.max(0, maxLineWidth() - TEXT_W)));
    }

    /** 刪除文字後呼叫，確保 scrollLeft 不會超出現有內容 */
    private void clampScrollAfterEdit() {
        ensureCursorVisible();  // 會順帶 clampScroll
    }

    // ── 關閉時儲存 ──────────────────────────────────────────────────────────

    @Override
    public void onClose() {
        PacketDistributor.sendToServer(new SaveNetlistPayload(blockPos, buildNetlist()));
        super.onClose();
    }
}
