package github.dctime.dctimesignals.compatability.jade;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.block.ConstSignalBlock;
import github.dctime.dctimesignals.block.RedstoneToSignalConverterBlock;
import github.dctime.dctimesignals.block.SignalOperationBlock;
import github.dctime.dctimesignals.block.SignalWireBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

import java.util.function.BiConsumer;

@WailaPlugin
public class DCtimeJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(SignalValueBlockComponentProvider.INSTANCE, SignalWireBlock.class);
        registration.registerBlockDataProvider(OutputSignalValueBlockComponentProvider.INSTANCE, ConstSignalBlock.class);
        registration.registerBlockDataProvider(OutputSignalValueBlockComponentProvider.INSTANCE, SignalOperationBlock.class);
        registration.registerBlockDataProvider(OutputSignalValueBlockComponentProvider.INSTANCE, RedstoneToSignalConverterBlock.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(SignalValueBlockComponentProvider.INSTANCE, SignalWireBlock.class);
        registration.registerBlockComponent(OutputSignalValueBlockComponentProvider.INSTANCE, ConstSignalBlock.class);
        registration.registerBlockComponent(OutputSignalValueBlockComponentProvider.INSTANCE, SignalOperationBlock.class);
        registration.registerBlockComponent(OutputSignalValueBlockComponentProvider.INSTANCE, RedstoneToSignalConverterBlock.class);
    }

    public static void dependenciesDataGen(BiConsumer<String, String> langConsumer) {
        SignalValueBlockComponentProvider.dependenciesDataGen(langConsumer);
        OutputSignalValueBlockComponentProvider.dependenciesDataGen(langConsumer);

    }

    public static String getUIDTranslationKey(String providerName) {
        return "config.jade.plugin_" + DCtimeMod.MODID + "." + providerName;
    }
}
