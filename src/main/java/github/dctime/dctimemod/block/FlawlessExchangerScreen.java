package github.dctime.dctimemod.block;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;


public class FlawlessExchangerScreen extends AbstractContainerScreen {

    public FlawlessExchangerScreen(FlawlessExchangerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

    }
}
