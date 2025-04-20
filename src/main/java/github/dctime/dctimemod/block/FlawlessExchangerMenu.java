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

//    FlawlessExchangerBlockEntity entity;
    ContainerLevelAccess access;
    //client
    public FlawlessExchangerMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL, new ItemStackHandler(1));
    }

    // server
    public FlawlessExchangerMenu(int containerId, Inventory inventory, ContainerLevelAccess access, IItemHandler dataInventory) {
        super(RegisterMenuTypes.FLAWLESS_EXCHANGER_MENU.get(), containerId);
        this.access = access;

        this.addSlot(new SlotItemHandler(dataInventory, 0, 0, 0));
        this.addStandardInventorySlots(inventory, 8, 84);
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
