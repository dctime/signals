package github.dctime.dctimesignals.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SignalResearchStationSignalOutputBlock extends SignalOutputBlock {
    public SignalResearchStationSignalOutputBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable Integer getSignalValue(BlockState state, Level level, BlockPos pos) {
        return 0;
    }
}
