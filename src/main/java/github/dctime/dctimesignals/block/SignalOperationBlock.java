package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.*;
import github.dctime.dctimesignals.item.SignalOperationBaseCardItem;
import github.dctime.dctimesignals.menu.SignalOperationMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SignalOperationBlock extends Block implements EntityBlock {
    public enum SideMode implements net.minecraft.util.StringRepresentable {
        NONE,
        INPUT,
        INPUT2,
        OUTPUT;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }

    public SignalOperationBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(NORTH_SIDE_MODE, SideMode.NONE)
                .setValue(SOUTH_SIDE_MODE, SideMode.NONE)
                .setValue(EAST_SIDE_MODE, SideMode.NONE)
                .setValue(WEST_SIDE_MODE, SideMode.NONE)
                .setValue(UP_SIDE_MODE, SideMode.NONE)
                .setValue(DOWN_SIDE_MODE, SideMode.NONE)
        );

    }

    public static final EnumProperty<SideMode> NORTH_SIDE_MODE = EnumProperty.create("north_side_mode", SideMode.class);
    public static final EnumProperty<SideMode> SOUTH_SIDE_MODE = EnumProperty.create("south_side_mode", SideMode.class);
    public static final EnumProperty<SideMode> EAST_SIDE_MODE = EnumProperty.create("east_side_mode", SideMode.class);
    public static final EnumProperty<SideMode> WEST_SIDE_MODE = EnumProperty.create("west_side_mode", SideMode.class);
    public static final EnumProperty<SideMode> UP_SIDE_MODE = EnumProperty.create("up_side_mode", SideMode.class);
    public static final EnumProperty<SideMode> DOWN_SIDE_MODE = EnumProperty.create("down_side_mode", SideMode.class);
    public static final List<EnumProperty<SideMode>> SIDE_MODES = List.of(NORTH_SIDE_MODE, SOUTH_SIDE_MODE, EAST_SIDE_MODE, WEST_SIDE_MODE, UP_SIDE_MODE, DOWN_SIDE_MODE);

    public static Direction getInputDirection(BlockState state) {
        if (state.getValue(SignalOperationBlock.NORTH_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT) return Direction.NORTH;
        if (state.getValue(SignalOperationBlock.SOUTH_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT) return Direction.SOUTH;
        if (state.getValue(SignalOperationBlock.EAST_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT) return Direction.EAST;
        if (state.getValue(SignalOperationBlock.WEST_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT) return Direction.WEST;
        if (state.getValue(SignalOperationBlock.UP_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT) return Direction.UP;
        if (state.getValue(SignalOperationBlock.DOWN_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT) return Direction.DOWN;
        return null;
    }

    public static Direction getOutputDirection(BlockState state) {
        if (state.getValue(SignalOperationBlock.NORTH_SIDE_MODE) == SignalOperationBlock.SideMode.OUTPUT) return Direction.NORTH;
        if (state.getValue(SignalOperationBlock.SOUTH_SIDE_MODE) == SignalOperationBlock.SideMode.OUTPUT) return Direction.SOUTH;
        if (state.getValue(SignalOperationBlock.EAST_SIDE_MODE) == SignalOperationBlock.SideMode.OUTPUT) return Direction.EAST;
        if (state.getValue(SignalOperationBlock.WEST_SIDE_MODE) == SignalOperationBlock.SideMode.OUTPUT) return Direction.WEST;
        if (state.getValue(SignalOperationBlock.UP_SIDE_MODE) == SignalOperationBlock.SideMode.OUTPUT) return Direction.UP;
        if (state.getValue(SignalOperationBlock.DOWN_SIDE_MODE) == SignalOperationBlock.SideMode.OUTPUT) return Direction.DOWN;
        return null;
    }

    public static Direction getInput2Direction(BlockState state) {
        if (state.getValue(SignalOperationBlock.NORTH_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT2) return Direction.NORTH;
        if (state.getValue(SignalOperationBlock.SOUTH_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT2) return Direction.SOUTH;
        if (state.getValue(SignalOperationBlock.EAST_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT2) return Direction.EAST;
        if (state.getValue(SignalOperationBlock.WEST_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT2) return Direction.WEST;
        if (state.getValue(SignalOperationBlock.UP_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT2) return Direction.UP;
        if (state.getValue(SignalOperationBlock.DOWN_SIDE_MODE) == SignalOperationBlock.SideMode.INPUT2) return Direction.DOWN;
        return null;
    }

    public boolean checkIfSideModesValid(BlockState state, SignalOperationBaseCardItem cardItem) {

        int inputCount = 0;
        int input2Count = 0;
        int outputCount = 0;
        for (EnumProperty<SideMode> mode : SIDE_MODES) {
            if (state.getValue(mode) == SideMode.INPUT) inputCount++;
            if (state.getValue(mode) == SideMode.INPUT2) input2Count++;
            if (state.getValue(mode) == SideMode.OUTPUT) outputCount++;
        }

        if (inputCount > 1 || input2Count > 1 || outputCount > 1) return false;

        int currentConfig = 0;
        if (inputCount == 1) currentConfig += 1;
        if (input2Count == 1) currentConfig += 2;
        if (outputCount == 1) currentConfig += 4;

        return cardItem.checkIfPortsValid(currentConfig);
    }

    @Nullable
    public EnumProperty<SideMode> getSideModeProperty(Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH_SIDE_MODE;
            case SOUTH -> SOUTH_SIDE_MODE;
            case EAST -> EAST_SIDE_MODE;
            case WEST -> WEST_SIDE_MODE;
            case UP -> UP_SIDE_MODE;
            case DOWN -> DOWN_SIDE_MODE;
            default -> null;
        };
    }

    @SuppressWarnings("unchecked") // Due to generics, an unchecked cast is necessary here.
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // You can return different tickers here, depending on whatever factors you want. A common use case would be
        // to return different tickers on the client or server, only tick one side to begin with,
        // or only return a ticker for some blockstates (e.g. when using a "my machine is working" blockstate property).
        return type == RegisterBlockEntities.SIGNAL_OPERATION_BLOCK_ENTITY.get() ? (BlockEntityTicker<T>) SignalOperationBlockEntity::tick : null;
    }

    public static final float WIRE_WIDTH = 4.5f;

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
        if (level.isClientSide()) return super.useWithoutItem(state, level, pos, player, hitResult);
        //server

        if (player.getMainHandItem().getItem() == RegisterItems.SIGNAL_DETECTOR.get()) {

            if (level.getBlockEntity(pos) instanceof SignalOperationBlockEntity entity) {
                Item item = entity.getItems().getStackInSlot(SignalOperationBlockEntity.CARD_SLOT_INDEX).getItem();
                if (item instanceof SignalOperationBaseCardItem cardItem) {
                    if (!checkIfSideModesValid(state, cardItem)) {
                        player.displayClientMessage(Component.literal("Operation Signal Block not Valid").setStyle(Component.literal("").getStyle().withColor(0xFF0000)), false);
                    } else {
                        player.displayClientMessage(Component.literal("Output Signal Value: " + entity.getOutputValue()), false);
                    }
                } else {
                    player.displayClientMessage(Component.literal("Operation Block must insert a card"), false);
                }

            }

            return InteractionResult.SUCCESS;
        }
        if (player.getMainHandItem().getItem() == RegisterItems.SIGNAL_CONFIGURATOR.get()) {
            switchConnectionOutput(hitResult.getDirection(), level, pos);

            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            System.out.println("Opening Menu");
            // server menu
            serverPlayer.openMenu(state.getMenuProvider(level, pos));
            return InteractionResult.SUCCESS;
        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    private void switchConnectionOutput(Direction direction, Level level, BlockPos pos) {
        BlockState oldState = level.getBlockState(pos);
        EnumProperty<SideMode> targetSideModeProperty = getSideModeProperty(direction);

        if (targetSideModeProperty == null) return;
        if (oldState.getValue(targetSideModeProperty) == SideMode.NONE) {
            level.setBlockAndUpdate(pos, oldState
                    .setValue(targetSideModeProperty, SideMode.INPUT)
            );
        } else if (oldState.getValue(targetSideModeProperty) == SideMode.INPUT) {
            level.setBlockAndUpdate(pos, oldState
                    .setValue(targetSideModeProperty, SideMode.INPUT2)
            );
        } else if (oldState.getValue(targetSideModeProperty) == SideMode.INPUT2) {
            level.setBlockAndUpdate(pos, oldState
                    .setValue(targetSideModeProperty, SideMode.OUTPUT)
            );
        } else if (oldState.getValue(targetSideModeProperty) == SideMode.OUTPUT) {
            level.setBlockAndUpdate(pos, oldState
                    .setValue(targetSideModeProperty, SideMode.NONE)
            );
        }
        level.playSound(null, pos, RegisterSoundEvents.CONFIGURATOR_SOUND.get(), SoundSource.PLAYERS);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!oldState.is(this)) {
            level.invalidateCapabilities(pos);
        }

        if (level.isClientSide()) return;

        if (level.getBlockEntity(pos) instanceof SignalOperationBlockEntity entity) {
            Direction outputDirection = getOutputDirection(state);
            if (outputDirection == null) return;

            detectSignalWireAndUpdate(state, level, pos, false, false, entity.getOutputValue(), outputDirection);
        }


    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        dropContents(state, level, pos, newState, movedByPiston);
        if (!state.is(newState.getBlock())) {
            level.invalidateCapabilities(pos);
        }

        if (level.isClientSide()) return;
        Direction outputDirection = getOutputDirection(state);
        if (outputDirection == null) return;
        //
        detectSignalWireAndUpdate(state, level, pos, true, true, 0, outputDirection);

        super.onRemove(state, level, pos, newState, true);
    }

    protected void dropContents(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof SignalOperationBlockEntity blockEntity) {
                if (level instanceof ServerLevel) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                            blockEntity.getItems().getStackInSlot(SignalOperationBlockEntity.CARD_SLOT_INDEX));
                }
            }
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        if (level.isClientSide()) return;
        if (level.getBlockEntity(pos) instanceof SignalOperationBlockEntity entity) {
            // no output direction
            if (getOutputDirection(state) == null) return;
            detectSignalWireAndUpdate(state, level, pos, false, false, entity.getOutputValue(), getOutputDirection(state));
        }
    }

    // for output side
    public static void detectSignalWireAndUpdate(BlockState state, Level level, BlockPos pos, boolean forcefully, boolean noSignal, int signalValue, Direction outputDirection) {
        BlockPos targetPos = pos.relative(outputDirection);

//        System.out.println("pos: x: " + pos.getX() + ", y: " + pos.getY() + ", z:" + pos.getZ());
//        System.out.println("targetPos: x: " + targetPos.getX() + ", y: " + targetPos.getY() + ", z:" + targetPos.getZ());

        SignalWireInformation targetInfo = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, targetPos, outputDirection);
        if (targetInfo == null) return;
//        System.out.println("got signal wire");
        // higher signal value dominates lower signal value
        // onBreak set to 0 forcefully to prevent edge case that the wire remains the signal the signal block sends
        if (targetInfo.getSignalValue() != null) {
            if (targetInfo.getSignalValue() >= signalValue && !forcefully) return;
        }

        if (noSignal) {
            if (targetInfo.getSignalValue() == null) {
//                System.out.println("signal value changed from NULL to NULL");
            } else {
//                System.out.println("signal value changed from " + targetInfo.getSignalValue() + " to NULL");
            }

            targetInfo.setNoSignal();
        } else {
            if (targetInfo.getSignalValue() == null) {
//                System.out.println("signal value changed from NULL to " + signalValue);
            } else {
//                System.out.println("signal value changed from " + targetInfo.getSignalValue() + " to " + signalValue);
            }

            targetInfo.setSignalValue(signalValue);
        }

        level.updateNeighborsAt(targetPos, level.getBlockState(targetPos).getBlock());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH_SIDE_MODE, SOUTH_SIDE_MODE, EAST_SIDE_MODE, WEST_SIDE_MODE, UP_SIDE_MODE, DOWN_SIDE_MODE);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SignalOperationBlockEntity(blockPos, blockState);
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((containerId, playerInventory, player)-> {
            SignalOperationBlockEntity entity = ((SignalOperationBlockEntity) level.getBlockEntity(pos));
            assert entity != null;
            return new SignalOperationMenu(
                containerId,
                playerInventory,
                ContainerLevelAccess.create(level, pos),
                entity.getData(),
                entity.getItems(),
                    entity.getLevel(),
                    entity.getBlockPos());
        },
            Component.translatable("menu.title." + DCtimeMod.MODID + ".signal_operation_menu"));
    }


}
