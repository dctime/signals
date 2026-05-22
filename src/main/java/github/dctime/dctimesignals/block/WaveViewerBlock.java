package github.dctime.dctimesignals.block;

import com.mojang.serialization.MapCodec;
import github.dctime.dctimesignals.RegisterItems;
import github.dctime.dctimesignals.item.SignalDataItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxPlayable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class WaveViewerBlock extends Block implements EntityBlock {
    public WaveViewerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WaveViewerBlockEntity(pos, state);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                            Player player, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof WaveViewerBlockEntity be)) {
            return InteractionResult.PASS;
        }

        // Shift 右鍵 → 取出碟片
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                ItemStack extracted = be.extractItem();
                if (!extracted.isEmpty()) {
                    player.getInventory().add(extracted);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // 有數據 → 開啟波形 GUI
//        if (be.hasSignalData()) {
            if (!level.isClientSide && player instanceof ServerPlayer sp) {
                sp.openMenu(be);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
//        }

//        return InteractionResult.PASS;
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
                                       BlockPos pos, Player player, InteractionHand hand,
                                       BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof WaveViewerBlockEntity be)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // 手持碟片 → 插入
        if (stack.getItem() instanceof SignalDataItem && SignalDataItem.hasData(stack)) {
            if (!level.isClientSide) {
                // 伺服器端才執行邏輯
                if (be.getStoredItem().isEmpty()) {
                    ItemStack leftover = be.insertItem(stack.copyWithCount(1));
                    if (leftover.isEmpty()) stack.shrink(1);
                } else {
                    player.displayClientMessage(
                            Component.literal("SHIFT RIGHT CLICK WITH EMPTY HAND TO TAKE OUT"), true);
                }
            }
            // 兩端都回傳，客戶端 SUCCESS，伺服器 CONSUME
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        if (stack.getItem() instanceof SignalDataItem && !SignalDataItem.hasData(stack)) {
            player.displayClientMessage(
                    Component.literal("THE DISK DOESN'T HAVE ANY DATA IN IT, stack: " + stack.getItem().toString()), true);
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    // 破壞時掉出碟片
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos,
                         BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof WaveViewerBlockEntity be) {
                ItemStack stored = be.getStoredItem();
                if (!stored.isEmpty()) {
                    net.minecraft.world.Containers.dropItemStack(
                            level, pos.getX(), pos.getY(), pos.getZ(), stored);
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
