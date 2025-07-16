package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterBlockEntities;
import github.dctime.dctimesignals.menu.GroundPenetratingSignalEmitterMenu;
import github.dctime.dctimesignals.menu.SignalResearchItemChamberMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class GroundPenetratingSignalEmitterBlock extends Block implements EntityBlock {
    public GroundPenetratingSignalEmitterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GroundPenetratingSignalEmitterBlockEntity(blockPos, blockState);
    }

    @SuppressWarnings("unchecked") // Due to generics, an unchecked cast is necessary here.
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // You can return different tickers here, depending on whatever factors you want. A common use case would be
        // to return different tickers on the client or server, only tick one side to begin with,
        // or only return a ticker for some blockstates (e.g. when using a "my machine is working" blockstate property).
        return type == RegisterBlockEntities.GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK_ENTITY.get() ? (BlockEntityTicker<T>)(BlockEntityTicker<GroundPenetratingSignalEmitterBlockEntity>) GroundPenetratingSignalEmitterBlockEntity::tick : null;
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((containerId, playerInventory, player)-> {
            GroundPenetratingSignalEmitterBlockEntity entity = ((GroundPenetratingSignalEmitterBlockEntity) level.getBlockEntity(pos));
            assert entity != null;
            return new GroundPenetratingSignalEmitterMenu(
                    containerId,
                    playerInventory,
                    ContainerLevelAccess.create(level, pos),
                    entity.getItemStackHandler(),
                    entity.getData()
            );
        },
                Component.translatable("menu.title." + DCtimeMod.MODID + ".ground_penetrating_signal_emitter_menu"));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(state.getMenuProvider(level, pos));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        dropContents(state, level, pos, newState, movedByPiston);
        super.onRemove(state, level, pos, newState, true);
    }

    protected void dropContents(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof GroundPenetratingSignalEmitterBlockEntity blockEntity) {
                if (level instanceof ServerLevel) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                            (blockEntity).getItemStackHandler().getStackInSlot(GroundPenetratingSignalEmitterBlockEntity.ITEMS_PICKAXE_OUTPUT));
                }
            }
        }
    }
}
