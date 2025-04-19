package github.dctime.dctimemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
        if (!level.isClientSide()) {
            //server send packet to client
            BlockEntity entity = level.getBlockEntity(pos);
            level.sendBlockUpdated(entity.getBlockPos(), entity.getBlockState(), entity.getBlockState(), Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS);
        }

        if (level.isClientSide()) {
            // client side
            BlockEntity entity = level.getBlockEntity(pos);
            if (!(entity instanceof FlawlessExchangerBlockEntity flawlessExchangerBlockEntity))
                return InteractionResult.FAIL;
            System.out.println("Client ProcessTime: " + flawlessExchangerBlockEntity.getProcessTime());
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
