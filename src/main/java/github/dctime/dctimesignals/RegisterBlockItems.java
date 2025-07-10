package github.dctime.dctimesignals;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegisterBlockItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DCtimeMod.MODID);

    public static final DeferredItem<BlockItem> BUILD_HELPER_BLOCK_ITEM =
        ITEMS.registerSimpleBlockItem(
            RegisterBlocks.BUILD_HELPER_BLOCK
        );

    public static final DeferredItem<BlockItem> SIGNAL_WIRE =
            ITEMS.registerSimpleBlockItem(
                    RegisterBlocks.SIGNAL_WIRE
            );

    public static final DeferredItem<BlockItem> CONSTANT_SIGNAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(RegisterBlocks.CONSTANT_SIGNAL_BLOCK);
    public static final DeferredItem<BlockItem> SINGAL_TO_REDSTONE_CONVERTER = ITEMS.registerSimpleBlockItem(RegisterBlocks.SINGAL_TO_REDSTONE_CONVERTER);
    public static final DeferredItem<BlockItem> SIGNAL_OPERATION_BLOCK = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_OPERATION_BLOCK);
    public static final DeferredItem<BlockItem> REDSTONE_TO_SIGNAL_CONVERTER = ITEMS.registerSimpleBlockItem(RegisterBlocks.REDSTONE_TO_SIGNAL_CONVERTER);
    public static final DeferredItem<BlockItem> SIGNAL_BLOCKING_MATERIAL_BLOCK = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_BLOCKING_MATERIAL_BLOCK);
    public static final DeferredItem<BlockItem> SIGNAL_RESEARCH_STATION = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_RESEARCH_STATION);
    public static final DeferredItem<BlockItem> SIGNAL_RESEARCH_STATION_SIGNAL_INPUT = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT);
    public static final DeferredItem<BlockItem> SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT);
    public static final DeferredItem<BlockItem> SIGNAL_RESEARCH_ITEM_CHAMBER = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_RESEARCH_ITEM_CHAMBER);
    public static final DeferredItem<BlockItem> AETHERITE_CERAMIC_BLOCK_ITEM =
        ITEMS.registerSimpleBlockItem(
            RegisterBlocks.AETHERITE_CERAMIC_BLOCK
        );
}
