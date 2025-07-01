package github.dctime.dctimesignals.menu;

import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterMenuTypes;
import github.dctime.dctimesignals.block.SignalResearchStationBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class SignalResearchMenu extends AbstractContainerMenu {

    private ContainerLevelAccess access;

    // Server
    public SignalResearchMenu(int containerId, Inventory playerInv, ContainerLevelAccess access, ContainerData data) {
        super(RegisterMenuTypes.SIGNAL_RESEARECH_MENU.get(), containerId);
        this.access = access;

        checkContainerDataCount(data, SignalResearchStationBlockEntity.DATA_SIZE);
    }

    // Client menu constructor (Registered)
    public SignalResearchMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL, new SimpleContainerData(SignalResearchStationBlockEntity.DATA_SIZE));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, RegisterBlocks.SIGNAL_RESEARCH_STATION.get());
    }
}
