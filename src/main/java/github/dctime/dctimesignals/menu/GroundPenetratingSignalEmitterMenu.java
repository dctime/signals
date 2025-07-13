package github.dctime.dctimesignals.menu;

import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterMenuTypes;
import github.dctime.dctimesignals.block.GroundPenetratingSignalEmitterBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class GroundPenetratingSignalEmitterMenu extends AbstractContainerMenu {

    ContainerLevelAccess access;
    ItemStackHandler items;
    ContainerData data;
    // Server
    public GroundPenetratingSignalEmitterMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, ItemStackHandler items, ContainerData data) {
        super(RegisterMenuTypes.GROUND_PENETRATING_SIGNAL_EMITTER.get(), containerId);
        this.access = access;
        this.items = items;
        this.data = data;

        checkContainerDataCount(data, GroundPenetratingSignalEmitterBlockEntity.DATA_SIZE);

        this.addSlot(new SlotItemHandler(items, GroundPenetratingSignalEmitterBlockEntity.ITEMS_PICKAXE_INPUT, 0, 10));
        this.addSlot(new SlotItemHandler(items, GroundPenetratingSignalEmitterBlockEntity.ITEMS_PICKAXE_OUTPUT, 0, 30));
        this.addSlot(new SlotItemHandler(items, GroundPenetratingSignalEmitterBlockEntity.ITEMS_FILTER, 0, 50));
        this.addDataSlots(data);

        // inventory slots
        int rows = 3;
        int i = (rows - 4) * 18;

        // first slot index: 0 + 0 * 9 + 9 = 9
        // l is row
        // j1 is column
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        // player hotbar 0-8
        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
        }
    }

    // client
    public GroundPenetratingSignalEmitterMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL, new ItemStackHandler(GroundPenetratingSignalEmitterBlockEntity.ITEMS_SIZE), new SimpleContainerData(GroundPenetratingSignalEmitterBlockEntity.DATA_SIZE));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, RegisterBlocks.GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK.get());
    }
}
