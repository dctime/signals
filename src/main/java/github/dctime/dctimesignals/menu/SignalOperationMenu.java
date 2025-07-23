package github.dctime.dctimesignals.menu;

import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterMenuTypes;
import github.dctime.dctimesignals.RegisterSoundEvents;
import github.dctime.dctimesignals.block.SignalOperationBlockEntity;
import github.dctime.dctimesignals.item.SignalOperationBaseCardItem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SignalOperationMenu extends AbstractContainerMenu {

    private static class CardSlotItemHandler extends SlotItemHandler {
        private Level level;
        private BlockPos blockPos;
        public CardSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, Level level, BlockPos pos) {
            super(itemHandler, index, xPosition, yPosition);
            this.level = level;
            this.blockPos = pos;
        }

        // this is client side check for server side check SignalOperationBlockEntity CardItemStackHandler
        @Override
        public boolean mayPlace(ItemStack stack) {
            if (level == null) return stack.getItem() instanceof SignalOperationBaseCardItem;
            if (level.isClientSide()) return stack.getItem() instanceof SignalOperationBaseCardItem;
            if (stack.getItem() instanceof SignalOperationBaseCardItem) {
                level.playSound(null, blockPos, RegisterSoundEvents.CARD_INSERT_SOUND.get(), SoundSource.BLOCKS);
                return true;
            }
            return false;
        }

        @Override
        public int getMaxStackSize() {return 1;}
    }

    private ContainerData data;
    private ContainerLevelAccess access;

    public ContainerData getData() {
        return data;
    }

    public SignalOperationMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL, new SimpleContainerData(SignalOperationBlockEntity.DATA_SIZE), new ItemStackHandler(SignalOperationBlockEntity.ITEM_SIZE), null, null);
    }

    public SignalOperationMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, ContainerData data, IItemHandler cardHandler, Level level, BlockPos pos) {
        super(RegisterMenuTypes.SIGNAL_OPERATION_MENU.get(), containerId);

        checkContainerDataCount(data, SignalOperationBlockEntity.DATA_SIZE);

        this.access = access;
        this.data = data;

        addDataSlots(this.data);
        addSlot(new CardSlotItemHandler(cardHandler, 0, 80, 37, level, pos));

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
        // System.out.println("data: " + data.get(ConstSignalBlockEntity.OUTPUT_SIGNAL_VALUE_INDEX));
    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        // get the clicked item stack
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 0) {
                // move from card slot to inventory or hotbar
                // include startIndex exclude endIndex
                if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else {
                // pressed inventory or hotbar
                // try to put item into card slot
                if (this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    // clear clicked item
                    return ItemStack.EMPTY;
                }

                // clicked inventory slots

                if (index >= 1 && index < 28) {
                    // move to hotbar
                    if (!this.moveItemStackTo(itemstack1, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 28 && index < 37) {
                    if (!this.moveItemStackTo(itemstack1, 1, 28, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(itemstack1, 1, 37, false)) {
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
        return AbstractContainerMenu.stillValid(this.access, player, RegisterBlocks.SIGNAL_OPERATION_BLOCK.get());
    }
}
