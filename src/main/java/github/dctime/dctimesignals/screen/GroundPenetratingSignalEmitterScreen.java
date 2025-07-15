package github.dctime.dctimesignals.screen;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.menu.GroundPenetratingSignalEmitterMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GroundPenetratingSignalEmitterScreen extends AbstractContainerScreen<GroundPenetratingSignalEmitterMenu> {

    private static final ResourceLocation CONTAINER_TEXTURE = ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "textures/gui/container/ground_penetrating_signal_emitter_screen.png");

    public GroundPenetratingSignalEmitterScreen(GroundPenetratingSignalEmitterMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i1, int i2) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_TEXTURE, i, j+1, 0, 0, this.imageWidth, this.imageHeight);
    }
}
