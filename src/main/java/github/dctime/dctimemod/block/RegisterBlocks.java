package github.dctime.dctimemod.block;

import github.dctime.dctimemod.DCtimeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegisterBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DCtimeMod.MODID);

    public static final DeferredBlock<Block> BUILD_HELPER_BLOCK = BLOCKS.register(
      "build_helper_block",
            (registryName) -> new BuildHelperBlock(BlockBehaviour.Properties.of()
                    .setId(ResourceKey.create(Registries.BLOCK, registryName))
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
                    .lightLevel((blockState) -> {
                        if (blockState.getValue(BuildHelperBlock.DESTROY_MODE)) {
                            return 0;
                        } else {
                            return 15;
                        }
                    })
                    .instabreak()
            )
    );
}
