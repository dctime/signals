package github.dctime.dctimesignals;

import github.dctime.dctimesignals.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

    public static final DeferredBlock<Block> SIGNAL_WIRE = BLOCKS.registerBlock(
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
                    .noOcclusion()
    );

    public static final DeferredBlock<Block> REDSTONE_TO_SIGNAL_CONVERTER = BLOCKS.registerBlock(
            "redstone_to_signal_converter",
            RedstoneToSignalConverterBlock::new,
            BlockBehaviour.Properties.of()
                    .destroyTime(1.0f)
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
    );

    public static final DeferredBlock<Block> SIGNAL_BLOCKING_MATERIAL_BLOCK = BLOCKS.registerSimpleBlock(
            "signal_blocking_material_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
    );

    public static final DeferredBlock<Block> SINGAL_WORLD_PORTAL = BLOCKS.registerBlock(
            "signal_world_portal",
            SignalWorldPortalBlock::new,
            BlockBehaviour.Properties.of()
                    .noCollission()
                    .noOcclusion()
                    .strength(-1.0F, 3600000.0F)
                    .noLootTable()
                    .isValidSpawn(Blocks::never)
                    .forceSolidOn()
    );

    public static final DeferredBlock<Block> SIGNAL_RESEARCH_STATION = BLOCKS.registerBlock(
            "signal_research_station",
            SignalResearchStationBlock::new,
            BlockBehaviour.Properties.of()
                    .destroyTime(1.0f)
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
                    .noOcclusion()
    );

    public static final DeferredBlock<Block> SIGNAL_RESEARCH_STATION_SIGNAL_INPUT = BLOCKS.registerBlock(
            "signal_research_station_signal_input",
            SignalResearchStationSignalInputBlock::new,
            BlockBehaviour.Properties.of()
                    .destroyTime(1.0f)
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
                    .noOcclusion()
    );

    public static final DeferredBlock<Block> SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT = BLOCKS.registerBlock(
            "signal_research_station_signal_output",
            SignalResearchStationSignalOutputBlock::new,
            BlockBehaviour.Properties.of()
                    .destroyTime(1.0f)
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
                    .noOcclusion()
    );

    public static final DeferredBlock<Block> SIGNAL_RESEARCH_ITEM_CHAMBER = BLOCKS.registerBlock(
            "signal_research_item_chamber",
            SignalResearchItemChamberBlock::new,
            BlockBehaviour.Properties.of()
                    .destroyTime(1.0f)
                    .explosionResistance(100.0f)
                    .sound(SoundType.METAL)
                    .noOcclusion()
    );

    public static final DeferredBlock<Block> AETHERITE_CERAMIC_BLOCK = BLOCKS.registerBlock(
            "aetherite_ceramic_block",
            AetheriteCeramicBlock::new,
            BlockBehaviour.Properties.of()
                    .strength(0.6F) // Same as clay
                    .sound(SoundType.GRAVEL)
                    .requiresCorrectToolForDrops()
    );
}
