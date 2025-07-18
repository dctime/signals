package github.dctime.dctimesignals.datagen;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DCtimeBlockTagsProvider extends BlockTagsProvider {
    public DCtimeBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DCtimeMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(RegisterBlocks.SIGNAL_BLOCKING_MATERIAL_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(RegisterBlocks.SIGNAL_BLOCKING_MATERIAL_BLOCK.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(RegisterBlocks.AETHERITE_CERAMIC_BLOCK.get());
    }
}
