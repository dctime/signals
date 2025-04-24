package github.dctime.dctimemod.block;

import github.dctime.dctimemod.RegisterBlockEntities;
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
        //server

        if (player.getMainHandItem().getItem() == Items.STICK) {
            SignalWireBlockEntity entity = ((SignalWireBlockEntity) level.getBlockEntity(pos));
            System.out.println("Signal Strength: "+entity.getSignalStrength()+", Signal Value: "+entity.getSignalValue());
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
        level.setBlockAndUpdate(pos, state);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (level.isClientSide()) return;

        //server
        if (level.getBlockEntity(pos) instanceof SignalWireBlockEntity entity) {
            if (signalSourceDetected(level, pos)) {
                entity.setSignalValue(30);
            }
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        if (level.isClientSide()) return;
        if (level.getBlockEntity(pos) instanceof SignalWireBlockEntity entity) {
            if (signalSourceDetected(level, pos)) {
                entity.setSignalValue(30);
            } else {
                entity.setSignalValue(0);
            }
        }

    }

    private boolean signalSourceDetected(Level level, BlockPos pos) {
        List<BlockPos> positions = List.of(pos.above(), pos.below(), pos.north(), pos.south(), pos.east(), pos.west());
        for (int i = 0; i < positions.size(); i++) {
            if (level.getBlockState(positions.get(i)).getBlock() == Blocks.REDSTONE_BLOCK) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SignalWireBlockEntity(blockPos, blockState);
    }


}
