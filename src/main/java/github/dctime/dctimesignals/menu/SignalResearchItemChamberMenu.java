package github.dctime.dctimesignals.menu;

import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterMenuTypes;
import github.dctime.dctimesignals.block.SignalResearchItemChamberBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class SignalResearchItemChamberMenu extends AbstractContainerMenu {
    static class OutputSlotItemHand extends SlotItemHandler {
        public OutputSlotItemHand(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }

    static class ResearchingItemSlot extends SlotItemHandler {
        public ResearchingItemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false; // Prevent placing items in this slot
        }
        public boolean mayPickup(Player player) {
            return false; // Prevent picking up items from this slot
        }
    }
    // Server
    private final ContainerLevelAccess access;
    private final ContainerData data;
    private final IItemHandler researchingItem;

    public double getProgress() {
        return data.get(SignalResearchItemChamberBlockEntity.DATA_PROGRESS_INDEX);
    }
    public ItemStack getResearchingItem() {
        return researchingItem.getStackInSlot(SignalResearchItemChamberBlockEntity.RESEARCHING_ITEM_INDEX);
    }

    // server
    public SignalResearchItemChamberMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, IItemHandler itemSlots, ContainerData data, IItemHandler researchingItem) {

        super(RegisterMenuTypes.SIGNAL_RESEARCH_ITEM_CHAMBER_MENU.get(), containerId);

        this.access = access;
        this.data = data;
        this.researchingItem = researchingItem;

        checkContainerDataCount(data, 1);

        this.addDataSlots(data);

        // input
        this.addSlot(new SlotItemHandler(itemSlots, SignalResearchItemChamberBlockEntity.ITEMS_INPUT_1_INDEX, 80, 56+1));
        this.addSlot(new SlotItemHandler(itemSlots, SignalResearchItemChamberBlockEntity.ITEMS_INPUT_2_INDEX, 80-18, 56+1));
        this.addSlot(new SlotItemHandler(itemSlots, SignalResearchItemChamberBlockEntity.ITEMS_INPUT_3_INDEX, 80+18, 56+1));
        // output
        this.addSlot(new OutputSlotItemHand(itemSlots, SignalResearchItemChamberBlockEntity.ITEMS_OUTPUT_INDEX, 80, 16+1));
        // researching item
        this.addSlot(new ResearchingItemSlot(researchingItem, SignalResearchItemChamberBlockEntity.RESEARCHING_ITEM_INDEX, 80 + 18 * 2, 16+1));

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
    // Client
    public SignalResearchItemChamberMenu(int containerId, Inventory playerInv) {
        this(containerId, playerInv, ContainerLevelAccess.NULL, new ItemStackHandler(SignalResearchItemChamberBlockEntity.ITEMS_SIZE), new SimpleContainerData(SignalResearchItemChamberBlockEntity.DATA_SIZE), new ItemStackHandler(SignalResearchItemChamberBlockEntity.RESEARCHING_ITEM_SIZE));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            // If we're moving from the container slots (inputs or output)
            if (index < 5) {
                // Try to move to player inventory
                if (!this.moveItemStackTo(itemstack1, 5, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }
            // If we're moving from player inventory
            else {
                // Try to move to input slots only (0-2)
                if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
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
        return AbstractContainerMenu.stillValid(this.access, player, RegisterBlocks.SIGNAL_RESEARCH_ITEM_CHAMBER.get());
    }
}
