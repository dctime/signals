package github.dctime.dctimesignals.screen;

import github.dctime.dctimesignals.menu.SignalResearchMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class SignalResearchScreen extends AbstractContainerScreen<SignalResearchMenu> {
    public SignalResearchScreen(SignalResearchMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        for (int inputSignalIndex = 0; inputSignalIndex < this.getMenu().getInputSignalData().getCount(); inputSignalIndex++) {
            int value = this.getMenu().getInputSignalData().get(inputSignalIndex);
            guiGraphics.drawString(this.font, String.valueOf(value), 10, 10+inputSignalIndex * 20, 0xFFFFFF);
        }

        for (int outputSignalIndex = 0; outputSignalIndex < this.getMenu().getOutputSignalData().getCount(); outputSignalIndex++) {
            int value = this.getMenu().getOutputSignalData().get(outputSignalIndex);
            guiGraphics.drawString(this.font, String.valueOf(value), 100, 10+outputSignalIndex * 20, 0xFFFFFF);
        }
    }
}
