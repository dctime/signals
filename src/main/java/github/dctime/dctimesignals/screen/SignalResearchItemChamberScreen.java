package github.dctime.dctimesignals.screen;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.menu.SignalResearchItemChamberMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SignalResearchItemChamberScreen extends AbstractContainerScreen<SignalResearchItemChamberMenu> {
    private static final ResourceLocation CONTAINER_TEXTURE = ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "textures/gui/container/signal_research_station_item_chamber_screen.png");
    private static final ResourceLocation LIGHT_BULB = ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "textures/gui/container/signal_research_station_item_chamber_light_bulb.png");

    public SignalResearchItemChamberScreen(SignalResearchItemChamberMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i1, int i2) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_TEXTURE, i, j+1, 0, 0, this.imageWidth, this.imageHeight);
        double progress = menu.getProgress()/100.0;
        int progressHeight = (int)((22-3-4)*progress);
        int startY = 22 - progressHeight - 3;
        guiGraphics.blit(LIGHT_BULB, i+79, j+33+startY+1, 0, startY, 18, progressHeight+4-1, 18, 22);
    }
}
