package github.dctime.dctimemod.menu;

import github.dctime.dctimemod.RegisterBlocks;
import github.dctime.dctimemod.RegisterMenuTypes;
import github.dctime.dctimemod.block.SignalOperationBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SignalOperationMenu extends AbstractContainerMenu {
    private ContainerData data;
    private ContainerLevelAccess access;

    public ContainerData getData() {
        return data;
    }

    public SignalOperationMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL, new SimpleContainerData(SignalOperationBlockEntity.DATA_SIZE), new ItemStackHandler(SignalOperationBlockEntity.ITEM_SIZE));
    }

    public SignalOperationMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, ContainerData data, IItemHandler cardHandler) {
        super(RegisterMenuTypes.SIGNAL_OPERATION_MENU.get(), containerId);

        checkContainerDataCount(data, SignalOperationBlockEntity.DATA_SIZE);

        this.access = access;
        this.data = data;

        addDataSlots(this.data);
        addSlot(new SlotItemHandler(cardHandler, 0, 0, 0));

        int rows = 3;
        int i = (rows - 4) * 18;

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
        }
        // System.out.println("data: " + data.get(ConstSignalBlockEntity.OUTPUT_SIGNAL_VALUE_INDEX));
    }


    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, RegisterBlocks.SIGNAL_OPERATION_BLOCK.get());
    }
}
