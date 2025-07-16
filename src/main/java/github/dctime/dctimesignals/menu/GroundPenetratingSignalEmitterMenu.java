package github.dctime.dctimesignals.menu;

import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterItems;
import github.dctime.dctimesignals.RegisterMenuTypes;
import github.dctime.dctimesignals.block.GroundPenetratingSignalEmitterBlockEntity;
import github.dctime.dctimesignals.block.SignalResearchItemChamberBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class GroundPenetratingSignalEmitterMenu extends AbstractContainerMenu {

    public static class FilterSlot extends SlotItemHandler {
        public FilterSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);

        }

        @Override
        public boolean mayPickup(Player playerIn) {
            return false; // Prevent player from taking items out of the filter slot
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            if (!(stack.getItem() instanceof BlockItem)) return false;
            if (!(getItemHandler() instanceof ItemStackHandler)) return false;
            ItemStackHandler itemHandler = (ItemStackHandler) getItemHandler();
            itemHandler.setStackInSlot(GroundPenetratingSignalEmitterBlockEntity.ITEMS_FILTER, stack.copyWithCount(1));
            return false; // Allow placing any item in the filter slot
        }
    }

    public static class PickaxeInputSlot extends SlotItemHandler {
        public PickaxeInputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getItem().getDefaultInstance().is(RegisterItems.SIGNAL_PICKAXE); // Allow only pickaxes
        }
    }

    public static class PickaxeOutputSlot extends SlotItemHandler {
        public PickaxeOutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }


    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        System.out.println("Clicked slot: " + slotId + ", button: " + button + ", clickType: " + clickType);
        if (slotId == GroundPenetratingSignalEmitterBlockEntity.ITEMS_FILTER &&
                clickType == ClickType.PICKUP && button == 0
        ) {
            clearFilter();

        }
        super.clicked(slotId, button, clickType, player);
    }

    public void clearFilter() {
        items.setStackInSlot(GroundPenetratingSignalEmitterBlockEntity.ITEMS_FILTER, ItemStack.EMPTY);
        System.out.println("Filter slot cleared.");
    }

    public void setFilter(@Nullable ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            clearFilter();
        } else {
            items.setStackInSlot(GroundPenetratingSignalEmitterBlockEntity.ITEMS_FILTER, stack.copyWithCount(1));
        }
    }

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

        this.addSlot(new PickaxeInputSlot(items, GroundPenetratingSignalEmitterBlockEntity.ITEMS_PICKAXE_INPUT, 43, 14+1));
        this.addSlot(new PickaxeOutputSlot(items, GroundPenetratingSignalEmitterBlockEntity.ITEMS_PICKAXE_OUTPUT, 43, 53+1));
        this.addSlot(new FilterSlot(items, GroundPenetratingSignalEmitterBlockEntity.ITEMS_FILTER, 123, 32+1));
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
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            // If we're moving from the container slots (inputs or output)
            if (index < 3) {
                // Try to move to player inventory 8 slots inventory starts from 8
                if (!this.moveItemStackTo(itemstack1, GroundPenetratingSignalEmitterBlockEntity.ITEMS_SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            // If we're moving from player inventory
            else {
                // Try to move to input slots only (0) not include  1 2
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, RegisterBlocks.GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK.get());
    }
}
