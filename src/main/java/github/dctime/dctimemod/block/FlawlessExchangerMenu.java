package github.dctime.dctimemod.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class FlawlessExchangerMenu extends AbstractContainerMenu {

    ContainerLevelAccess access;
    //client
    public FlawlessExchangerMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }
    // server
    public FlawlessExchangerMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        this(containerId, inventory, access, new ItemStackHandler(1), DataSlot.standalone());
    }

    // server
    public FlawlessExchangerMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler itemHandler, DataSlot dataSingle) {
        super(RegisterMenuTypes.FLAWLESS_EXCHANGER_MENU.get(), containerId);
        this.access = access;
        this.addSlot(new SlotItemHandler(itemHandler, 0, 100, 100));
        this.addSlot(new Slot(inventory, 1, 200, 200));
        this.addDataSlot(dataSingle);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(access, player, RegisterBlocks.FLAWLESS_EXCHANGER.get());
    }
}
