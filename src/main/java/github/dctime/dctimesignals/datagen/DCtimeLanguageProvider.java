package github.dctime.dctimesignals.datagen;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterItems;
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
        this.addBlock(RegisterBlocks.SIGNAL_WIRE, "Signal Wire");
        this.addBlock(RegisterBlocks.CONSTANT_SIGNAL_BLOCK, "Const Signal Block");
        this.addBlock(RegisterBlocks.SINGAL_TO_REDSTONE_CONVERTER, "Signal to Redstone Converter");
        this.addBlock(RegisterBlocks.SIGNAL_OPERATION_BLOCK, "Signal Operation Block");
        this.addBlock(RegisterBlocks.REDSTONE_TO_SIGNAL_CONVERTER, "Redstone to Signal Converter");
        this.addItem(RegisterItems.SIGNAL_DETECTOR, "Signal Detector");
        this.addItem(RegisterItems.AND_CARD, "And Card");
        this.addItem(RegisterItems.NOT_CARD, "Not Card");
        this.addItem(RegisterItems.OR_CARD, "Or Card");
        this.addItem(RegisterItems.SIGNAL_CONFIGURATOR, "Signal Configurator");
        this.add("menu.title." + DCtimeMod.MODID + ".signal_operation_menu", "Signal Operation Menu");
        this.addBlock(RegisterBlocks.SIGNAL_BLOCKING_MATERIAL_BLOCK, "Signal Blocking Material Block");
        this.addItem(RegisterItems.SIGNAL_BLOCKING_MATERIAL, "Signal Blocking Material");
        this.addBlock(RegisterBlocks.AETHERITE_CERAMIC_BLOCK, "Aetherite Ceramic Block");
        this.addItem(RegisterItems.AETHERITE_CERAMIC_BALL, "Aetherite Ceramic Ball");
        this.addBlock(RegisterBlocks.SIGNAL_RESEARCH_STATION, "Signal Research Station");
        this.addBlock(RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT, "Signal Research Station Signal Input");
        this.addBlock(RegisterBlocks.SIGNAL_RESEARCH_ITEM_CHAMBER, "Signal Research Station Item Chamber");
        this.addBlock(RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT, "Signal Research Station Signal Output");
        this.add("menu.title." + DCtimeMod.MODID + ".signal_item_chamber_menu", "Signal Research Item Chamber");
        this.addItem(RegisterItems.PLUS_CARD, "Plus Card");
        this.addBlock(RegisterBlocks.GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK, "Ground Penetrating Signal Emitter");
        this.addItem(RegisterItems.SIGNAL_PICKAXE, "Signal Pickaxe");
        this.add("menu.title." + DCtimeMod.MODID + ".ground_penetrating_signal_emitter_menu", "G.P.S. Emitter Menu");
        this.add("title.jei." + DCtimeMod.MODID + ".signal_research", "Signal Research");


        // ponder text
        if (ModList.get().isLoaded("ponder")) {
            CreateDependencies.dependenciesDataGen(this);
        }

        if (ModList.get().isLoaded("jade")) {
            DCtimeJadePlugin.dependenciesDataGen(this::add);
        }
    }
}
