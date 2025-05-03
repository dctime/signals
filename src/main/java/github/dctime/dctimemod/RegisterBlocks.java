package github.dctime.dctimemod;

import github.dctime.dctimemod.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegisterBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(DCtimeMod.MODID);

    public static final DeferredBlock<Block> BUILD_HELPER_BLOCK = BLOCKS.registerBlock(
            "build_helper_block",
            BuildHelperBlock::new,
            BlockBehaviour.Properties.of()
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
                    .noOcclusion()
                    .instabreak()
    );

    public static final DeferredBlock<Block> FLAWLESS_EXCHANGER = BLOCKS.registerBlock(
            "flawless_exchanger",
            FlawlessExchangerBlock::new,
            BlockBehaviour.Properties.of()
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
                    .noOcclusion()
    );

    public static final DeferredBlock<Block> SINGAL_WIRE = BLOCKS.registerBlock(
            "signal_wire",
            SignalWireBlock::new,
            BlockBehaviour.Properties.of()
                    .destroyTime(1.0f)
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
                    .noOcclusion()
    );

    public static final DeferredBlock<Block> CONSTANT_SIGNAL_BLOCK = BLOCKS.registerBlock(
            "const_signal_block",
            ConstSignalBlock::new,
            BlockBehaviour.Properties.of()
                    .destroyTime(1.0f)
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
    );

    public static final DeferredBlock<Block> SINGAL_TO_REDSTONE_CONVERTER = BLOCKS.registerBlock(
            "signal_to_redstone_converter",
            SignalToRedstoneConverter::new,
            BlockBehaviour.Properties.of()
                    .destroyTime(1.0f)
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
                    .noOcclusion()
    );

    public static final DeferredBlock<Block> SIGNAL_OPERATION_BLOCK = BLOCKS.registerBlock(
            "signal_operation_block",
            SignalOperationBlock::new,
            BlockBehaviour.Properties.of()
                    .destroyTime(1.0f)
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
    );
}
