package github.dctime.dctimemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class FlawlessExchangerBlock extends Block implements EntityBlock {
    public FlawlessExchangerBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FlawlessExchangerBlockEntity(blockPos, blockState);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (FlawlessExchangerBlockEntity::tick);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
//        if (!level.isClientSide()) {
//            //server send packet to client
//            BlockEntity entity = level.getBlockEntity(pos);
//            level.sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS);
//        }
//
//        if (level.isClientSide()) {
//            // client side
//            BlockEntity entity = level.getBlockEntity(pos);
//            if (!(entity instanceof FlawlessExchangerBlockEntity flawlessExchangerBlockEntity))
//                return InteractionResult.FAIL;
//            System.out.println("Client ProcessTime: " + flawlessExchangerBlockEntity.getProcessTime());
//            return InteractionResult.SUCCESS;
//        }
//
//        if (!level.isClientSide()) {
//            BlockEntity entity = level.getBlockEntity(pos);
//            if (entity instanceof Container container) {
//                ItemStack stack = container.getItem(0);
//                container.setItem(0, player.getMainHandItem());
//                player.setItemSlot(EquipmentSlot.MAINHAND, stack);
//            }
//        }

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            System.out.println("Opening Menu");
            serverPlayer.openMenu(state.getMenuProvider(level, pos));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((containerId, playerInventory, player)->new FlawlessExchangerMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos)), Component.translatable("menu.title.dctimemod.flawless_exchanger_menu"));
    }
}
