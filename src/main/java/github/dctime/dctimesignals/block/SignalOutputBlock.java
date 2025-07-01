package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

abstract class SignalOutputBlock extends OneDirectionSignalBlock {
    public SignalOutputBlock(Properties properties) {
        super(properties);
    }

    public static final EnumProperty<Direction> OUTPUT_DIRECTION = DirectionProperty.create("output_direction", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!oldState.is(this)) {
            level.invalidateCapabilities(pos);
        }

        if (level.isClientSide()) return;
        Integer signalValue = getSignalValue(state, level, pos);
        if (signalValue == null) {
            detectSignalWireAndUpdate(state, level, pos, false, true, 0);
        } else {
            detectSignalWireAndUpdate(state, level, pos, false, false, signalValue);
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return super.useWithoutItem(state, level, pos, player, hitResult, OUTPUT_DIRECTION, "No Output Signal", "Output Value: ");
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, true);
        if (!state.is(newState.getBlock())) {
            level.invalidateCapabilities(pos);
        }

        if (level.isClientSide()) return;
        detectSignalWireAndUpdate(state, level, pos, true, true, 0);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(OUTPUT_DIRECTION, context.getNearestLookingDirection().getOpposite());
    }

    public void detectSignalWireAndUpdate(BlockState state, Level level, BlockPos pos, boolean forcefully, boolean noSignal, int signalValue) {
        Direction direction = state.getValue(OUTPUT_DIRECTION);
        BlockPos targetPos = pos.relative(direction);

//        System.out.println("pos: x: " + pos.getX() + ", y: " + pos.getY() + ", z:" + pos.getZ());
//        System.out.println("targetPos: x: " + targetPos.getX() + ", y: " + targetPos.getY() + ", z:" + targetPos.getZ());

        SignalWireInformation targetInfo = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, targetPos, direction);
        if (targetInfo == null) return;
//        System.out.println("got signal wire");
        // higher signal value dominates lower signal value
        // onBreak set to 0 forcefully to prevent edge case that the wire remains the signal the signal block sends
        if (targetInfo.getSignalValue() != null) {
            if (targetInfo.getSignalValue() >= signalValue && !forcefully) return;
        }
//        System.out.println("signal value changed");
        if (noSignal) {targetInfo.setNoSignal();}
        else targetInfo.setSignalValue(signalValue);


        level.updateNeighborsAt(targetPos, level.getBlockState(targetPos).getBlock());
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if (level.isClientSide()) return;
        Integer signalValue = getSignalValue(state, level, pos);
        if (signalValue == null) {
            detectSignalWireAndUpdate(state, level, pos, false, true, 0);
        } else {
            detectSignalWireAndUpdate(state, level, pos, false, false, signalValue);
        }
    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OUTPUT_DIRECTION);
    }
}
