package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterCapabilities;
import github.dctime.dctimesignals.RegisterItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;


public class SignalWireBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
    private final VoxelShape BASE = Block.box(
            8 - (double) WIRE_WIDTH / 2,
            8 - (double) WIRE_WIDTH / 2,
            8 - (double) WIRE_WIDTH / 2,
            8 + (double) WIRE_WIDTH / 2,
            8 + (double) WIRE_WIDTH / 2,
            8 + (double) WIRE_WIDTH / 2
    );

    private final VoxelShape EAST_SHAPE = Block.box(8 + WIRE_WIDTH / 2, 8 - WIRE_WIDTH / 2, 8 - WIRE_WIDTH / 2,
            16, 8 + WIRE_WIDTH / 2, 8 + WIRE_WIDTH / 2);
    private final VoxelShape WEST_SHAPE = Block.box(0, 8 - WIRE_WIDTH / 2, 8 - WIRE_WIDTH / 2,
            8 - WIRE_WIDTH / 2, 8 + WIRE_WIDTH / 2, 8 + WIRE_WIDTH / 2);
    private final VoxelShape SOUTH_SHAPE = Block.box(8 - WIRE_WIDTH / 2, 8 - WIRE_WIDTH / 2, 8 + WIRE_WIDTH / 2,
            8 + WIRE_WIDTH / 2, 8 + WIRE_WIDTH / 2, 16);
    private final VoxelShape NORTH_SHAPE = Block.box(8 - WIRE_WIDTH / 2, 8 - WIRE_WIDTH / 2, 0,
            8 + WIRE_WIDTH / 2, 8 + WIRE_WIDTH / 2, 8 - WIRE_WIDTH / 2);
    private final VoxelShape UP_SHAPE = Block.box(8 - WIRE_WIDTH / 2, 8 + WIRE_WIDTH / 2, 8 - WIRE_WIDTH / 2,
            8 + WIRE_WIDTH / 2, 16, 8 + WIRE_WIDTH / 2);
    private final VoxelShape DOWN_SHAPE = Block.box(8 - WIRE_WIDTH / 2, 0, 8 - WIRE_WIDTH / 2,
            8 + WIRE_WIDTH / 2, 8 - WIRE_WIDTH / 2, 8 + WIRE_WIDTH / 2);

    VoxelShape[] shapes = new VoxelShape[2 * 2 * 2 * 2 * 2 * 2];
    public static HashMap<VoxelShape, Direction> voxelShapeDirectionPair;
    public static HashMap<Direction, BooleanProperty> directionToConnectionProperty;

    public SignalWireBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(WATERLOGGED, false)
        );

        directionToConnectionProperty = new HashMap<>();
        voxelShapeDirectionPair = new HashMap<>();

        directionToConnectionProperty.put(Direction.NORTH, SignalWireBlock.NORTH);
        directionToConnectionProperty.put(Direction.SOUTH, SignalWireBlock.SOUTH);
        directionToConnectionProperty.put(Direction.EAST, SignalWireBlock.EAST);
        directionToConnectionProperty.put(Direction.WEST, SignalWireBlock.WEST);
        directionToConnectionProperty.put(Direction.UP, SignalWireBlock.UP);
        directionToConnectionProperty.put(Direction.DOWN, SignalWireBlock.DOWN);

        voxelShapeDirectionPair.put(NORTH_SHAPE, Direction.NORTH);
        voxelShapeDirectionPair.put(SOUTH_SHAPE, Direction.SOUTH);
        voxelShapeDirectionPair.put(EAST_SHAPE, Direction.EAST);
        voxelShapeDirectionPair.put(WEST_SHAPE, Direction.WEST);
        voxelShapeDirectionPair.put(UP_SHAPE, Direction.UP);
        voxelShapeDirectionPair.put(DOWN_SHAPE, Direction.DOWN);
        voxelShapeDirectionPair.put(BASE, null);

        makeShapes();
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return SignalWireBlockEntity::tick;
    }

    public static final BooleanProperty NORTH = BooleanProperty.create("signal_wire_north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("signal_wire_south");
    public static final BooleanProperty EAST = BooleanProperty.create("signal_wire_east");
    public static final BooleanProperty WEST = BooleanProperty.create("signal_wire_west");
    public static final BooleanProperty UP = BooleanProperty.create("signal_wire_up");
    public static final BooleanProperty DOWN = BooleanProperty.create("signal_wire_down");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

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
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN, WATERLOGGED);
    }

    public static final float WIRE_WIDTH = 4f;

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }


    private void makeShapes() {
        // 000000 north south east west up down
        for (int i = 0; i < 2 * 2 * 2 * 2 * 2 * 2; i++) {
            VoxelShape shape = Shapes.empty();
            shape = Shapes.or(shape, BASE);
            if ((i & 1) == 1)
                shape = Shapes.or(shape, NORTH_SHAPE);
            if ((i & 2) == 2)
                shape = Shapes.or(shape, SOUTH_SHAPE);
            if ((i & 4) == 4)
                shape = Shapes.or(shape, EAST_SHAPE);
            if ((i & 8) == 8)
                shape = Shapes.or(shape, WEST_SHAPE);
            if ((i & 16) == 16)
                shape = Shapes.or(shape, UP_SHAPE);
            if ((i & 32) == 32)
                shape = Shapes.or(shape, DOWN_SHAPE);
            shapes[i] = shape;
        }
    }

    // break box
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int shapeIndex = 0;

        if (state.getValue(NORTH)) {
            shapeIndex += 1;
        }
        if (state.getValue(SOUTH)) {
            shapeIndex += 2;
        }
        if (state.getValue(EAST)) {
            shapeIndex += 4;
        }
        if (state.getValue(WEST)) {
            shapeIndex += 8;
        }
        if (state.getValue(UP)) {
            shapeIndex += 16;
        }
        if (state.getValue(DOWN)) {
            shapeIndex += 32;
        }

        return shapes[shapeIndex];
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        //server

        if (player.getMainHandItem().getItem() == RegisterItems.SIGNAL_DETECTOR.get()) {
            SignalWireBlockEntity entity = ((SignalWireBlockEntity) level.getBlockEntity(pos));
            Integer signalValue = entity.getSignalValue();
            if (signalValue == null) player.displayClientMessage(Component.literal("No Signal"), false);
            else player.displayClientMessage(Component.literal("Signal Value: " + signalValue), false);

            return InteractionResult.CONSUME;
        } else if (player.getMainHandItem().getItem() == RegisterItems.SIGNAL_CONFIGURATOR.get()) {
            Direction accessingDirection = getPlayerLookingAtModel(player, state, level, pos);
            if (accessingDirection == null) {
                System.out.println("AccessingDirection is null");
                accessingDirection = hitResult.getDirection();
            }
            switchConnectionOutput(accessingDirection, level, pos, player, null);
        }

        // changing the wire configuration may cause wire connection change
        updateWireValue(state, level, pos);

        return InteractionResult.CONSUME;
    }

    public float getBlockReachDistance(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
        if (attribute == null) {
            return (float) Attributes.BLOCK_INTERACTION_RANGE.value().getDefaultValue();
        }
        return (float) attribute.getValue();
    }


    protected @Nullable Direction getPlayerLookingAtModel(Player player, BlockState state, BlockGetter world, BlockPos pos) {
        Vec3 start = player.getEyePosition(1F);
        Vec3 end = start.add(player.getLookAngle().normalize().scale(getBlockReachDistance(player)));

        double minDistance = Double.MAX_VALUE;
        VoxelShape targetVoxel = Shapes.empty();

        for (VoxelShape shape : voxelShapeDirectionPair.keySet()) {
            // TODO: Turn direction into property
            Direction direction = voxelShapeDirectionPair.get(shape);
            if (direction != null) { // if voxel is BASE
                BooleanProperty checkProperty = directionToConnectionProperty.get(direction);
                boolean hasConnection = state.getValue(checkProperty);

                // dont check voxels that doesnt have connection
                if (!hasConnection) continue;
            }

            double d = getDistanceBetweenEyeAndVoxelShape(state, world, pos, start, end, shape);
            if (d < minDistance) {
                targetVoxel = shape;
                minDistance = d;
            }
        }

        if (targetVoxel.isEmpty()) return null;
        return voxelShapeDirectionPair.get(targetVoxel);
    }

    private double getDistanceBetweenEyeAndVoxelShape(BlockState state, BlockGetter world, BlockPos pos, Vec3 start, Vec3 end, VoxelShape shape) {
        BlockHitResult blockRayTraceResult = world.clipWithInteractionOverride(start, end, pos, shape, state);
        if (blockRayTraceResult == null) {
            return Double.MAX_VALUE;
        }
        return blockRayTraceResult.getLocation().distanceTo(start);
    }

    protected void switchConnectionOutput(Direction direction, Level level, BlockPos pos, Player player, @Nullable BooleanProperty targetRedstoneProperty) {
        BlockState oldState = level.getBlockState(pos);
        BooleanProperty targetConnectionProperty = directionToConnectionProperty.get(direction);

        // if the connection is already extended
        boolean isOldConnection = oldState.getValue(targetConnectionProperty);
        BlockPos effectNeighborPos = pos.relative(direction);

        BooleanProperty effectNeighborConnectionProperty = directionToConnectionProperty.get(direction.getOpposite());

        if (!isOldConnection) {
            if (targetRedstoneProperty == null) {
                level.setBlockAndUpdate(pos, oldState
                        .setValue(targetConnectionProperty, true)
                );
            } else {
                level.setBlockAndUpdate(pos, oldState
                        .setValue(targetConnectionProperty, true)
                        .setValue(targetRedstoneProperty, false)
                );
            }

            // extend neighbor too
            if (level.getBlockState(effectNeighborPos).getBlock() instanceof SignalWireBlock neighborBlock) {
                level.setBlockAndUpdate(
                        effectNeighborPos,
                        level.getBlockState(effectNeighborPos).setValue(
                                effectNeighborConnectionProperty, true
                        )
                );
            }

        } else {
            if (targetRedstoneProperty == null) {
                level.setBlockAndUpdate(pos, oldState
                        .setValue(targetConnectionProperty, false)
                );
            } else {
                level.setBlockAndUpdate(pos, oldState
                        .setValue(targetConnectionProperty, false)
                        .setValue(targetRedstoneProperty, false)
                );
            }

            // remove neighbor connection
            if (level.getBlockState(effectNeighborPos).getBlock() instanceof SignalWireBlock neighborBlock) {
                level.setBlockAndUpdate(
                        effectNeighborPos,
                        level.getBlockState(effectNeighborPos).setValue(
                                effectNeighborConnectionProperty, false
                        )
                );
            }
        }

    }


    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {

        if (!oldState.is(this)) {
            level.invalidateCapabilities(pos);
        }
        if (level.isClientSide()) return;
        //server
        updateWireValue(state, level, pos);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        super.onRemove(state, level, pos, newState, movedByPiston);
        if (!state.is(newState.getBlock())) {
            level.invalidateCapabilities(pos);
        }
        if (level.isClientSide()) return;
        updateWireValue(state, level, pos);

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
//                System.out.println("Direction: " + direction.getName());
//                System.out.println("BlockPos: " + "x: " + pos.getX() + ", y:" + pos.getY() + ",z: " + pos.getZ()
//                    + ", Neighbor: " + "x: " + neighborPos.getX() + ", y: " + neighborPos.getY() + ", z: " + neighborPos.getZ());
                if (info == null) {
//                    System.out.println("Neighbor not connected");
                } else if (!SignalWireBlock.directionHasConnection(entity, direction)) {
//                    System.out.println("Self not connected");
                } else if (info != null && SignalWireBlock.directionHasConnection(entity, direction)) {
                    if (info.getSignalValue() == null && entity.getSignalValue() == null) return;
                    if ((info.getSignalValue() != null && entity.getSignalValue() != null) &&
                            (info.getSignalValue().intValue() == entity.getSignalValue().intValue())) return;

                    if (info.getSignalValue() == null) {
                        entity.setNoSignal();
                    } else {
                        entity.setSignalValue(info.getSignalValue());
                    }

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

    // call this function if there is a chance that the wires value is changed
    protected void updateWireValue(BlockState state, Level level, BlockPos pos) {
        // update the wire
        if (level.getBlockEntity(pos) instanceof SignalWireBlockEntity entity) {
            entity.getInformation().setNoSignal();
        }

        BooleanProperty[] directionProperties = {NORTH, SOUTH, EAST, WEST, UP, DOWN};
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN};
        for (int i = 0; i < 6; i++) {
            // wire extends connection to certain direction
            if (state.getValue(directionProperties[i])) {
                BlockPos nearbyPos = pos.relative(directions[i]);
                SignalWireInformation info = level.getCapability(
                        RegisterCapabilities.SIGNAL_VALUE,
                        nearbyPos,
                        directions[i]
                );

                if (info != null) {
                    // for self connection break case like editing connection and breaking block
                    info.setNoSignal();
                    level.updateNeighborsAt(nearbyPos, this);
                }
            }
        }
        // for signal block nearby case
        level.updateNeighborsAt(pos, this);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return !(Boolean) state.getValue(WATERLOGGED);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return (Boolean) state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return super.getStateForPlacement(context).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }
}
