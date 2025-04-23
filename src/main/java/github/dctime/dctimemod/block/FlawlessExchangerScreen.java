package github.dctime.dctimemod.block;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;


public class FlawlessExchangerScreen extends AbstractContainerScreen<FlawlessExchangerMenu> {

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
        graphics.drawString(getMinecraft().font, menu.getProcessTime() + "", leftPos+(int)(imageWidth*0.1), topPos, 0xFFFFFF);
        graphics.fill((int) (leftPos+titleLabelX+imageWidth*0.9), (int)(topPos+titleLabelY+imageHeight*0.1), (int)(leftPos+titleLabelX+imageWidth*0.95), (int)(topPos+inventoryLabelY-imageWidth*0.01), 0xFFFF0000);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
    }
}
