package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterBlockEntities;
import github.dctime.dctimesignals.menu.SignalOperationMenu;
import github.dctime.dctimesignals.menu.SignalResearchItemChamberMenu;
import github.dctime.dctimesignals.screen.SignalResearchItemChamberScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
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

public class SignalResearchItemChamberBlock extends Block implements EntityBlock {
    public SignalResearchItemChamberBlock(Properties properties) {
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


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SignalResearchItemChamberBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // You can return different tickers here, depending on whatever factors you want. A common use case would be
        // to return different tickers on the client or server, only tick one side to begin with,
        // or only return a ticker for some blockstates (e.g. when using a "my machine is working" blockstate property).
        return type == RegisterBlockEntities.SIGNAL_RESEARCH_ITEM_CHAMBER_BLOCK_ENTITY.get() ? (BlockEntityTicker<T>)(BlockEntityTicker<SignalResearchItemChamberBlockEntity>) SignalResearchItemChamberBlockEntity::tick : null;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(state.getMenuProvider(level, pos));
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        dropContents(state, level, pos, newState, movedByPiston);
        super.onRemove(state, level, pos, newState, true);
    }

    protected void dropContents(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof SignalResearchItemChamberBlockEntity blockEntity) {
                if (level instanceof ServerLevel) {
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                            (blockEntity).getItems().getStackInSlot(SignalResearchItemChamberBlockEntity.ITEMS_INPUT_1_INDEX));
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                            (blockEntity).getItems().getStackInSlot(SignalResearchItemChamberBlockEntity.ITEMS_INPUT_2_INDEX));
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                            (blockEntity).getItems().getStackInSlot(SignalResearchItemChamberBlockEntity.ITEMS_INPUT_3_INDEX));
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                            (blockEntity).getItems().getStackInSlot(SignalResearchItemChamberBlockEntity.ITEMS_OUTPUT_INDEX));
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                            (blockEntity).getResearchingItem().getStackInSlot(SignalResearchItemChamberBlockEntity.RESEARCHING_ITEM_INPUT_1_INDEX).copy());
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                            (blockEntity).getResearchingItem().getStackInSlot(SignalResearchItemChamberBlockEntity.RESEARCHING_ITEM_INPUT_2_INDEX).copy());
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                            (blockEntity).getResearchingItem().getStackInSlot(SignalResearchItemChamberBlockEntity.RESEARCHING_ITEM_INPUT_3_INDEX).copy());

                }
            }
        }
    }

    @Override
    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((containerId, playerInventory, player)-> {
            SignalResearchItemChamberBlockEntity entity = ((SignalResearchItemChamberBlockEntity) level.getBlockEntity(pos));
            assert entity != null;
            return new SignalResearchItemChamberMenu(
                    containerId,
                    playerInventory,
                    ContainerLevelAccess.create(level, pos),
                    entity.getItems(),
                    entity.getData(),
                    entity.getResearchingItem()
            );
        },
                Component.translatable("menu.title." + DCtimeMod.MODID + ".signal_item_chamber_menu"));
    }
}
