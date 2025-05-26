package github.dctime.dctimemod.screen;


import github.dctime.dctimemod.DCtimeMod;
import github.dctime.dctimemod.menu.SignalOperationMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class SignalOperationScreen extends AbstractContainerScreen<SignalOperationMenu> {
    private static final ResourceLocation CONTAINER_TEXTURE = ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "textures/gui/container/operation_signal_screen.png");

    public SignalOperationScreen(SignalOperationMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i1, int i2) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2 + 1;
        guiGraphics.blit(CONTAINER_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
    }
}
