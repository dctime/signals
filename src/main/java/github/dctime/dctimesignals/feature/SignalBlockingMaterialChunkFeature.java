package github.dctime.dctimesignals.feature;

import com.mojang.serialization.Codec;
import github.dctime.dctimesignals.configuration.SignalBlockingMaterialChunkConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.function.Predicate;

public class SignalBlockingMaterialChunkFeature extends Feature<SignalBlockingMaterialChunkConfiguration> {
    public SignalBlockingMaterialChunkFeature(Codec<SignalBlockingMaterialChunkConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SignalBlockingMaterialChunkConfiguration> featurePlaceContext) {
        BlockPos originPos = featurePlaceContext.origin();
        WorldGenLevel level = featurePlaceContext.level();
        RandomSource random = featurePlaceContext.random();

        if (!level.getBlockState(originPos).is(Blocks.STONE)) {
            return false;
        }

        for (int x = -random.nextIntBetweenInclusive(0, 8); x <= random.nextIntBetweenInclusive(0, 8); x++) {
            for (int y = -random.nextIntBetweenInclusive(0, 8); y <= random.nextIntBetweenInclusive(0, 8); y++) {
                for (int z = -random.nextIntBetweenInclusive(0, 8); z <= random.nextIntBetweenInclusive(0, 8); z++) {
                    BlockPos pos = originPos.offset(x, y, z);
                    if (level.getBlockState(pos).is(Blocks.STONE)) {
                        level.setBlock(pos, Blocks.DIRT.defaultBlockState(), 3);
                    }
                }
            }
        }

        return true;
    }
}
