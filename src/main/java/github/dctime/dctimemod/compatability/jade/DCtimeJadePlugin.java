package github.dctime.dctimemod.compatability.jade;

import com.ibm.icu.util.Output;
import github.dctime.dctimemod.RegisterBlocks;
import github.dctime.dctimemod.block.ConstSignalBlock;
import github.dctime.dctimemod.block.SignalOperationBlock;
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
        registration.registerBlockDataProvider(OutputSignalValueBlockComponentProvider.INSTANCE, ConstSignalBlock.class);
        registration.registerBlockDataProvider(OutputSignalValueBlockComponentProvider.INSTANCE, SignalOperationBlock.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(SignalValueBlockComponentProvider.INSTANCE, SignalWireBlock.class);
        registration.registerBlockComponent(OutputSignalValueBlockComponentProvider.INSTANCE, ConstSignalBlock.class);
        registration.registerBlockComponent(OutputSignalValueBlockComponentProvider.INSTANCE, SignalOperationBlock.class);
    }

    public static void dependenciesDataGen(BiConsumer<String, String> langConsumer) {
        SignalValueBlockComponentProvider.dependenciesDataGen(langConsumer);
        OutputSignalValueBlockComponentProvider.dependenciesDataGen(langConsumer);

    }
}
