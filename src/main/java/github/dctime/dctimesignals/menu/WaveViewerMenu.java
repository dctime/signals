package github.dctime.dctimesignals.menu;

import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterMenuTypes;
import github.dctime.dctimesignals.block.WaveViewerBlockEntity;
import github.dctime.dctimesignals.item.SignalDataItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class WaveViewerMenu extends AbstractContainerMenu {

    // 客戶端從這裡讀波形
    public String label;
    public double[] times;
    public double[] values;

    private ContainerLevelAccess access;

    // ── 客戶端建構子（RegisterMenuTypes 呼叫）──────────────────────────
    public WaveViewerMenu(int windowId, Inventory playerInv) {
        this(windowId, playerInv, new ItemStackHandler(1), ContainerLevelAccess.NULL);
    }

    // ── 伺服器端建構子（be.createMenu 呼叫）────────────────────────────────
    public WaveViewerMenu(int windowId, Inventory playerInv, WaveViewerBlockEntity be) {
        this(windowId, playerInv, be.getInventory(),
                ContainerLevelAccess.create(be.getLevel(), be.getBlockPos()));
    }

    // ── 共用邏輯 ────────────────────────────────────────────────────────────
    private WaveViewerMenu(int windowId, Inventory playerInv,
                           IItemHandler inventory, ContainerLevelAccess access) {
        super(RegisterMenuTypes.WAVE_VIEWER_MENU.get(), windowId);
        this.access = access;

        // 訊號碟片槽，座標是相對於 Screen 的 leftPos/topPos
        // 只傳資料不顯示
        this.addSlot(new SlotItemHandler(inventory, 0, -100, -100));

//        // 玩家物品欄（27格）
//        for (int row = 0; row < 3; row++) {
//            for (int col = 0; col < 9; col++) {
//                this.addSlot(new Slot(playerInv, col + row * 9 + 9,
//                        8 + col * 18, 84 + row * 18));
//            }
//        }
//
//        // 玩家快捷列（9格）
//        for (int col = 0; col < 9; col++) {
//            this.addSlot(new Slot(playerInv, col, 8 + col * 18, 142));
//        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, RegisterBlocks.WAVE_VIEWER.get());
    }
}
