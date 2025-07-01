package github.dctime.dctimesignals.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
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
}
