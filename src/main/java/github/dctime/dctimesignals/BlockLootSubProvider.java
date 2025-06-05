package github.dctime.dctimesignals;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class BlockLootSubProvider extends net.minecraft.data.loot.BlockLootSubProvider {
    public BlockLootSubProvider(HolderLookup.Provider lookupProvider) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return RegisterBlocks.BLOCKS.getEntries().stream().map(e -> (Block)e.value()).toList();
    }

    @Override
    protected void generate() {
        this.add(RegisterBlocks.BUILD_HELPER_BLOCK.get(), this.createSingleItemTable(RegisterBlockItems.BUILD_HELPER_BLOCK_ITEM));
        this.add(RegisterBlocks.FLAWLESS_EXCHANGER.get(), this.createSingleItemTable(Items.DIRT));
        this.add(RegisterBlocks.SINGAL_WIRE.get(), this.createSingleItemTable(RegisterBlockItems.SIGNAL_WIRE));
        this.add(RegisterBlocks.CONSTANT_SIGNAL_BLOCK.get(), this.createSingleItemTable(RegisterBlockItems.CONSTANT_SIGNAL_BLOCK_ITEM));
        this.add(RegisterBlocks.SINGAL_TO_REDSTONE_CONVERTER.get(), this.createSingleItemTable(RegisterBlockItems.SINGAL_TO_REDSTONE_CONVERTER));
        this.add(RegisterBlocks.SIGNAL_OPERATION_BLOCK.get(), this.createSingleItemTable(RegisterBlockItems.SIGNAL_OPERATION_BLOCK));

    }
}
