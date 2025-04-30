package github.dctime.dctimemod.block;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ConstSignalScreen extends AbstractContainerScreen<ConstSignalMenu> {

    EditBox editBox;

    public ConstSignalScreen(ConstSignalMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        editBox = new EditBox(Minecraft.getInstance().font, 0, 0, Component.literal("Hello"));
        this.addRenderableOnly(editBox);
        setFocused(true);
        editBox.setEditable(true);
        editBox.setFocused(true);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        editBox.setWidth((int) (imageWidth*0.5));
        editBox.setHeight((int) (imageHeight*0.3));
        editBox.setX(leftPos+(imageWidth/2-editBox.getWidth()/2));
        editBox.setY(topPos+(imageHeight/2-editBox.getHeight()/2));

        editBox.render(guiGraphics, mouseX, mouseY, partialTick);

        System.out.println("Active:" + editBox.isActive() + "Focused:" + editBox.isFocused() + "MouseX:" + mouseX + "MouseY:" + mouseY);
    }

    @Override
    public boolean charTyped(char c, int modifier)
    {
        return this.editBox.charTyped(c,modifier);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int uhh)
    {
        return this.editBox.keyPressed(keyCode, scanCode, uhh)
                || (this.editBox.isFocused() && keyCode != 256)
                || super.keyPressed(keyCode, scanCode, uhh);
    }

    @Override
    public void onClose() {
        getMenu().getData().set(ConstSignalBlockEntity.OUTPUT_SIGNAL_VALUE_INDEX, Integer.parseInt(editBox.getValue()));
        super.onClose();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // dont render anything
    }
}
