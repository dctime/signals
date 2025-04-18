package github.dctime.dctimemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class BuildHelperBlock extends Block {
    public static final Property<Boolean> DESTROY_MODE = BooleanProperty.create("destroy_mode");
    public BuildHelperBlock(Properties p_49795_) {
        super(p_49795_);

        registerDefaultState(stateDefinition.any()
                .setValue(DESTROY_MODE, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DESTROY_MODE);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            // server
            changeModeByRightClick(state, level, pos, player);
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    private void changeModeByRightClick(BlockState state, Level level, BlockPos pos, Player player) {
        if (player.getItemInHand(InteractionHand.MAIN_HAND) != ItemStack.EMPTY) return;
        if (player.getItemInHand(InteractionHand.OFF_HAND) != ItemStack.EMPTY) return;
        BlockState newState = state.setValue(DESTROY_MODE, !state.getValue(DESTROY_MODE));
        level.setBlock(pos, newState, Block.UPDATE_CLIENTS);
    }

    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        if (state.getValue(DESTROY_MODE)) {
            return super.getDestroyProgress(state, player, level, pos);
        } else {
            return 0.0f;
        }
    }


}
