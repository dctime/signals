package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SignalResearchStationSignalOutputBlock extends SignalOutputBlock implements EntityBlock {
    public SignalResearchStationSignalOutputBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable Integer getSignalValue(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof SignalResearchStationSignalOutputBlockEntity entity) {
            return entity.getOutputValue();
        }
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SignalResearchStationSignalOutputBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        // You can return different tickers here, depending on whatever factors you want. A common use case would be
        // to return different tickers on the client or server, only tick one side to begin with,
        // or only return a ticker for some blockstates (e.g. when using a "my machine is working" blockstate property).
        return type == RegisterBlockEntities.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT_BLOCK_ENTITY.get() ? (BlockEntityTicker<T>)(BlockEntityTicker<SignalResearchStationSignalOutputBlockEntity>) SignalResearchStationSignalOutputBlockEntity::tick : null;
    }


}
