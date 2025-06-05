package github.dctime.dctimemod.compatability.jade;

import github.dctime.dctimemod.RegisterBlocks;
import github.dctime.dctimemod.block.SignalWireBlock;
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
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(SignalValueBlockComponentProvider.INSTANCE, SignalWireBlock.class);
    }

    public static void dependenciesDataGen(BiConsumer<String, String> langConsumer) {
        SignalValueBlockComponentProvider.dependenciesDataGen(langConsumer);
        langConsumer.accept("config.jade.plugin_dctimemod.signal_jade",
                "Show signal value for DCtime Mod's Signal Wire in Jade");
    }
}
