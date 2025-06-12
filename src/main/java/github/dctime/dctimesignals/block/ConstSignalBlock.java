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

public class ConstSignalBlock extends SignalOutputBlock implements EntityBlock {
    public ConstSignalBlock(Properties properties) {
        super(properties);
    }

    public void updateFromGui(ConstSignalBlockEntity entity, int value) {
        detectSignalWireAndUpdate(entity.getBlockState(), entity.getLevel(), entity.getPos(), true, false, value);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        InteractionResult result = super.useWithoutItem(state, level, pos, player, hitResult);
        if (result == InteractionResult.SUCCESS) {
            return result;
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

    @Override
    @Nullable
    Integer getSignalValue(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof ConstSignalBlockEntity entity) {
            return entity.getOutputSignalValue();
        }
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ConstSignalBlockEntity(blockPos, blockState);
    }
}
