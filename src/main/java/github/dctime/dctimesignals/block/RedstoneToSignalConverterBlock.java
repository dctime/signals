package github.dctime.dctimesignals.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RedstoneToSignalConverterBlock extends SignalOutputBlock{
    public RedstoneToSignalConverterBlock(Properties properties) {
        super(properties);
    }

    private int receivedSignal = 0;

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
//        Direction outputDirection = state.getValue(OUTPUT_DIRECTION);
//
//        int tempReceivedSignal = 0;
//        for (Direction direction : directions) {
//            if (state.getValue(SignalOutputBlock.OUTPUT_DIRECTION) == direction) continue;
//            int temp = level.getSignal(pos.relative(direction), direction.getOpposite());
//            if (temp > tempReceivedSignal) {
//                tempReceivedSignal = temp;
//            }
//        }

        int tempReceivedSignal = calculateTargetStrength(level, pos);
        System.out.println("RedstoneToSignal: " + tempReceivedSignal);
        receivedSignal = tempReceivedSignal;
        detectSignalWireAndUpdate(state, level, pos, true, false, receivedSignal);
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
        return receivedSignal;
    }
}
