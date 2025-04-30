package github.dctime.dctimemod.block;

import github.dctime.dctimemod.RegisterBlocks;
import github.dctime.dctimemod.RegisterMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ConstSignalMenu extends AbstractContainerMenu {
    private ContainerData data;
    ContainerLevelAccess access;

    public ContainerData getData() {
        return data;
    }

    public ConstSignalMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL, new SimpleContainerData(ConstSignalBlockEntity.DATA_SIZE));
    }

    public ConstSignalMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, SimpleContainerData data) {
        super(RegisterMenuTypes.CONST_SIGNAL_MENU.get(), containerId);
        this.access = access;
        this.data = data;
        addDataSlots(this.data);
        // System.out.println("data: " + data.get(ConstSignalBlockEntity.OUTPUT_SIGNAL_VALUE_INDEX));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, RegisterBlocks.CONSTANT_SIGNAL_BLOCK.get());
    }
}
