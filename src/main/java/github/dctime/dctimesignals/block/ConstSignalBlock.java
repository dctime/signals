package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterCapabilities;
import github.dctime.dctimesignals.RegisterItems;
import github.dctime.dctimesignals.menu.ConstSignalMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
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

public class ConstSignalBlock extends Block implements EntityBlock {
    public static final EnumProperty<Direction> OUTPUT_DIRECTION = DirectionProperty.create("output_direction", Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN);

    public ConstSignalBlock(Properties properties) {
        super(properties);
    }

    private final Direction[] directions = {
            Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST
    };

    private void setToNextDirection(BlockState state, Level level, BlockPos pos) {
        Direction currentDirection = state.getValue(OUTPUT_DIRECTION);
        for (int i = 0; i < 6; i++) {
            if (currentDirection == directions[i]) {
                if (i == 5) {
                    level.setBlockAndUpdate(pos, state.setValue(OUTPUT_DIRECTION, directions[0]));
                } else {
                    level.setBlockAndUpdate(pos, state.setValue(OUTPUT_DIRECTION, directions[i+1]));
                }
            }
        }
    }


    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!oldState.is(this)) {
            level.invalidateCapabilities(pos);
        }

        if (level.isClientSide()) return;

        if (level.getBlockEntity(pos) instanceof ConstSignalBlockEntity entity)
        detectSignalWireAndUpdate(state, level, pos, false, false, entity.getOutputSignalValue());

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

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        if (level.isClientSide()) return;
        if (level.getBlockEntity(pos) instanceof ConstSignalBlockEntity entity) {
            detectSignalWireAndUpdate(state, level, pos, false, false, entity.getOutputSignalValue());
        }
    }

    public void updateFromGui(ConstSignalBlockEntity entity, int value) {
        detectSignalWireAndUpdate(entity.getBlockState(), entity.getLevel(), entity.getPos(), true, false, value);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && player.getMainHandItem().getItem() == RegisterItems.SIGNAL_DETECTOR.get()) {
            ConstSignalBlockEntity entity = (ConstSignalBlockEntity) level.getBlockEntity(pos);
            if (entity == null) return InteractionResult.FAIL;
            player.displayClientMessage(Component.literal("Const Output Signal Value: " + entity.getOutputSignalValue()), false);

            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide && player.getMainHandItem().getItem() == RegisterItems.SIGNAL_CONFIGURATOR.get()) {
            setToNextDirection(state, level, pos);
            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(state.getMenuProvider(level, pos));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((containerId, playerInventory, player)-> {
            ConstSignalBlockEntity entity = ((ConstSignalBlockEntity) level.getBlockEntity(pos));
            return new ConstSignalMenu(
                    containerId,
                    playerInventory,
                    ContainerLevelAccess.create(level, pos),
                    entity.data,
                    entity
            );
        },
                Component.translatable("menu.title.dctimemod.const_signal_block_menu"));
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OUTPUT_DIRECTION);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ConstSignalBlockEntity(blockPos, blockState);
    }
}
