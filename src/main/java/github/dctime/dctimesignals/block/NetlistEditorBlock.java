package github.dctime.dctimesignals.block;

import com.mojang.serialization.MapCodec;
import github.dctime.dctimesignals.item.SignalDataItem;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public class NetlistEditorBlock extends BaseEntityBlock {

    public NetlistEditorBlock(Properties props) {
        super(props);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new NetlistEditorBlockEntity(blockPos, blockState);
    }

    // 手持物品右鍵 → 插入碟片
    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level,
                                           BlockPos pos, Player player, InteractionHand hand,
                                           BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof NetlistEditorBlockEntity be)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (stack.getItem() instanceof SignalDataItem) {
            if (!level.isClientSide) {
                if (be.hasDisc()) {
                    player.displayClientMessage(
                            Component.literal("請先取出碟片（Shift 右鍵）"), true);
                } else {
                    ItemStack leftover = be.insertItem(stack.copyWithCount(1));
                    if (leftover.isEmpty()) stack.shrink(1);
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    // 手空右鍵 → 取出碟片 或 開啟 GUI
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                            Player player, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof NetlistEditorBlockEntity be)) {
            return InteractionResult.PASS;
        }

        // Shift 右鍵 → 取出碟片
        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                ItemStack extracted = be.extractItem();
                if (!extracted.isEmpty()) player.getInventory().add(extracted);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // 右鍵 → 開啟 GUI
        if (!level.isClientSide && player instanceof ServerPlayer sp) {
            sp.openMenu(be, buf -> {
                buf.writeBlockPos(be.getBlockPos());
                buf.writeUtf(be.getNetlist());
            });
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    // 破壞時掉出碟片
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos,
                         BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof NetlistEditorBlockEntity be) {
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
