package github.dctime.dctimemod.compatability.create;


import github.dctime.dctimemod.DCtimeMod;
import github.dctime.dctimemod.RegisterBlocks;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DCtimeModPonders {
    public static final ResourceLocation SIGNALS = ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signals");

    public static void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {

        helper.registerTag(SIGNALS)
                .addToIndex()
                .item(RegisterBlocks.CONSTANT_SIGNAL_BLOCK.get(), true, false)
                .title("Signal Blocks")
                .description("Signals")
                .register();

    }

    public static void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<DeferredHolder<?, ?>> HELPER = helper.withKeyFunction(DeferredHolder::getId);

        HELPER.addStoryBoard(RegisterBlocks.SINGAL_WIRE, "signal_wire", DCtimeModPonderScenes::signalWire, SIGNALS);

    }
}
