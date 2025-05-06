package github.dctime.dctimemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SignalOperationBlock extends Block implements EntityBlock {
    public enum SideMode implements net.minecraft.util.StringRepresentable {
        NONE,
        INPUT,
        OUTPUT;

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
    public SignalOperationBlock(Properties properties) {
        super(properties);
    }

    public static final EnumProperty<SideMode> NORTH_SIDE_MODE = EnumProperty.create("north_side_mode", SideMode.class);
    public static final EnumProperty<SideMode> SOUTH_SIDE_MODE = EnumProperty.create("south_side_mode", SideMode.class);
    public static final EnumProperty<SideMode> EAST_SIDE_MODE = EnumProperty.create("east_side_mode", SideMode.class);
    public static final EnumProperty<SideMode> WEST_SIDE_MODE = EnumProperty.create("west_side_mode", SideMode.class);
    public static final EnumProperty<SideMode> UP_SIDE_MODE = EnumProperty.create("up_side_mode", SideMode.class);
    public static final EnumProperty<SideMode> DOWN_SIDE_MODE = EnumProperty.create("down_side_mode", SideMode.class);

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

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.CONSUME;
        //server

        if (player.getMainHandItem().isEmpty()) {
            System.out.println("Player hand is empty");
            if (!player.isCrouching())
                switchConnectionOutput(hitResult.getDirection(), level, pos);
            else
                switchConnectionOutput(hitResult.getDirection().getOpposite(), level, pos);
        }

        return InteractionResult.SUCCESS;
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
                    .setValue(targetSideModeProperty, SideMode.OUTPUT)
            );
        } else if (oldState.getValue(targetSideModeProperty) == SideMode.OUTPUT) {
            level.setBlockAndUpdate(pos, oldState
                    .setValue(targetSideModeProperty, SideMode.NONE)
            );
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH_SIDE_MODE, SOUTH_SIDE_MODE, EAST_SIDE_MODE, WEST_SIDE_MODE, UP_SIDE_MODE, DOWN_SIDE_MODE);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SignalOperationBlockEntity(blockPos, blockState);
    }
}
