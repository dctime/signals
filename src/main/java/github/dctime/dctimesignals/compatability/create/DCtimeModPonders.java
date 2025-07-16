package github.dctime.dctimesignals.compatability.create;


import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterItems;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

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
                .add(RegisterBlocks.SIGNAL_WIRE.getId())
                .add(RegisterBlocks.CONSTANT_SIGNAL_BLOCK.getId())
                .add(RegisterItems.SIGNAL_DETECTOR.getId())
                .add(RegisterBlocks.SIGNAL_OPERATION_BLOCK.getId());

    }

    public static void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<DeferredHolder<?, ?>> HELPER = helper.withKeyFunction(DeferredHolder::getId);

        HELPER.addStoryBoard(RegisterBlocks.SIGNAL_WIRE, "signal_wire_connection", DCtimeModPonderScenes::signalWireConnection, SIGNALS);
        HELPER.addStoryBoard(RegisterBlocks.SIGNAL_WIRE, "signal_wire_signal_characteristics", DCtimeModPonderScenes::signalWireCharacteristics, SIGNALS);
        HELPER.addStoryBoard(RegisterBlocks.SIGNAL_WIRE, "signal_wire_high_value_first", DCtimeModPonderScenes::signalWireHighValueFirst, SIGNALS);

        HELPER.addStoryBoard(RegisterBlocks.CONSTANT_SIGNAL_BLOCK, "const_signal_block_tutorial", DCtimeModPonderScenes::constSignalBlockTutorial, SIGNALS);
        HELPER.addStoryBoard(RegisterBlocks.CONSTANT_SIGNAL_BLOCK, "signal_wire_signal_characteristics", DCtimeModPonderScenes::signalWireCharacteristics, SIGNALS);
        HELPER.addStoryBoard(RegisterBlocks.CONSTANT_SIGNAL_BLOCK, "signal_wire_high_value_first", DCtimeModPonderScenes::signalWireHighValueFirst, SIGNALS);

        HELPER.addStoryBoard(RegisterItems.SIGNAL_DETECTOR, "signal_wire_signal_characteristics", DCtimeModPonderScenes::signalWireCharacteristics, SIGNALS);
        HELPER.addStoryBoard(RegisterItems.SIGNAL_DETECTOR, "signal_wire_high_value_first", DCtimeModPonderScenes::signalWireHighValueFirst, SIGNALS);

        HELPER.addStoryBoard(RegisterBlocks.SIGNAL_OPERATION_BLOCK, "operation_block_tutorial", DCtimeModPonderScenes::operationBlockTutorial, SIGNALS);

        HELPER.addStoryBoard(RegisterBlocks.SINGAL_WORLD_PORTAL, "signal_world_portal", DCtimeModPonderScenes::signalWorldPortal, SIGNALS);
        HELPER.addStoryBoard(RegisterItems.SIGNAL_BLOCKING_MATERIAL, "signal_world_portal", DCtimeModPonderScenes::signalWorldPortal, SIGNALS);
        HELPER.addStoryBoard(RegisterItems.AETHERITE_CERAMIC_BALL, "signal_world_portal", DCtimeModPonderScenes::signalWorldPortal, SIGNALS);
        HELPER.addStoryBoard(RegisterBlocks.SIGNAL_BLOCKING_MATERIAL_BLOCK, "signal_world_portal", DCtimeModPonderScenes::signalWorldPortal, SIGNALS);
        HELPER.addStoryBoard(RegisterBlocks.AETHERITE_CERAMIC_BLOCK, "signal_world_portal", DCtimeModPonderScenes::signalWorldPortal, SIGNALS);

        HELPER.addStoryBoard(RegisterBlocks.AETHERITE_CERAMIC_BLOCK, "signal_world_resources", DCtimeModPonderScenes::signalWorldResources, SIGNALS);
        HELPER.addStoryBoard(RegisterBlocks.SIGNAL_BLOCKING_MATERIAL_BLOCK, "signal_world_resources", DCtimeModPonderScenes::signalWorldResources, SIGNALS);
        HELPER.addStoryBoard(RegisterItems.AETHERITE_CERAMIC_BALL, "signal_world_resources", DCtimeModPonderScenes::signalWorldResources, SIGNALS);
        HELPER.addStoryBoard(RegisterItems.SIGNAL_BLOCKING_MATERIAL, "signal_world_resources", DCtimeModPonderScenes::signalWorldResources, SIGNALS);

        List<DeferredItem<Item>> cards = List.of(
          RegisterItems.AND_CARD,
          RegisterItems.NOT_CARD,
          RegisterItems.OR_CARD
        );

        for (DeferredItem<Item> card : cards) {
            HELPER.addStoryBoard(card, "basic_plate", DCtimeModPonderScenes::binaryConversionTutorial, SIGNALS);
        }

        HELPER.addStoryBoard(RegisterItems.AND_CARD, "basic_plate", DCtimeModPonderScenes::binaryAndOrOperationTutorial, SIGNALS);
        HELPER.addStoryBoard(RegisterItems.OR_CARD, "basic_plate", DCtimeModPonderScenes::binaryAndOrOperationTutorial, SIGNALS);
        HELPER.addStoryBoard(RegisterItems.NOT_CARD, "basic_plate", DCtimeModPonderScenes::binaryNotOperationTutorial, SIGNALS);

        for (DeferredItem<Item> card : cards) {
            HELPER.addStoryBoard(card, "operation_block_tutorial", DCtimeModPonderScenes::operationBlockTutorial, SIGNALS);
        }



        HELPER.addStoryBoard(RegisterBlocks.SINGAL_TO_REDSTONE_CONVERTER, "signal_to_redstone", DCtimeModPonderScenes::signalToRedstone, SIGNALS);
        HELPER.addStoryBoard(RegisterBlocks.REDSTONE_TO_SIGNAL_CONVERTER, "redstone_to_signal", DCtimeModPonderScenes::redstoneToSignal, SIGNALS);


        List<DeferredBlock<Block>> signalResearchBlocks = List.of(
                RegisterBlocks.SIGNAL_RESEARCH_STATION,
                RegisterBlocks.SIGNAL_RESEARCH_ITEM_CHAMBER,
                RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT,
                RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT
        );

        for (DeferredBlock<Block> block : signalResearchBlocks) {
            HELPER.addStoryBoard(block, "signal_research_station_tutorial", DCtimeModPonderScenes::signalResearchStationTutorial, SIGNALS);
            HELPER.addStoryBoard(block, "null", DCtimeModPonderScenes::binaryConversionTutorial, SIGNALS);
        }

        HELPER.addStoryBoard(RegisterBlocks.GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK, "signal_pickaxe", DCtimeModPonderScenes::signalPickaxeAndGPSEmitterTutorial);
        HELPER.addStoryBoard(RegisterItems.SIGNAL_PICKAXE, "signal_pickaxe", DCtimeModPonderScenes::signalPickaxeAndGPSEmitterTutorial);

    }
}
