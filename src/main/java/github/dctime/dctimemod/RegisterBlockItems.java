package github.dctime.dctimemod;

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
                    RegisterBlocks.SINGAL_WIRE
            );

    public static final DeferredItem<BlockItem> CONSTANT_SIGNAL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem(RegisterBlocks.CONSTANT_SIGNAL_BLOCK);
}
