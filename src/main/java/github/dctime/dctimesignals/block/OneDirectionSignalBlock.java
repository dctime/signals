package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterItems;
import github.dctime.dctimesignals.RegisterSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public abstract class OneDirectionSignalBlock extends Block {
    public OneDirectionSignalBlock(Properties properties) {
        super(properties);
    }

    protected final Direction[] directions = {
            Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST
    };

    protected void setToNextDirection(BlockState state, Level level, BlockPos pos, EnumProperty<Direction> directionEnumProperty) {
        Direction currentDirection = state.getValue(directionEnumProperty);
        for (int i = 0; i < 6; i++) {
            if (currentDirection == directions[i]) {
                if (i == 5) {
                    level.setBlockAndUpdate(pos, state.setValue(directionEnumProperty, directions[0]));
                } else {
                    level.setBlockAndUpdate(pos, state.setValue(directionEnumProperty, directions[i+1]));
                }
            }
        }
    }

    @Nullable
    abstract Integer getSignalValue(BlockState state, Level level, BlockPos pos);

    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult, EnumProperty<Direction> directionEnumProperty, String noSignalText, String hasSignalText) {
        if (!level.isClientSide && player.getMainHandItem().getItem() == RegisterItems.SIGNAL_DETECTOR.get()) {
            Integer value = getSignalValue(state, level, pos);
            if (value == null)
                player.displayClientMessage(Component.literal(noSignalText), false);
            else
                player.displayClientMessage(Component.literal(hasSignalText + value), false);

            return InteractionResult.SUCCESS;
        }

        if (!level.isClientSide && player.getMainHandItem().getItem() == RegisterItems.SIGNAL_CONFIGURATOR.get()) {
            setToNextDirection(state, level, pos, directionEnumProperty);
            level.playSound(null, pos, RegisterSoundEvents.CONFIGURATOR_SOUND.get(), SoundSource.PLAYERS);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
}
