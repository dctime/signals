package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SignalResearchStationSignalInputBlock extends OneDirectionSignalBlock implements EntityBlock {
    public SignalResearchStationSignalInputBlock(Properties properties) {
        super(properties);

        registerDefaultState(stateDefinition.any().setValue(INPUT_SIDE_DIRECTION, Direction.NORTH));
    }

    @Override
    @Nullable
    Integer getSignalValue(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof SignalResearchStationSignalInputBlockEntity entity) {
            return entity.getStoredSignalValue();
        }
        return null;
    }

    public static final EnumProperty<Direction> INPUT_SIDE_DIRECTION = DirectionProperty.create("input_side_direction", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(INPUT_SIDE_DIRECTION);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(INPUT_SIDE_DIRECTION, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SignalResearchStationSignalInputBlockEntity(blockPos, blockState);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return super.useWithoutItem(state, level, pos, player, hitResult, INPUT_SIDE_DIRECTION, "No Input Signal", "Current input Value: ");
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if (level.isClientSide()) return;
        //server


        if (level.getBlockEntity(pos) instanceof SignalResearchStationSignalInputBlockEntity entity) {
            // events that will trigger the method:
            // self blockstate modifies like connections to someone else
            // nearby blocks break/placed
            // nearby signal wires updated

            // get direction of the updated block
            // if he is a signal block do the following:
            // if he has connection to you and you have connection to him and the value is different
            // use the capability system
            // get value from him and modify your value
            // if there is signal block nearby then modify your value again
            // (for signal web signal block break edge cases)
            // update the surroundings
            // else then
            // break and do not update other stuff and this is the end of the update tree
            Direction direction = Direction.fromDelta(neighborPos.getX() - pos.getX(), neighborPos.getY() - pos.getY(), neighborPos.getZ() - pos.getZ());
            if (entity.getBlockState().getValue(SignalResearchStationSignalInputBlock.INPUT_SIDE_DIRECTION) != direction) return;
            if (neighborBlock instanceof SignalWireBlock signalWireBlock) {
                SignalWireInformation info = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, neighborPos, direction);
                // neighbor connection stored in info
                // self connection stored in directionHasConnection
//                System.out.println("Direction: " + direction.getName());
//                System.out.println("BlockPos: " + "x: " + pos.getX() + ", y:" + pos.getY() + ",z: " + pos.getZ()
//                    + ", Neighbor: " + "x: " + neighborPos.getX() + ", y: " + neighborPos.getY() + ", z: " + neighborPos.getZ());
                if (info == null) {
//                    System.out.println("Neighbor not connected");
                } else {
                    if (!(info.getSignalValue() == null)) {
                        entity.setStoredSignalValue(info.getSignalValue());
                    }
                }
            }
            // Const Signal will set the value of the wire to zero when on move
            // Const Signal Block will apply signal the wire automatically when updated (when some signal block destroyed in the circuit)
        }
    }
}
