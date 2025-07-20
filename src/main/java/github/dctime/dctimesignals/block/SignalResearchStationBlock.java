package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterBlockEntities;
import github.dctime.dctimesignals.RegisterItems;
import github.dctime.dctimesignals.RegisterSoundEvents;
import github.dctime.dctimesignals.menu.SignalOperationMenu;
import github.dctime.dctimesignals.menu.SignalResearchMenu;
import github.dctime.dctimesignals.screen.SignalResearchScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == RegisterBlockEntities.SIGNAL_RESEARCH_STATION_BLOCK_ENTITY.get() ? (BlockEntityTicker<T>) (BlockEntityTicker<SignalResearchStationBlockEntity>)SignalResearchStationBlockEntity::tick : null;
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (player.getMainHandItem().is(RegisterItems.SIGNAL_CONFIGURATOR)) {
            if (level.getBlockEntity(pos) instanceof SignalResearchStationBlockEntity entity) {
                // do this both in server and client cuz screen needs pos while hard to send pos data to client
                entity.reassembleMultiblock(player);
                entity.showDebugOutline();
                level.playSound(null, pos, RegisterSoundEvents.CONFIGURATOR_SOUND.get(), SoundSource.PLAYERS);
                return InteractionResult.SUCCESS;
            }
        }

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(state.getMenuProvider(level, pos));
            return InteractionResult.SUCCESS;
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

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((containerId, playerInventory, player)-> {
            SignalResearchStationBlockEntity entity = ((SignalResearchStationBlockEntity) level.getBlockEntity(pos));
            assert entity != null;
            return new SignalResearchMenu(
                    containerId,
                    playerInventory,
                    ContainerLevelAccess.create(level, pos),
                    entity.getInputSignalData(),
                    entity.getOutputSignalData(),
                    entity.getRequiredInputSignalData(),
                    entity.getFlagsData()
            );
        },
                Component.translatable("menu.title." + DCtimeMod.MODID + ".signal_research_menu"));
    }
}
