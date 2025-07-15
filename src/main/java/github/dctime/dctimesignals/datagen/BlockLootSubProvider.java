package github.dctime.dctimesignals.datagen;

import github.dctime.dctimesignals.RegisterBlockItems;
import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

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
        this.add(RegisterBlocks.SIGNAL_WIRE.get(), this.createSingleItemTable(RegisterBlockItems.SIGNAL_WIRE));
        this.add(RegisterBlocks.CONSTANT_SIGNAL_BLOCK.get(), this.createSingleItemTable(RegisterBlockItems.CONSTANT_SIGNAL_BLOCK_ITEM));
        this.add(RegisterBlocks.SINGAL_TO_REDSTONE_CONVERTER.get(), this.createSingleItemTable(RegisterBlockItems.SINGAL_TO_REDSTONE_CONVERTER));
        this.add(RegisterBlocks.SIGNAL_OPERATION_BLOCK.get(), this.createSingleItemTable(RegisterBlockItems.SIGNAL_OPERATION_BLOCK));
        this.add(RegisterBlocks.REDSTONE_TO_SIGNAL_CONVERTER.get(), this.createSingleItemTable(RegisterBlockItems.REDSTONE_TO_SIGNAL_CONVERTER));
        this.add(RegisterBlocks.SIGNAL_BLOCKING_MATERIAL_BLOCK.get(),
            block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block,
                    LootItem.lootTableItem(RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                        .apply(SetItemCountFunction.setCount(new ConstantValue(1.0F)))
                )
            )
        );
        this.add(RegisterBlocks.SIGNAL_RESEARCH_STATION.get(), this.createSingleItemTable(RegisterBlockItems.SIGNAL_RESEARCH_STATION));
        this.add(RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT.get(), this.createSingleItemTable(RegisterBlockItems.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT));
        this.add(RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT.get(), this.createSingleItemTable(RegisterBlockItems.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT));
        this.add(RegisterBlocks.SIGNAL_RESEARCH_ITEM_CHAMBER.get(), this.createSingleItemTable(RegisterBlockItems.SIGNAL_RESEARCH_ITEM_CHAMBER));
        this.add(RegisterBlocks.AETHERITE_CERAMIC_BLOCK.get(),
            block -> createSilkTouchDispatchTable(block,
                applyExplosionDecay(block,
                    LootItem.lootTableItem(RegisterItems.AETHERITE_CERAMIC_BALL.get())
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                )
            )
        );
        this.add(RegisterBlocks.GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK.get(), this.createSingleItemTable(RegisterBlockItems.GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK_ITEM));
    }
}
