package github.dctime.dctimemod.block;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class SignalToRedstoneConverter extends SignalWireBlock {
    private HashMap<Direction, BooleanProperty> directionToRedstoneProperty;


    public SignalToRedstoneConverter(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(SOUTH, false)
                .setValue(EAST, false)
                .setValue(WEST, false)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(SignalWireBlock.NORTH, false)
                .setValue(SignalWireBlock.SOUTH, false)
                .setValue(SignalWireBlock.EAST, false)
                .setValue(SignalWireBlock.WEST, false)
                .setValue(SignalWireBlock.UP, false)
                .setValue(SignalWireBlock.DOWN, false)
        );

        directionToRedstoneProperty = new HashMap<>();


        directionToRedstoneProperty.put(Direction.NORTH, NORTH);
        directionToRedstoneProperty.put(Direction.SOUTH, SOUTH);
        directionToRedstoneProperty.put(Direction.EAST, EAST);
        directionToRedstoneProperty.put(Direction.WEST, WEST);
        directionToRedstoneProperty.put(Direction.UP, UP);
        directionToRedstoneProperty.put(Direction.DOWN, DOWN);


    }

    public static final BooleanProperty NORTH = BooleanProperty.create("redstone_output_north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("redstone_output_south");
    public static final BooleanProperty EAST = BooleanProperty.create("redstone_output_east");
    public static final BooleanProperty WEST = BooleanProperty.create("redstone_output_west");
    public static final BooleanProperty UP = BooleanProperty.create("redstone_output_up");
    public static final BooleanProperty DOWN = BooleanProperty.create("redstone_output_down");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, SOUTH, EAST, WEST, UP, DOWN);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {

            return InteractionResult.CONSUME;
        }

        Item mainHandItem = player.getMainHandItem().getItem();
        if (mainHandItem == Items.REDSTONE) {
            System.out.println("Player hand has redstone");
            switchRedstoneOutput(hitResult.getDirection(), level, pos);
        } else if (mainHandItem == Items.STICK) {
            SignalWireBlockEntity entity = ((SignalWireBlockEntity) level.getBlockEntity(pos));
            System.out.println("Signal Value: " + entity.getSignalValue());
            player.displayClientMessage(Component.literal("Signal Value: " + entity.getSignalValue()), true);
        } else if (player.getMainHandItem().isEmpty()) {
            System.out.println("Adjusting using empty hand");
            if (!player.isCrouching())
                switchConnectionOutput(hitResult.getDirection(), level, pos);
            else
                switchConnectionOutput(hitResult.getDirection().getOpposite(), level, pos);
        }

        updateWireValue(state, level, pos);

        return InteractionResult.SUCCESS;
    }

    private void switchConnectionOutput(Direction direction, Level level, BlockPos pos) {
        BlockState oldState = level.getBlockState(pos);
        BooleanProperty targetRedstoneProperty = directionToRedstoneProperty.get(direction);
        BooleanProperty targetConnectionProperty = directionToConnectionProperty.get(direction);

        boolean isOldConnection = oldState.getValue(targetConnectionProperty);

        if (!isOldConnection)
            level.setBlockAndUpdate(pos, oldState
                    .setValue(targetConnectionProperty, true)
                    .setValue(targetRedstoneProperty, false)

            );
        else
            level.setBlockAndUpdate(pos, oldState
                    .setValue(targetConnectionProperty, false)
                    .setValue(targetRedstoneProperty, false)
            );
    }

    private void switchRedstoneOutput(Direction direction, Level level, BlockPos pos) {

        BlockState oldState = level.getBlockState(pos);
        BooleanProperty targetRedstoneProperty = directionToRedstoneProperty.get(direction);
        BooleanProperty targetConnectionProperty = directionToConnectionProperty.get(direction);

        boolean isOldRedstone = oldState.getValue(targetRedstoneProperty);

        if (!isOldRedstone)
        level.setBlockAndUpdate(pos, oldState
                .setValue(targetRedstoneProperty, true)
                .setValue(targetConnectionProperty, false)
        );
        else
            level.setBlockAndUpdate(pos, oldState
                    .setValue(targetRedstoneProperty, false)
                    .setValue(targetConnectionProperty, false)
            );
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        if (state.getValue(directionToRedstoneProperty.get(direction.getOpposite()))
        && level.getBlockEntity(pos) instanceof SignalWireBlockEntity entity) {
            return Math.min(entity.getSignalValue(), 15);
        }
        return 0;
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }
}
