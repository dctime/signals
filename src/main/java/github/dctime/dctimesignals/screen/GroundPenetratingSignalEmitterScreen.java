package github.dctime.dctimesignals.screen;

import github.dctime.dctimesignals.menu.GroundPenetratingSignalEmitterMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GroundPenetratingSignalEmitterScreen extends AbstractContainerScreen<GroundPenetratingSignalEmitterMenu> {


    public GroundPenetratingSignalEmitterScreen(GroundPenetratingSignalEmitterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

    }
}
