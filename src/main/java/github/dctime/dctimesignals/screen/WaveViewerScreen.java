package github.dctime.dctimesignals.screen;

import github.dctime.dctimesignals.item.SignalDataItem;
import github.dctime.dctimesignals.menu.WaveViewerMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class WaveViewerScreen extends AbstractContainerScreen<WaveViewerMenu> {
    // ── 面板尺寸 ────────────────────────────────────────────────────────────
    private static final int GUI_W = 280;
    private static final int GUI_H = 200;

    // ── 波形繪製區 ──────────────────────────────────────────────────────────
    private static final int HEADER_H = 28;
    private static final int FOOTER_H = 24;
    private static final int PAD      = 12;

    private static final int PLOT_X = PAD;
    private static final int PLOT_Y = HEADER_H;
    private static final int PLOT_W = GUI_W - PAD * 2;
    private static final int PLOT_H = GUI_H - HEADER_H - FOOTER_H;

    // ── 顏色 ────────────────────────────────────────────────────────────────
    private static final int C_PANEL_BG    = 0xFF0F1117;
    private static final int C_PANEL_BORDER= 0xFF2A2D3A;
    private static final int C_HEADER_BG   = 0xFF1A1D27;
    private static final int C_PLOT_BG     = 0xFF080B10;
    private static final int C_GRID        = 0x18FFFFFF;
    private static final int C_AXIS        = 0x40FFFFFF;
    private static final int C_ZERO_LINE   = 0x5500D4FF;
    private static final int C_WAVE        = 0xFF00D4FF;
    private static final int C_WAVE_GLOW   = 0x3000D4FF;
    private static final int C_TITLE       = 0xFFE0E6FF;
    private static final int C_LABEL       = 0xFF00D4FF;
    private static final int C_INFO        = 0xFF8892AA;
    private static final int C_NO_DATA     = 0xFF444466;

    public WaveViewerScreen(WaveViewerMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth  = GUI_W;
        this.imageHeight = GUI_H;
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = GUI_H + 100;
        this.titleLabelX     = GUI_W + 100;
    }

    // ── 從 slot 讀取最新數據（每幀都讀，確保即時更新）─────────────────────
    private ItemStack getSignalStack() {
        return this.menu.getSlot(0).getItem();
    }

    // ── 渲染 ────────────────────────────────────────────────────────────────

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partial) {
        renderBackground(gfx, mouseX, mouseY, partial);
        super.render(gfx, mouseX, mouseY, partial);
    }

    @Override
    protected void renderBg(GuiGraphics gfx, float partial, int mouseX, int mouseY) {
        int ox = (width  - GUI_W) / 2;
        int oy = (height - GUI_H) / 2;

        ItemStack stack  = getSignalStack();
        boolean hasData  = SignalDataItem.hasData(stack);
        double[] times   = hasData ? SignalDataItem.getTimes(stack)  : new double[0];
        double[] values  = hasData ? SignalDataItem.getValues(stack) : new double[0];
        String label     = hasData ? SignalDataItem.getLabel(stack)  : "---";

        drawPanel(gfx, ox, oy);
        drawHeader(gfx, ox, oy, label);
        drawPlotArea(gfx, ox, oy, values);
        drawWaveform(gfx, ox, oy, times, values);
        drawFooter(gfx, ox, oy, times, values);

        // Slot 格子背景（Slot 在 leftPos+80, topPos+30）
//        gfx.fill(leftPos + 79, topPos + 29,
//                leftPos + 97, topPos + 47, 0xFF555555);
    }

    // ── 面板 ────────────────────────────────────────────────────────────────

    private void drawPanel(GuiGraphics gfx, int ox, int oy) {
        gfx.fill(ox - 1, oy - 1, ox + GUI_W + 1, oy + GUI_H + 1, 0xFF000000);
        gfx.fill(ox, oy, ox + GUI_W, oy + GUI_H, C_PANEL_BG);
        gfx.fill(ox, oy, ox + 1, oy + GUI_H, C_PANEL_BORDER);
        gfx.fill(ox + GUI_W - 1, oy, ox + GUI_W, oy + GUI_H, C_PANEL_BORDER);
        gfx.fill(ox, oy + GUI_H - 1, ox + GUI_W, oy + GUI_H, C_PANEL_BORDER);
    }

    // ── 標題列 ──────────────────────────────────────────────────────────────

    private void drawHeader(GuiGraphics gfx, int ox, int oy, String label) {
        gfx.fill(ox, oy, ox + GUI_W, oy + HEADER_H, C_HEADER_BG);
        gfx.fill(ox, oy + HEADER_H - 1, ox + GUI_W, oy + HEADER_H, 0xFF2A3550);
        gfx.fill(ox, oy, ox + 3, oy + HEADER_H, C_LABEL);
        gfx.drawString(font, "WAVE VIEWER", ox + 10, oy + 6, C_TITLE, false);

        String sig = "SIG: " + label;
        int sw = font.width(sig);
        gfx.fill(ox + GUI_W - sw - 14, oy + 9, ox + GUI_W - sw - 11, oy + 12, C_LABEL);
        gfx.drawString(font, sig, ox + GUI_W - sw - 10, oy + 6, C_LABEL, false);
    }

    // ── 波形區背景 ──────────────────────────────────────────────────────────

    private void drawPlotArea(GuiGraphics gfx, int ox, int oy, double[] values) {
        int px = ox + PLOT_X;
        int py = oy + PLOT_Y;

        gfx.fill(px, py, px + PLOT_W, py + PLOT_H, C_PLOT_BG);

        // 格線
        for (int c = 1; c < 5; c++)
            gfx.fill(px + PLOT_W * c / 5, py, px + PLOT_W * c / 5 + 1, py + PLOT_H, C_GRID);
        for (int r = 1; r < 5; r++)
            gfx.fill(px, py + PLOT_H * r / 5, px + PLOT_W, py + PLOT_H * r / 5 + 1, C_GRID);

        // 邊框
        gfx.fill(px, py, px + PLOT_W, py + 1, C_AXIS);
        gfx.fill(px, py + PLOT_H - 1, px + PLOT_W, py + PLOT_H, C_AXIS);
        gfx.fill(px, py, px + 1, py + PLOT_H, C_AXIS);
        gfx.fill(px + PLOT_W - 1, py, px + PLOT_W, py + PLOT_H, C_AXIS);

        // 零線（虛線）
        if (values.length > 0) {
            double vMin = values[0], vMax = values[0];
            for (double v : values) { if (v < vMin) vMin = v; if (v > vMax) vMax = v; }
            double pad = (vMax - vMin) * 0.1;
            double dispMin = vMin - pad, dispMax = vMax + pad;
            if (Math.abs(dispMax - dispMin) > 1e-12 && 0 >= dispMin && 0 <= dispMax) {
                int zy = py + PLOT_H - (int)((0 - dispMin) / (dispMax - dispMin) * PLOT_H);
                zy = clamp(zy, py + 1, py + PLOT_H - 2);
                for (int x = px + 1; x < px + PLOT_W - 1; x += 4)
                    gfx.fill(x, zy, Math.min(x + 2, px + PLOT_W - 1), zy + 1, C_ZERO_LINE);
            }
        }
    }

    // ── 波形折線 ────────────────────────────────────────────────────────────

    private void drawWaveform(GuiGraphics gfx, int ox, int oy,
                              double[] times, double[] values) {
        int px = ox + PLOT_X;
        int py = oy + PLOT_Y;

        if (values.length < 2) {
            gfx.drawCenteredString(font, "No signal data",
                    ox + PLOT_X + PLOT_W / 2, oy + PLOT_Y + PLOT_H / 2 - 4, C_NO_DATA);
            return;
        }

        double vMin = values[0], vMax = values[0];
        for (double v : values) { if (v < vMin) vMin = v; if (v > vMax) vMax = v; }
        if (Math.abs(vMax - vMin) < 1e-12) { vMin -= 1; vMax += 1; }

        double pad = (vMax - vMin) * 0.1;
        double dispMin = vMin - pad, dispMax = vMax + pad, dispRange = dispMax - dispMin;
        int n = values.length;

        for (int i = 0; i < n - 1; i++) {
            int x0 = px + (int)((double) i      / (n-1) * (PLOT_W-2)) + 1;
            int x1 = px + (int)((double)(i + 1) / (n-1) * (PLOT_W-2)) + 1;
            int y0 = py + PLOT_H - 1 - (int)((values[i]   - dispMin) / dispRange * (PLOT_H-2));
            int y1 = py + PLOT_H - 1 - (int)((values[i+1] - dispMin) / dispRange * (PLOT_H-2));
            y0 = clamp(y0, py + 1, py + PLOT_H - 2);
            y1 = clamp(y1, py + 1, py + PLOT_H - 2);

            drawLine(gfx, x0, y0 - 1, x1, y1 - 1, C_WAVE_GLOW);
            drawLine(gfx, x0, y0 + 1, x1, y1 + 1, C_WAVE_GLOW);
            drawLine(gfx, x0, y0, x1, y1, C_WAVE);
        }
    }

    // ── 底部資訊 ────────────────────────────────────────────────────────────

    private void drawFooter(GuiGraphics gfx, int ox, int oy,
                            double[] times, double[] values) {
        int fy = oy + GUI_H - FOOTER_H;
        gfx.fill(ox, fy, ox + GUI_W, fy + 1, 0xFF2A3550);
        gfx.fill(ox, fy + 1, ox + GUI_W, oy + GUI_H, C_HEADER_BG);

        if (values.length == 0) return;

        double vMin = values[0], vMax = values[0];
        for (double v : values) { if (v < vMin) vMin = v; if (v > vMax) vMax = v; }
        double tEnd = times.length > 0 ? times[times.length - 1] : 0;

        String info = String.format(
                "MIN  %.3fV    MAX  %.3fV    TIME  %.2fms    PTS  %d",
                vMin, vMax, tEnd * 1000.0, values.length);
        gfx.drawString(font, info,
                ox + (GUI_W - font.width(info)) / 2,
                fy + (FOOTER_H - font.lineHeight) / 2,
                C_INFO, false);
    }

    // ── 工具 ────────────────────────────────────────────────────────────────

    private static void drawLine(GuiGraphics gfx, int x0, int y0,
                                 int x1, int y1, int color) {
        int dx = Math.abs(x1-x0), sx = x0 < x1 ? 1 : -1;
        int dy = Math.abs(y1-y0), sy = y0 < y1 ? 1 : -1;
        int err = dx - dy;
        while (true) {
            gfx.fill(x0, y0, x0+1, y0+1, color);
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) { err -= dy; x0 += sx; }
            if (e2 <  dx) { err += dx; y0 += sy; }
        }
    }

    private static int clamp(int v, int min, int max) {
        return Math.max(min, Math.min(max, v));
    }
}
