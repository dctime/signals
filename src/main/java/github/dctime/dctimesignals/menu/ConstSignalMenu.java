package github.dctime.dctimesignals.menu;

import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterMenuTypes;
import github.dctime.dctimesignals.block.ConstSignalBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ConstSignalMenu extends AbstractContainerMenu {
    private ContainerData data;
    private ContainerLevelAccess access;
    private ConstSignalBlockEntity entity;

    public ContainerData getData() {
        return data;
    }

    public ConstSignalMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL, new SimpleContainerData(ConstSignalBlockEntity.DATA_SIZE), null);
    }

    public ConstSignalMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access, SimpleContainerData data, ConstSignalBlockEntity entity) {
        super(RegisterMenuTypes.CONST_SIGNAL_MENU.get(), containerId);
        this.access = access;
        this.data = data;
        this.entity = entity;
        addDataSlots(this.data);
        // System.out.println("data: " + data.get(ConstSignalBlockEntity.OUTPUT_SIGNAL_VALUE_INDEX));
    }

    // server only not synced
    @Nullable
    public ConstSignalBlockEntity getBlockEntity() {
        return entity;
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
