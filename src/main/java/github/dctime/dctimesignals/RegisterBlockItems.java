package github.dctime.dctimesignals;

import github.dctime.dctimesignals.block.SignalOperationBlockItem;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.Function;

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
//    public static final DeferredItem<BlockItem> SIGNAL_OPERATION_BLOCK = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_OPERATION_BLOCK);
//    public static final DeferredItem<BlockItem> SIGNAL_OPERATION_BLOCK = registerCustomBlockItem(RegisterBlocks.SIGNAL_OPERATION_BLOCK, new Item.Properties(), SignalOperationBlockItem.class);
    public static final DeferredItem<BlockItem> SIGNAL_OPERATION_BLOCK = registerCustomBlockItem(RegisterBlocks.SIGNAL_OPERATION_BLOCK, (loc) -> new SignalOperationBlockItem(RegisterBlocks.SIGNAL_OPERATION_BLOCK.get(), new Item.Properties()));
    public static final DeferredItem<BlockItem> REDSTONE_TO_SIGNAL_CONVERTER = ITEMS.registerSimpleBlockItem(RegisterBlocks.REDSTONE_TO_SIGNAL_CONVERTER);
    public static final DeferredItem<BlockItem> SIGNAL_BLOCKING_MATERIAL_BLOCK = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_BLOCKING_MATERIAL_BLOCK);
    public static final DeferredItem<BlockItem> SIGNAL_RESEARCH_STATION = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_RESEARCH_STATION);
    public static final DeferredItem<BlockItem> SIGNAL_RESEARCH_STATION_SIGNAL_INPUT = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT);
    public static final DeferredItem<BlockItem> SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT);
    public static final DeferredItem<BlockItem> SIGNAL_RESEARCH_ITEM_CHAMBER = ITEMS.registerSimpleBlockItem(RegisterBlocks.SIGNAL_RESEARCH_ITEM_CHAMBER);
    public static final DeferredItem<BlockItem> AETHERITE_CERAMIC_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(RegisterBlocks.AETHERITE_CERAMIC_BLOCK);
    public static final DeferredItem<BlockItem> GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(RegisterBlocks.GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK);

    private static <B extends BlockItem> DeferredItem<BlockItem> registerCustomBlockItem(Holder<Block> block, Function<ResourceLocation, BlockItem> blockItemSupplier) {
        String name = (block.unwrapKey().orElseThrow()).location().getPath();
        Objects.requireNonNull(block);
        return ITEMS.register(name, blockItemSupplier);
    }
}
