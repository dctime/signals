package github.dctime.dctimesignals.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class SignalResearchStationBlock extends Block {
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
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return (BlockState)this.defaultBlockState().setValue(SCREEN_SIDE_FACING, context.getNearestLookingDirection().getOpposite());
    }
}
