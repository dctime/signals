package github.dctime.dctimemod;

import github.dctime.dctimemod.compatability.create.CreateDependencies;
import net.minecraft.data.PackOutput;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class DCtimeLanguageProvider extends LanguageProvider {
    public DCtimeLanguageProvider(PackOutput output) {
        super(output, DCtimeMod.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addBlock(RegisterBlocks.BUILD_HELPER_BLOCK, "Build Helper Block");
        this.add("itemGroup." + DCtimeMod.MODID + ".dctimemodtab", "DCtime Mod");

        // ponder text
        if (ModList.get().isLoaded("create")) {
            CreateDependencies.dependenciesDataGen(this);
        }
    }
}
