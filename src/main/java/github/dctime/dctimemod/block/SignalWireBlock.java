package github.dctime.dctimemod.block;

import github.dctime.dctimemod.RegisterBlockEntities;
import github.dctime.dctimemod.RegisterCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.CubeVoxelShape;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Entity;

import java.util.List;


public class SignalWireBlock extends Block implements EntityBlock {
    public SignalWireBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
            .setValue(NORTH, false)
            .setValue(SOUTH, false)
            .setValue(EAST, false)
            .setValue(WEST, false)
            .setValue(UP, false)
            .setValue(DOWN, false)
        );
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return SignalWireBlockEntity::tick;
    }

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    public static boolean directionGotConnection(SignalWireBlockEntity entity, @Nullable Direction direction) {
        switch (direction) {
            case NORTH:
                return entity.getBlockState().getValue(SignalWireBlock.SOUTH);
            case SOUTH:
                return entity.getBlockState().getValue(SignalWireBlock.NORTH);
            case EAST:
                return entity.getBlockState().getValue(SignalWireBlock.WEST);
            case WEST:
                return entity.getBlockState().getValue(SignalWireBlock.EAST);
            case UP:
                return entity.getBlockState().getValue(SignalWireBlock.DOWN);
            case DOWN:
                return entity.getBlockState().getValue(SignalWireBlock.UP);
            case null:
                return true;
            default:
                return false;
        }
    }

    public static boolean directionHasConnection(SignalWireBlockEntity entity, @Nullable Direction direction) {
        switch (direction) {
            case NORTH:
                return entity.getBlockState().getValue(SignalWireBlock.NORTH);
            case SOUTH:
                return entity.getBlockState().getValue(SignalWireBlock.SOUTH);
            case EAST:
                return entity.getBlockState().getValue(SignalWireBlock.EAST);
            case WEST:
                return entity.getBlockState().getValue(SignalWireBlock.WEST);
            case UP:
                return entity.getBlockState().getValue(SignalWireBlock.UP);
            case DOWN:
                return entity.getBlockState().getValue(SignalWireBlock.DOWN);
            case null:
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    public static final int WIRE_WIDTH = 4;

    // collision box
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(
            8 - (double) WIRE_WIDTH / 2,
            8 - (double) WIRE_WIDTH / 2,
            8 - (double) WIRE_WIDTH / 2,
            8 + (double) WIRE_WIDTH / 2,
            8 + (double) WIRE_WIDTH / 2,
            8 + (double) WIRE_WIDTH / 2
        );
    }

    // break box
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(
            8 - (double) WIRE_WIDTH,
            8 - (double) WIRE_WIDTH,
            8 - (double) WIRE_WIDTH,
            8 + (double) WIRE_WIDTH,
            8 + (double) WIRE_WIDTH,
            8 + (double) WIRE_WIDTH
        );
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.PASS;
        //server

        if (player.getMainHandItem().getItem() == Items.STICK) {
            SignalWireBlockEntity entity = ((SignalWireBlockEntity) level.getBlockEntity(pos));
            System.out.println("Signal Value: " + entity.getSignalValue());
        } else if (player.getMainHandItem().isEmpty()) {
            setBlockStateByPlayerInput(state, level, pos, player, hitResult);
        }

        return InteractionResult.SUCCESS;
    }

    private void setBlockStateByPlayerInput(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!player.isCrouching()) {
            if (hitResult.getDirection() == Direction.UP) state = state.setValue(UP, !state.getValue(UP));
            if (hitResult.getDirection() == Direction.DOWN) state = state.setValue(DOWN, !state.getValue(DOWN));
            if (hitResult.getDirection() == Direction.NORTH) state = state.setValue(NORTH, !state.getValue(NORTH));
            if (hitResult.getDirection() == Direction.SOUTH) state = state.setValue(SOUTH, !state.getValue(SOUTH));
            if (hitResult.getDirection() == Direction.EAST) state = state.setValue(EAST, !state.getValue(EAST));
            if (hitResult.getDirection() == Direction.WEST) state = state.setValue(WEST, !state.getValue(WEST));
        } else {
            if (hitResult.getDirection() == Direction.DOWN) state = state.setValue(UP, !state.getValue(UP));
            if (hitResult.getDirection() == Direction.UP) state = state.setValue(DOWN, !state.getValue(DOWN));
            if (hitResult.getDirection() == Direction.SOUTH) state = state.setValue(NORTH, !state.getValue(NORTH));
            if (hitResult.getDirection() == Direction.NORTH) state = state.setValue(SOUTH, !state.getValue(SOUTH));
            if (hitResult.getDirection() == Direction.WEST) state = state.setValue(EAST, !state.getValue(EAST));
            if (hitResult.getDirection() == Direction.EAST) state = state.setValue(WEST, !state.getValue(WEST));
        }

        System.out.println("BlockState Updated");
        SignalWireInformation info = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, pos, null);
        if (info != null) {
            if (signalSourceDetected(level, pos) != null) {
                info.setSignalValue(30);
            } else {
                info.setSignalValue(0);
            }
        }

        level.setBlockAndUpdate(pos, state);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {

        if (!oldState.is(this)) {
            level.invalidateCapabilities(pos);
        }
        if (level.isClientSide()) return;
        //server
        if (level.getBlockEntity(pos) instanceof SignalWireBlockEntity entity) {
            if (signalSourceDetected(level, pos) != null) {
                // set the wire to 30
                entity.setSignalValue(30);
                level.updateNeighborsAt(pos, this);
            }
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if (!state.is(newState.getBlock())) {
            level.invalidateCapabilities(pos);
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if (level.isClientSide()) return;
        //server


        if (level.getBlockEntity(pos) instanceof SignalWireBlockEntity entity) {
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
            if (neighborBlock instanceof SignalWireBlock signalWireBlock) {
                SignalWireInformation info = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, neighborPos, direction);
                // neighbor connection stored in info
                // self connection stored in directionHasConnection
                System.out.println("Direction: " + direction.getName());
                System.out.println("BlockPos: " + "x: " + pos.getX() + ", y:" + pos.getY() + ",z: " + pos.getZ()
                    + ", Neighbor: " + "x: " + neighborPos.getX() + ", y: " + neighborPos.getY() + ", z: " + neighborPos.getZ());
                if (info == null) {
                    System.out.println("Neighbor not connected");
                }
                else if (!SignalWireBlock.directionHasConnection(entity, direction)) {
                    System.out.println("Self not connected");
                }
                else if (info != null && SignalWireBlock.directionHasConnection(entity, direction) && info.getSignalValue() != entity.getSignalValue()) {
                    entity.setSignalValue(info.getSignalValue());
                    level.updateNeighborsAt(pos, this);
                }
            }
            // Const Signal will set the value of the wire to zero when on move
            // Const Signal Block will apply signal the wire automatically when updated (when some signal block destroyed in the circuit)
        }
    }

    @Deprecated
    @Nullable
    private Direction signalSourceDetected(Level level, BlockPos pos) {
        List<BlockPos> positions = List.of(pos.above(), pos.below(), pos.north(), pos.south(), pos.east(), pos.west());
        List<Direction> directions = List.of(Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
        for (int i = 0; i < positions.size(); i++) {
            if (level.getBlockState(positions.get(i)).getBlock() == Blocks.REDSTONE_BLOCK) {
                return directions.get(i);
            }
        }
        return null;
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SignalWireBlockEntity(blockPos, blockState);
    }


}
