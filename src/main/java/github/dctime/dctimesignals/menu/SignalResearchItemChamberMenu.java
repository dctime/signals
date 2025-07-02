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
    // Server
    ContainerLevelAccess access;
    public SignalResearchItemChamberMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, IItemHandler itemSlots, ContainerData data) {
        super(RegisterMenuTypes.SIGNAL_RESEARCH_ITEM_CHAMBER_MENU.get(), containerId);

        this.access = access;

        checkContainerDataCount(data, 1);

        this.addDataSlots(data);

        this.addSlot(new SlotItemHandler(itemSlots, 0, 10, 10));
        this.addSlot(new SlotItemHandler(itemSlots, 1, 20, 10));

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
        this(containerId, playerInv, ContainerLevelAccess.NULL, new ItemStackHandler(SignalResearchItemChamberBlockEntity.ITEMS_SIZE), new SimpleContainerData(SignalResearchItemChamberBlockEntity.DATA_SIZE));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, RegisterBlocks.SIGNAL_RESEARCH_ITEM_CHAMBER.get());
    }
}
