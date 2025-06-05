package github.dctime.dctimesignals.compatability.create;


import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterItems;
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

        helper.addToTag(SIGNALS)
                .add(RegisterBlocks.SINGAL_WIRE.getId())
                .add(RegisterBlocks.CONSTANT_SIGNAL_BLOCK.getId())
                .add(RegisterItems.SIGNAL_DETECTOR.getId());

    }

    public static void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<DeferredHolder<?, ?>> HELPER = helper.withKeyFunction(DeferredHolder::getId);

        HELPER.addStoryBoard(RegisterBlocks.SINGAL_WIRE, "signal_wire_connection", DCtimeModPonderScenes::signalWireConnection, SIGNALS);
        HELPER.addStoryBoard(RegisterBlocks.SINGAL_WIRE, "signal_wire_signal_characteristics", DCtimeModPonderScenes::signalWireCharacteristics, SIGNALS);
        HELPER.addStoryBoard(RegisterBlocks.SINGAL_WIRE, "signal_wire_high_value_first", DCtimeModPonderScenes::signalWireHighValueFirst, SIGNALS);

        HELPER.addStoryBoard(RegisterBlocks.CONSTANT_SIGNAL_BLOCK, "signal_wire_signal_characteristics", DCtimeModPonderScenes::signalWireCharacteristics, SIGNALS);
        HELPER.addStoryBoard(RegisterBlocks.CONSTANT_SIGNAL_BLOCK, "signal_wire_high_value_first", DCtimeModPonderScenes::signalWireHighValueFirst, SIGNALS);

        HELPER.addStoryBoard(RegisterItems.SIGNAL_DETECTOR, "signal_wire_signal_characteristics", DCtimeModPonderScenes::signalWireCharacteristics, SIGNALS);
        HELPER.addStoryBoard(RegisterItems.SIGNAL_DETECTOR, "signal_wire_high_value_first", DCtimeModPonderScenes::signalWireHighValueFirst, SIGNALS);



    }
}
