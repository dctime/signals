package github.dctime.dctimesignals.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SignalResearchStationBlock extends Block implements EntityBlock {
    public SignalResearchStationBlock(Properties properties) {
        super(properties);

        registerDefaultState(stateDefinition.any().setValue(SCREEN_SIDE_FACING, Direction.NORTH));
    }

    public static final DirectionProperty SCREEN_SIDE_FACING = BlockStateProperties.FACING;

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SCREEN_SIDE_FACING);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof SignalResearchStationBlockEntity entity) {
            entity.reassembleMultiblock();
        }

        if (level.isClientSide()) return InteractionResult.SUCCESS;
        if (level.getBlockEntity(pos) instanceof SignalResearchStationBlockEntity entity) {
            player.displayClientMessage(Component.literal("Multiblock Reassembled!"), false);
            int inputsCount = entity.getSignalInputPositions().size();
            int outputCount = entity.getSignalOutputPositions().size();
            player.displayClientMessage(Component.literal("Signal Inputs: " + inputsCount), false);
            player.displayClientMessage(Component.literal("Signal Outputs: " + outputCount), false);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(SCREEN_SIDE_FACING, context.getNearestLookingDirection().getOpposite());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SignalResearchStationBlockEntity(blockPos, blockState);
    }
}
