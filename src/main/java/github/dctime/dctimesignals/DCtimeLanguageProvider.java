package github.dctime.dctimesignals;

import github.dctime.dctimesignals.compatability.create.CreateDependencies;
import github.dctime.dctimesignals.compatability.jade.DCtimeJadePlugin;
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
        this.add("itemGroup." + DCtimeMod.MODID + ".dctimesignalstab", "Signals");
        this.addBlock(RegisterBlocks.SINGAL_WIRE, "Signal Wire");
        this.addBlock(RegisterBlocks.CONSTANT_SIGNAL_BLOCK, "Const Signal Block");
        this.addBlock(RegisterBlocks.SINGAL_TO_REDSTONE_CONVERTER, "Signal to Redstone Converter");
        this.addBlock(RegisterBlocks.SIGNAL_OPERATION_BLOCK, "Signal Operation Block");
        this.addItem(RegisterItems.SIGNAL_DETECTOR, "Signal Detector");
        this.addItem(RegisterItems.AND_CARD, "And Card");
        this.addItem(RegisterItems.NOT_CARD, "Not Card");
        this.addItem(RegisterItems.OR_CARD, "Or Card");
        this.addItem(RegisterItems.SIGNAL_CONFIGURATOR, "Signal Configurator");

        // ponder text
        if (ModList.get().isLoaded("ponder")) {
            CreateDependencies.dependenciesDataGen(this);
        }

        if (ModList.get().isLoaded("jade")) {
            DCtimeJadePlugin.dependenciesDataGen(this::add);
        }
    }
}
