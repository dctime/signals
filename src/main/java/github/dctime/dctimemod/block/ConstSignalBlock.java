package github.dctime.dctimemod.block;

import com.mojang.datafixers.kinds.Const;
import com.mojang.serialization.MapCodec;
import github.dctime.dctimemod.RegisterCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.ObserverBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class ConstSignalBlock extends DirectionalBlock {
    public static final MapCodec<ConstSignalBlock> CODEC = simpleCodec(ConstSignalBlock::new);

    public ConstSignalBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!oldState.is(this)) {
            level.invalidateCapabilities(pos);
        }

        if (level.isClientSide()) return;
        detectSignalWireAndUpdate(state, level, pos, movedByPiston, 30);

    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if (!state.is(newState.getBlock())) {
            level.invalidateCapabilities(pos);
        }

        if (level.isClientSide()) return;
        detectSignalWireAndUpdate(state, level, pos, movedByPiston, 0);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        if (level.isClientSide()) return;
        detectSignalWireAndUpdate(state, level, pos, movedByPiston, 30);

    }

    private void detectSignalWireAndUpdate(BlockState state, Level level, BlockPos pos, boolean movedByPiston, int signalValue) {
        Direction direction = state.getValue(FACING);
        BlockPos targetPos = pos.relative(direction);

        System.out.println("pos: x: " + pos.getX() + ", y: " + pos.getY() + ", z:" + pos.getZ());
        System.out.println("targetPos: x: " + targetPos.getX() + ", y: " + targetPos.getY() + ", z:" + targetPos.getZ());

        SignalWireInformation targetInfo = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, targetPos, direction);
        if (targetInfo == null) return;
        System.out.println("got signal wire");
        if (targetInfo.getSignalValue() == signalValue) return;
        System.out.println("signal value changed");
        targetInfo.setSignalValue(signalValue);
        level.updateNeighborsAt(targetPos, level.getBlockState(targetPos).getBlock());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
