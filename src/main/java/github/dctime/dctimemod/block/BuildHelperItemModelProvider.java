package github.dctime.dctimemod.block;

import github.dctime.dctimemod.DCtimeMod;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BuildHelperItemModelProvider extends ItemModelProvider {
    public BuildHelperItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DCtimeMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("build_helper_block", modLoc("block/build_helper_block_non_destroy_mode"));
    }
}
