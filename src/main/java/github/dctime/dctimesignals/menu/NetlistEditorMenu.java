package github.dctime.dctimesignals.menu;

import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterMenuTypes;
import github.dctime.dctimesignals.block.NetlistEditorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class NetlistEditorMenu extends AbstractContainerMenu {
    protected NetlistEditorMenu(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    private ContainerLevelAccess access;
    public String initialNetlist;
    public BlockPos blockPos;

    // ── 客戶端建構子 ────────────────────────────────────────────────────────
    public NetlistEditorMenu(int windowId, Inventory playerInv, FriendlyByteBuf buf) {
        super(RegisterMenuTypes.NETLIST_EDITOR_MENU.get(), windowId);
        this.access         = ContainerLevelAccess.NULL;
        this.blockPos       = buf.readBlockPos();
        this.initialNetlist = buf.readUtf();
    }

    // ── 伺服器端建構子 ──────────────────────────────────────────────────────
    public NetlistEditorMenu(int windowId, Inventory playerInv, NetlistEditorBlockEntity be) {
        super(RegisterMenuTypes.NETLIST_EDITOR_MENU.get(), windowId);
        this.access         = ContainerLevelAccess.create(be.getLevel(), be.getBlockPos());
        this.blockPos       = be.getBlockPos();
        this.initialNetlist = be.getNetlist();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, RegisterBlocks.NETLIST_EDITOR_BLOCK.get());
    }
}
