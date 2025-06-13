package github.dctime.dctimesignals.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RedstoneToSignalConverterBlock extends SignalOutputBlock implements EntityBlock {
    public RedstoneToSignalConverterBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (level.isClientSide()) return;

        if (!(neighborBlock instanceof SignalWireBlock)) {
            receiveSignalFromOutside(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        } else {
            // trigger by wire
            super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        }
    }

    private void receiveSignalFromOutside(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (level.isClientSide()) return;

        int tempReceivedSignal = calculateTargetStrength(level, pos);
        System.out.println("RedstoneToSignal: " + tempReceivedSignal);
        if (level.getBlockEntity(pos) instanceof RedstoneToSignalConverterBlockEntity blockEntity) {
            blockEntity.setReceivedSignal(tempReceivedSignal);
            detectSignalWireAndUpdate(state, level, pos, true, false, blockEntity.getReceivedSignal());
        }
    }

    private int calculateTargetStrength(Level level, BlockPos pos) {
        int i = level.getBestNeighborSignal(pos);
        int j = 0;
        if (i < 15) {
            for(Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos blockpos = pos.relative(direction);
                BlockState blockstate = level.getBlockState(blockpos);
                j = Math.max(j, this.getWireSignal(blockstate));
                BlockPos blockpos1 = pos.above();
                if (blockstate.isRedstoneConductor(level, blockpos) && !level.getBlockState(blockpos1).isRedstoneConductor(level, blockpos1)) {
                    j = Math.max(j, this.getWireSignal(level.getBlockState(blockpos.above())));
                } else if (!blockstate.isRedstoneConductor(level, blockpos)) {
                    j = Math.max(j, this.getWireSignal(level.getBlockState(blockpos.below())));
                }
            }
        }

        return Math.max(i, j - 1);
    }

    private int getWireSignal(BlockState state) {
        return state.is(this) ? state.getValue(RedStoneWireBlock.POWER) : 0;
    }

    @Override
    @Nullable Integer getSignalValue(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof RedstoneToSignalConverterBlockEntity entity)
            return entity.getReceivedSignal();
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RedstoneToSignalConverterBlockEntity(blockPos, blockState);
    }
}
