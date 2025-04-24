package github.dctime.dctimemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.CubeVoxelShape;
import net.minecraft.world.phys.shapes.VoxelShape;


public class SignalWireBlock extends Block {
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

    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    public static final int WIRE_WIDTH = 4;
    // collision box
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(
            8- (double) WIRE_WIDTH /2,
            8- (double) WIRE_WIDTH /2,
            8- (double) WIRE_WIDTH /2,
            8+ (double) WIRE_WIDTH /2,
            8+ (double) WIRE_WIDTH /2,
            8+ (double) WIRE_WIDTH /2
        );
    }

    // break box
    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(
            8- (double) WIRE_WIDTH,
            8- (double) WIRE_WIDTH,
            8- (double) WIRE_WIDTH,
            8+ (double) WIRE_WIDTH,
            8+ (double) WIRE_WIDTH,
            8+ (double) WIRE_WIDTH
        );
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.PASS;

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


        level.setBlockAndUpdate(pos, state);
        return InteractionResult.SUCCESS;
    }
}
