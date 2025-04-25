package github.dctime.dctimemod.block;

import github.dctime.dctimemod.RegisterBlocks;
import github.dctime.dctimemod.RegisterMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class FlawlessExchangerMenu extends AbstractContainerMenu {

//    FlawlessExchangerBlockEntity entity;
    ContainerLevelAccess access;
    ContainerData data;
    //client
    public FlawlessExchangerMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL, new ItemStackHandler(1), new SimpleContainerData(FlawlessExchangerBlockEntity.DATA_SIZE));
    }

    // server
    public FlawlessExchangerMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler dataInventory, ContainerData data) {
        super(RegisterMenuTypes.FLAWLESS_EXCHANGER_MENU.get(), containerId);

        checkContainerDataCount(data, FlawlessExchangerBlockEntity.DATA_SIZE);
        this.access = access;

        int rows = 3;
        int i = (rows - 4) * 18;

        this.addSlot(new SlotItemHandler(dataInventory, 0, 0, 0));
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 161 + i));
        }

        this.data = data;
        this.addDataSlots(this.data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(access, player, RegisterBlocks.FLAWLESS_EXCHANGER.get());
    }

    // for screen
    @OnlyIn(Dist.CLIENT)
    public int getProcessTime() {
        return this.data.get(0);
    }
}
