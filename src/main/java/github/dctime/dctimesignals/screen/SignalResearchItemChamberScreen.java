package github.dctime.dctimesignals.screen;

import github.dctime.dctimesignals.menu.SignalResearchItemChamberMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class SignalResearchItemChamberScreen extends AbstractContainerScreen<SignalResearchItemChamberMenu> {
    public SignalResearchItemChamberScreen(SignalResearchItemChamberMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

    }
}
