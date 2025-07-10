package github.dctime.dctimesignals.compatability.create;

import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterItems;
import github.dctime.dctimesignals.block.*;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DCtimeModPonderScenes {

    public static void signalWireConnection(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("signal_wire_connection", "Connecting Signal Wires");
        scene.showBasePlate();
        scene.idle(10);

        BlockPos wirePos = util.grid().at(2, 1, 2);
        scene.world().showSection(util.select().position(2, 1, 2), Direction.DOWN);
        scene.idle(10);
        scene.addKeyframe();
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("A signal wire by itself doesn't carry any signal.")
            .pointAt(wirePos.getCenter());
        scene.idle(40);
        scene.addKeyframe();
        scene.world().showSection(util.select().position(1, 1, 2), Direction.DOWN);
        scene.world().showSection(util.select().position(3, 1, 2), Direction.DOWN);
        scene.idle(20);

        Object connectionHighlight = new Object();
        Vec3 wireConnectionSurface = util.vector().blockSurface(wirePos, Direction.NORTH).add(0, 0, 0.4f);
        AABB wireClickSurface = new AABB(wireConnectionSurface, wireConnectionSurface);
        AABB wireClickSurfaceExpanded = wireClickSurface.inflate(1 / 10f, 1 / 10f, 1 / 128f);
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, connectionHighlight, wireClickSurfaceExpanded, 40);
        scene.overlay().showControls(wireConnectionSurface, Pointing.UP, 20).rightClick().withItem(RegisterItems.SIGNAL_CONFIGURATOR.toStack());
        scene.idle(20);
        scene.world().replaceBlocks(util.select().position(2, 1, 2), RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.NORTH, true), false);
        scene.idle(20);

        Object connectionHighlight2 = new Object();
        Vec3 wireConnectionSurface2 = util.vector().blockSurface(wirePos, Direction.WEST).add(0.4f, 0, 0);
        AABB wireClickSurface2 = new AABB(wireConnectionSurface2, wireConnectionSurface2);
        AABB wireClickSurfaceExpanded2 = wireClickSurface2.inflate(1 / 128f, 1 / 10f, 1 / 10f);
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, connectionHighlight2, wireClickSurfaceExpanded2, 60);
        scene.overlay().showControls(wireConnectionSurface2, Pointing.UP, 20).rightClick().withItem(RegisterItems.SIGNAL_CONFIGURATOR.toStack());
        scene.idle(20);
        scene.world().replaceBlocks(util.select().position(2, 1, 2), RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.NORTH, true).setValue(SignalWireBlock.WEST, true), false);
        scene.world().replaceBlocks(util.select().position(1, 1, 2), RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.EAST, true), false);
        scene.idle(20);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Right-click a side of the wire with the Signal Configurator to connect wires together.")
            .pointAt(wireConnectionSurface2);
        scene.idle(40);

        scene.addKeyframe();

        Object connectionHighlight3 = new Object();
        Vec3 wireConnectionSurface3 = util.vector().blockSurface(wirePos, Direction.NORTH).add(0, 0, 0.15f);
        AABB wireClickSurface3 = new AABB(wireConnectionSurface3, wireConnectionSurface3);
        AABB wireClickSurfaceExpanded3 = wireClickSurface3.inflate(1 / 10f, 1 / 10f, 1 / 6f);
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, connectionHighlight3, wireClickSurfaceExpanded3, 40);
        scene.overlay().showControls(wireConnectionSurface3, Pointing.UP, 20).rightClick().withItem(RegisterItems.SIGNAL_CONFIGURATOR.toStack());
        scene.idle(20);
        scene.world().replaceBlocks(util.select().position(2, 1, 2), RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true), false);
        scene.idle(20);

        Object connectionHighlight4 = new Object();
        Vec3 wireConnectionSurface4 = util.vector().blockSurface(wirePos, Direction.WEST).add(0, 0, 0);
        AABB wireClickSurface4 = new AABB(wireConnectionSurface4, wireConnectionSurface4);
        AABB wireClickSurfaceExpanded4 = wireClickSurface4.inflate(1 / 3f, 1 / 10f, 1 / 10f);
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, connectionHighlight4, wireClickSurfaceExpanded4, 40);
        scene.overlay().showControls(wireConnectionSurface4, Pointing.UP, 20).rightClick().withItem(RegisterItems.SIGNAL_CONFIGURATOR.toStack());
        scene.idle(20);
        scene.world().replaceBlocks(util.select().position(2, 1, 2), RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState(), false);
        scene.world().replaceBlocks(util.select().position(1, 1, 2), RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState(), false);
        scene.idle(20);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Right-click the connection of the wire with an empty hand to disconnect the wire.")
            .pointAt(wireConnectionSurface2);
        scene.idle(40);

        scene.markAsFinished();
    }
    public static void signalWireCharacteristics(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("signal_wire_signal_characteristics", "Characteristics of signals");
        scene.showBasePlate();
        scene.idle(10);

        BlockPos wirePos1 = util.grid().at(3, 1, 2);
        BlockPos wirePos2 = util.grid().at(2, 1, 2);
        BlockPos wirePos3 = util.grid().at(1, 1, 2);
        BlockPos sourcePos = util.grid().at(3, 1, 3);

        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                scene.world().showSection(util.select().position(x, 1, z), Direction.DOWN);
            }
        }

        Selection wireSelection1 = util.select().position(3, 1, 2);
        Selection wireSelection2 = util.select().position(2, 1, 2);
        Selection wireSelection3 = util.select().position(1, 1, 2);

        scene.world().replaceBlocks(wireSelection1, RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true), false);
        scene.world().replaceBlocks(wireSelection2, RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true).setValue(SignalWireBlock.EAST, true), false);
        scene.world().replaceBlocks(wireSelection3, RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.EAST, true), false);

        scene.overlay().showText(20)
            .independent(10)
                .text("A signal is a state stored by a wire, similar to how redstone dust holds a redstone signal. It is represented as an integer and is not related to real-world electrical signals.");

        scene.idle(20);
        scene.overlay().showControls(wireSelection2.getCenter(), Pointing.UP, 20).rightClick().withItem(RegisterItems.SIGNAL_DETECTOR.get().getDefaultInstance());
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Right-click the wire with a Signal Detector to read its current signal value.")
            .pointAt(wireSelection2.getCenter());
        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Connected wires will always share the same signal value when the network is stable")
            .pointAt(wireSelection2.getCenter());
        Object highlightConnectionWires = new Object();
        Vec3 wirePos2Center = wirePos2.getCenter();
        AABB wireClickSurface4 = new AABB(wirePos2Center.add(-(1.5f - 3 / 8f), -1 / 8f, -1 / 8f), wirePos2Center.add(1.5f - 3 / 8f, 1 / 8f, 1 / 8f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightConnectionWires, wireClickSurface4, 60);

        scene.idle(30);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Because the wires are not connected to a signal source in this case, they will display \"No Signal\"")
            .pointAt(wireSelection2.getCenter());

        scene.idle(30);
        scene.addKeyframe();

        scene.world().replaceBlocks(wireSelection1, RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true).setValue(SignalWireBlock.SOUTH, true), false);
        scene.idle(20);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Now the wires are properly connected to the signal source.")
            .pointAt(wireSelection1.getCenter());
        scene.world().replaceBlocks(util.select().position(wirePos1.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.RED_WOOL.defaultBlockState(), true);

        scene.idle(30);
        scene.overlay().showControls(wireSelection2.getCenter(), Pointing.UP, 20).rightClick().withItem(RegisterItems.SIGNAL_DETECTOR.get().getDefaultInstance());
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("If the signal source outputs a signal value of 30, the wires will display \"Signal Value: 30\" when right-clicked with a signal detector.")
            .pointAt(wireSelection2.getCenter());
        scene.idle(30);

        scene.addKeyframe();

        scene.overlay().showText(20)
            .independent(10)
            .text("Now, red wool represents signal 30, while white wool represents no signal.");

        scene.idle(30);

        scene.world().replaceBlocks(wireSelection2, RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true), false);
        scene.world().replaceBlocks(wireSelection1, RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.SOUTH, true), false);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Now, let's try disconnecting the wires")
            .pointAt(wireSelection2.getCenter().add(0.5f, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Disconnected wires will display \"No Signal\"")
            .pointAt(wireSelection3.getCenter().add(0.5f, 0, 0));
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .text("What just happened in those few ticks?");
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.RED_WOOL.defaultBlockState(), true);

        scene.idle(30);

        scene.world().replaceBlocks(util.select().position(wirePos1.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("When two wires are disconnected, they notify neighboring wires that their signal might now be \"No Signal\"")
            .pointAt(wireSelection1.getCenter());

        scene.idle(30);

        Object highlightUpdatedBlocks = new Object();
        Vec3 highlightUpdatedBlocksPos = sourcePos.getCenter();
        AABB updateBlockBox = new AABB(highlightUpdatedBlocksPos.add(-(0.5), -0.5, -0.5), highlightUpdatedBlocksPos.add(0.5, 0.5, 0.5));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightUpdatedBlocks, updateBlockBox, 60);

        Object highlightUpdatedWire = new Object();
        Vec3 highlightUpdatedWirePos = wirePos3.getCenter();
        AABB updateWireBox = new AABB(highlightUpdatedWirePos.add(-1 / 8f, -1 / 8f, -1 / 8f), highlightUpdatedWirePos.add(0.5f, 1 / 8f, 1 / 8f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightUpdatedWire, updateWireBox, 30);

        scene.overlay().showText(20)
            .independent(10)
            .text("These two blocks were updated because their connected wires changed");

        scene.idle(30);

        scene.world().replaceBlocks(util.select().position(wirePos1.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The signal source attempts to update the connected wire to its emitted signal value, setting the wire to 30")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightUpdatedWire, updateWireBox, 20);
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("This wire received a command to set itself to no signal. Since the connected wire was already at no signal, the update chain stopped.")
            .pointAt(wirePos3.getCenter());

        scene.idle(30);

        Object highlightUpdatedWire1 = new Object();
        Vec3 highlightUpdatedWirePos1 = wirePos1.getCenter();
        AABB updateWireBox11 = new AABB(highlightUpdatedWirePos1.add(-1 / 8f, -1 / 8f, -1 / 8f), highlightUpdatedWirePos1.add(1 / 8f, 1 / 8f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightUpdatedWire1, updateWireBox11, 60);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("On the left, the wire attempts to send a signal value of 30 to its connected neighbor.")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("There are no more connected neighbors with a signal below 30 or holding no signal, so the update chain ends")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.addKeyframe();

        scene.overlay().showText(20)
            .colored(PonderPalette.RED)
            .text("This process only takes a few ticks, depending on the wire length")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.RED)
            .text("But we know that the wire holds \"No Signal\" for a few ticks, then returns to signal value 30")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.RED)
            .text("This means the wire might \"drop\" its signal temporarily, which could cause issues in your signal network")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.RED)
            .text("Most signal-related blocks should ignore \"No Signal\", but just in case...")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.markAsFinished();
    }
    public static void signalWireHighValueFirst(SceneBuilder scene, SceneBuildingUtil util) {
        Selection sourceHigh = util.select().position(3, 1, 3);
        Selection sourceLow = util.select().position(1, 1, 3);
        Selection wireSelection1 = util.select().position(3, 1, 2);
        Selection wireSelection2 = util.select().position(2, 1, 2);
        Selection wireSelection3 = util.select().position(1, 1, 2);

        BlockPos wirePos1 = util.grid().at(3, 1, 2);
        BlockPos wirePos2 = util.grid().at(2, 1, 2);
        BlockPos wirePos3 = util.grid().at(1, 1, 2);
        BlockPos sourceHighPos = util.grid().at(3, 1, 3);
        BlockPos sourceLowPos = util.grid().at(1, 1, 3);

        scene.title("signal_wire_high_value_first", "Characteristics of signals Part 2");
        scene.showBasePlate();
        scene.idle(10);

        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                scene.world().showSection(util.select().position(x, 1, z), Direction.DOWN);
            }
        }

        scene.world().replaceBlocks(wireSelection1, RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true).setValue(SignalWireBlock.SOUTH, true), false);
        scene.world().replaceBlocks(wireSelection2, RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.EAST, true), false);
        scene.world().replaceBlocks(wireSelection3, RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.SOUTH, true).setValue(SignalWireBlock.EAST, true), false);

        scene.overlay().showText(20)
            .independent(10)
            .text("When two sources try to set the wire's value, the higher signal always wins over the lower one.");

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .text("In this scene, the high value source is marked with red wool and the low value source with yellow wool, each outputting to different circuits");

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .text("When the circuit is stable, all connected wires will share the same signal value");

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .text("The network connected to the high value source will be high, while the one connected to the low value source will remain low");

        scene.world().replaceBlocks(util.select().position(wirePos1.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.YELLOW_WOOL.defaultBlockState(), true);

        scene.idle(30);

        scene.addKeyframe();

        scene.world().replaceBlocks(wireSelection2, RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.EAST, true).setValue(SignalWireBlock.WEST, true), false);
        scene.world().replaceBlocks(wireSelection3, RegisterBlocks.SIGNAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.SOUTH, true).setValue(SignalWireBlock.EAST, true), false);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Let's see what happens when we connect the wires together...")
            .pointAt(wireSelection2.getCenter().add(-0.5f, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("When wires are connected, their value resets to \"No Signal\" and all connected neighbors are updated accordingly.")
            .pointAt(wireSelection2.getCenter().add(-0.5f, 0, 0));
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);

        Object highlightUpdatedBlocks = new Object();
        Object highlightUpdatedBlocks2 = new Object();
        Object highlightUpdatedBlocks3 = new Object();

        AABB updateBlockBox = new AABB(wirePos1.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos1.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.WHITE, highlightUpdatedBlocks, updateBlockBox, 50);
        AABB updateBlockBox4 = new AABB(sourceLowPos.getCenter().add(-0.5f, -0.5f, -0.5f), sourceLowPos.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.WHITE, highlightUpdatedBlocks3, updateBlockBox4, 200);

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("On the left, the wire reset its value to \"No Signal\" and instructed its connected neighbors to do the same when it received the command")
            .pointAt(wireSelection1.getCenter().add(0, 0.5, 0));

        AABB updateBlockBox2 = new AABB(sourceHighPos.getCenter().add(-0.5f, -0.5f, -0.5f), sourceHighPos.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.WHITE, highlightUpdatedBlocks, updateBlockBox2, 210);
        AABB updateBlockBox3 = new AABB(wirePos2.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos2.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.WHITE, highlightUpdatedBlocks2, updateBlockBox3, 50);
        scene.world().replaceBlocks(util.select().position(wirePos1.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("This wire is already at \"No Signal\", so the update chain ends here.")
            .pointAt(wireSelection2.getCenter().add(0, 0.5, 0));

        scene.idle(30);

        scene.addKeyframe();

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Let's focus on the right side now.")
            .pointAt(sourceLowPos.getCenter().add(0, 0.5, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The source will attempt to set the connected wire to its own value")
            .pointAt(sourceLowPos.getCenter().add(0, 0.5, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("It cannot overwrite the wire if the wire already holds a higher signal value than the one being set")
            .pointAt(sourceLowPos.getCenter().add(0, 0.5, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("In this case, the wire holds \"No Signal\", so the source can overwrite the value and tell the wire to update its connected neighbors")
            .pointAt(sourceLowPos.getCenter().add(0, 0.5, 0));

        AABB updateBlockBox5 = new AABB(wirePos3.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos3.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.OUTPUT, highlightUpdatedBlocks3, updateBlockBox5, 30);

        scene.idle(30);

        scene.addKeyframe();

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The wire updated itself to a low value and instructed its neighbor to do the same")
            .pointAt(wirePos3.getCenter().add(0, 0, 0));
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.YELLOW_WOOL.defaultBlockState(), true);

        Object highlightUpdatedBlocks4 = new Object();
        Object highlightUpdatedBlocks5 = new Object();
        AABB updateBlockBox6 = new AABB(wirePos2.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos2.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.OUTPUT, highlightUpdatedBlocks4, updateBlockBox6, 70);
        AABB updateBlockBox7 = new AABB(sourceLowPos.getCenter().add(-0.5f, -0.5f, -0.5f), sourceLowPos.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.OUTPUT, highlightUpdatedBlocks5, updateBlockBox7, 30);

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .text("Let's fast forward a bit...");

        AABB updateBlockBox8 = new AABB(wirePos1.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos1.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.RED, highlightUpdatedBlocks, updateBlockBox8, 20);
        scene.idle(20);
        AABB updateBlockBox9 = new AABB(wirePos2.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos2.getCenter().add(0.5f, 0.5f, 0.5f));
        AABB updateBlockBox10 = new AABB(sourceHighPos.getCenter().add(-0.5f, -0.5f, -0.5f), sourceHighPos.getCenter().add(0.5f, 0.5f, 0.5f));
        Object highlightUpdatedBlocks6 = new Object();
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.RED, highlightUpdatedBlocks, updateBlockBox9, 140);
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.RED, highlightUpdatedBlocks6, updateBlockBox10, 20);
        scene.world().replaceBlocks(util.select().position(wirePos1.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.idle(20);
        AABB updateBlockBox11 = new AABB(wirePos1.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos1.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.OUTPUT, highlightUpdatedBlocks4, updateBlockBox11, 50);
        Object highlightUpdatedBlocks9 = new Object();
        AABB updateBlockBox16 = new AABB(wirePos3.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos3.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.OUTPUT, highlightUpdatedBlocks9, updateBlockBox16, 20);
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.YELLOW_WOOL.defaultBlockState(), true);

        scene.idle(20);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("This wire received a command to set its value lower, but it already holds a higher value.")
            .pointAt(wirePos1.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Therefore, the wire does not overwrite itself and stops the update chain")
            .pointAt(wirePos1.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("This wire received a command to update to a higher value while it currently holds a lower value.")
            .pointAt(wirePos2.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Therefore, it updates itself and instructs connected neighbors to update as well")
            .pointAt(wirePos2.getCenter().add(0, 0, 0));
        AABB updateBlockBox12 = new AABB(wirePos3.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos3.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.RED, highlightUpdatedBlocks, updateBlockBox12, 100);

        Object highlightUpdatedBlocks7 = new Object();
        AABB updateBlockBox13 = new AABB(wirePos1.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos1.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.RED, highlightUpdatedBlocks7, updateBlockBox13, 40);

        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.RED_WOOL.defaultBlockState(), true);

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("This wire received a command to update to a high value, but it already holds that value, so the update chain stops here")
            .pointAt(wirePos1.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("This wire received a command to update to a higher value while it currently holds a lower value. It updates itself and tells its neighbors to update as well")
            .pointAt(wirePos3.getCenter().add(0, 0, 0));

        AABB updateBlockBox14 = new AABB(sourceLow.getCenter().add(-0.5f, -0.5f, -0.5f), sourceLow.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.RED, highlightUpdatedBlocks, updateBlockBox14, 60);

        Object highlightUpdatedBlocks8 = new Object();
        AABB updateBlockBox15 = new AABB(wirePos2.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos2.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.RED, highlightUpdatedBlocks8, updateBlockBox15, 80);

        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.RED_WOOL.defaultBlockState(), true);

        scene.idle(30);

        AABB updateBlockBox17 = new AABB(wirePos3.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos3.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.OUTPUT, highlightUpdatedBlocks, updateBlockBox17, 50);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The source attempts to overwrite the output port's wire")
            .pointAt(sourceLow.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .text("Both of them doesn't overwrite anything. Stops the updating chain");

        scene.idle(30);

        scene.addKeyframe();

        scene.overlay().showText(20)
            .independent(10)
            .text("Now the circuit is stable");

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .colored(PonderPalette.RED)
            .text("We can see that when there are two output sources trying to output to the same network");

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .colored(PonderPalette.RED)
            .text("The network always holds the highest signal value from all connected sources");

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .colored(PonderPalette.RED)
            .text("And when the circuit is unstable, the wire's value may fluctuate, potentially causing unintended behavior");

        scene.idle(30);

        scene.markAsFinished();
    }
    public static void operationBlockTutorial(SceneBuilder scene, SceneBuildingUtil util) {
        BlockPos operationBlockPos = util.grid().at(2, 1, 2);
        BlockPos outputWirePos = util.grid().at(2, 1, 1);
        BlockPos inputWirePos = util.grid().at(3, 1, 2);
        BlockPos inputWirePos2 = util.grid().at(1, 1, 2);
        Vec3 operationBlockPosNorth = operationBlockPos.getCenter().add(0, 0, -1 / 4f);
        Vec3 operationBlockPosEast = operationBlockPos.getCenter().add(1 / 4f, 0, 0);
        Vec3 operationBlockPosWest = operationBlockPos.getCenter().add(-1 / 4f, 0, 0);
        Object showConfigNorthHighlight = new Object();
        AABB showConfigNorthBox = new AABB(operationBlockPosNorth.add(-1 / 4f, -1 / 4f, -1 / 16f), operationBlockPosNorth.add(1 / 4f, 1 / 4f, 1 / 16f));

        scene.title("operation_block_tutorial", "Operation Block Tutorial");
        scene.showBasePlate();

        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                scene.world().showSection(util.select().position(x, 1, z), Direction.DOWN);
            }
        }

        scene.idle(10);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The Operation Block can perform various operations on the signal it receives")
            .pointAt(operationBlockPos.getCenter());
        scene.idle(40);


        int configNorthHighlightTime = 40 * 2;
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, showConfigNorthHighlight, showConfigNorthBox, configNorthHighlightTime);

        scene.overlay().showControls(operationBlockPosNorth, Pointing.DOWN, 20)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_CONFIGURATOR.get().getDefaultInstance());

        scene.world().modifyBlock(operationBlockPos, (blockState ->
                blockState.setValue(SignalOperationBlock.NORTH_SIDE_MODE,
                    SignalOperationBlock.SideMode.INPUT
                )),
            false
        );

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Right-click the Operation Block with a Signal Configurator to set the side mode")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);
        // end of configNorthHighlightTime

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("You can set the side to INPUT, INPUT2, or OUTPUT")
            .pointAt(operationBlockPosNorth);

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The INPUT port is indicated by a white arrow texture pointing into the block")
            .pointAt(operationBlockPosNorth);

        scene.idle(40);

        scene.overlay().showControls(operationBlockPosNorth, Pointing.DOWN, 20)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_CONFIGURATOR.get().getDefaultInstance());

        scene.world().modifyBlock(operationBlockPos, (blockState ->
                blockState.setValue(SignalOperationBlock.NORTH_SIDE_MODE,
                    SignalOperationBlock.SideMode.INPUT2
                )),
            false
        );

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The INPUT2 port is indicated by a blue arrow texture pointing into the block")
            .pointAt(operationBlockPosNorth);

        scene.idle(40);

        scene.overlay().showControls(operationBlockPosNorth, Pointing.DOWN, 20)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_CONFIGURATOR.get().getDefaultInstance());

        scene.world().modifyBlock(operationBlockPos, (blockState ->
                blockState.setValue(SignalOperationBlock.NORTH_SIDE_MODE,
                    SignalOperationBlock.SideMode.OUTPUT
                )),
            false
        );

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The OUTPUT port is shown by a white arrow texture pointing out of the block")
            .pointAt(operationBlockPosNorth);

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Now let's set the east side to INPUT")
            .pointAt(operationBlockPosEast);

        scene.world().modifyBlock(operationBlockPos, (blockState ->
                blockState.setValue(SignalOperationBlock.EAST_SIDE_MODE,
                    SignalOperationBlock.SideMode.INPUT
                )),
            false
        );

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("And set the west side to INPUT2")
            .pointAt(operationBlockPosWest);

        scene.world().modifyBlock(operationBlockPos, (blockState ->
                blockState.setValue(SignalOperationBlock.WEST_SIDE_MODE,
                    SignalOperationBlock.SideMode.INPUT2
                )),
            false
        );

        scene.idle(40);

        scene.addKeyframe();

        scene.world().modifyBlock(outputWirePos, (blockState ->
                blockState.setValue(
                    SignalWireBlock.SOUTH,
                    true
                )),
            false
        );

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("After connecting the output port")
            .pointAt(outputWirePos.getCenter());

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Because it contains an internal buffer that stores the most recently calculated signal value")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("By default, it stores a signal value of 0")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);

        scene.overlay().showControls(outputWirePos.getCenter(), Pointing.DOWN, 20)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_DETECTOR.get().getDefaultInstance());

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The output wire will display \"Signal Value: 0\" when right-clicked with a signal detector")
            .pointAt(outputWirePos.getCenter());

        scene.world().setBlock(outputWirePos.below(), Blocks.BLACK_WOOL.defaultBlockState(), true);

        scene.idle(40);

        scene.overlay().showControls(operationBlockPos.getCenter(), Pointing.DOWN, 20)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_DETECTOR.get().getDefaultInstance());

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("When right-clicked with a signal detector, the operation block will display \"Output Signal Value: 0\"")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);

        scene.addKeyframe();

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Now let's connect the input ports to the wires")
            .pointAt(operationBlockPos.getCenter());

        scene.world().modifyBlock(inputWirePos, (blockState ->
                blockState.setValue(
                    SignalWireBlock.WEST,
                    true
                )),
            false
        );

        scene.world().modifyBlock(inputWirePos2, (blockState ->
                blockState.setValue(
                    SignalWireBlock.EAST,
                    true
                )),
            false
        );

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .text("The output remains 0 because no operation card has been inserted yet");

        scene.world().setBlock(inputWirePos.below(), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().setBlock(inputWirePos2.below(), Blocks.YELLOW_WOOL.defaultBlockState(), true);

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("This block has a GUI. Right-click to open it.")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);

        scene.overlay().showControls(operationBlockPos.getCenter(), Pointing.DOWN, 20)
            .withItem(RegisterItems.AND_CARD.get().getDefaultInstance());

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Insert a card into the slot to configure the operation")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);
        scene.addKeyframe();

        scene.overlay().showText(20)
            .independent()
            .text("There are many types of cards, each offering different operations and supporting various input and output port configurations");

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .text("Some cards behave differently depending on the number of input or output ports");

        scene.idle(40);

        scene.overlay().showControls(outputWirePos.getCenter(), Pointing.DOWN, 20)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_DETECTOR.get().getDefaultInstance());

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The output wire now reflects the result of the operation")
            .pointAt(outputWirePos.getCenter());

        scene.world().setBlock(outputWirePos.below(), Blocks.ORANGE_WOOL.defaultBlockState(), true);

        scene.idle(40);
        scene.addKeyframe();

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("If we disconnect the input wire from the source")
            .pointAt(inputWirePos.getCenter());

        scene.world().modifyBlock(inputWirePos, (blockState ->
                blockState.setValue(
                    SignalWireBlock.SOUTH,
                    false
                )),
            false
        );

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The output wire will retain the last calculated value even after disconnecting the input")
            .pointAt(outputWirePos.getCenter());

        scene.world().setBlock(inputWirePos.below(), Blocks.WHITE_WOOL.defaultBlockState(), true);

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .text("This is because the block could not perform the operation with \"No Signal\", so it stopped updating the output");

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .colored(PonderPalette.RED)
            .text("As shown earlier (Signal Wire's Ponder), the signal wire may temporarily hold \"No Signal\" for a few ticks when the input network is modified");

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .colored(PonderPalette.RED)
            .text("The operation block ignores \"No Signal\" and will not update the output wire to \"No Signal\", making it safe");

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .colored(PonderPalette.RED)
            .text("But if two sources are connected to the same input network, a lower signal value may briefly appear for a few ticks");

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .colored(PonderPalette.RED)
            .text("This WILL pass through the operation block and cause the output wire to temporarily hold an unexpected value for a few ticks");

        scene.idle(40);
        scene.markAsFinished();
    }
    public static void signalToRedstone(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("signal_to_redstone", "Signal to Redstone Converter Tutorial");
        scene.showBasePlate();

        BlockPos wirePos = util.grid().at(2, 1, 2);
        Vec3 wireNorthVec = wirePos.getCenter().add(0, 0, -1 / 4f);
        Object highlightWireNorth = new Object();
        AABB wireNorthBox = new AABB(wireNorthVec.add(-1 / 8f, -1 / 8f, -1 / 32f), wireNorthVec.add(1 / 8f, 1 / 8f, 1 / 32f));


        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                scene.world().showSection(util.select().position(x, 1, z), Direction.DOWN);
            }
        }

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The Signal to Redstone Converter looks just like a signal wire, but offers additional features")
            .pointAt(wirePos.getCenter());
        scene.idle(40);


        scene.overlay().showControls(wireNorthVec, Pointing.DOWN, 20)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_CONFIGURATOR.get().getDefaultInstance());

        scene.world().modifyBlock(wirePos, (blockState ->
                blockState.setValue(SignalWireBlock.NORTH, true)),
            false
        );

        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightWireNorth, wireNorthBox, 40);

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Right-click it with a Signal Configurator to connect or disconnect the wires")
            .pointAt(wirePos.getCenter());

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Unlike a signal wire, it can convert the signal value into redstone power")
            .independent();

        scene.idle(40);

        scene.overlay().showControls(wireNorthVec, Pointing.DOWN, 20)
            .rightClick()
            .withItem(Items.REDSTONE.getDefaultInstance());

        scene.world().modifyBlock(wirePos, (blockState ->
            blockState.setValue(SignalWireBlock.NORTH, false).setValue(SignalToRedstoneConverter.NORTH, true)),
            false
        );

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("If the wire holds a signal value from 0 to 15, it will output the same redstone power level")
            .pointAt(wirePos.getCenter());

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Signals below 0 output 0 redstone power; signals above 15 output 15 redstone power")
            .pointAt(wirePos.getCenter());

        scene.idle(40);

        scene.world().setBlock(wirePos.north(), Blocks.REDSTONE_WIRE.defaultBlockState()
            .setValue(RedStoneWireBlock.POWER, 15)
            .setValue(RedStoneWireBlock.NORTH, RedstoneSide.SIDE)
            .setValue(RedStoneWireBlock.SOUTH, RedstoneSide.SIDE),
            false);

        scene.effects().indicateRedstone(wirePos.north());

        scene.idle(40);

        scene.markAsFinished();
    }
    public static void constSignalBlockTutorial(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("const_signal_block_tutorial", "Const Signal Block Tutorial");
        scene.showBasePlate();

        BlockPos constSignalBlockPos = util.grid().at(2, 1, 2);

        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                scene.world().showSection(util.select().position(x, 1, z), Direction.DOWN);
            }
        }

        scene.world().modifyBlock(constSignalBlockPos, (blockState ->
            blockState.setValue(ConstSignalBlock.OUTPUT_DIRECTION,
                Direction.UP
            )), false);

        scene.idle(40);

        scene.overlay().showText(20).colored(PonderPalette.WHITE)
            .text("Const Signal Block is a block that outputs a constant signal value")
            .pointAt(constSignalBlockPos.getCenter());

        scene.idle(40);

        scene.overlay().showText(20).colored(PonderPalette.WHITE)
            .text("It has an output port that can be rotated using a Signal Configurator")
            .pointAt(constSignalBlockPos.getCenter());

        scene.idle(40);

        scene.overlay().showControls(constSignalBlockPos.getCenter(), Pointing.DOWN, 5)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_CONFIGURATOR.get().getDefaultInstance());

        scene.world().modifyBlock(constSignalBlockPos, (blockState ->
                blockState.setValue(ConstSignalBlock.OUTPUT_DIRECTION,
                    Direction.DOWN
                )), false);

        scene.idle(10);

        scene.overlay().showControls(constSignalBlockPos.getCenter(), Pointing.DOWN, 5)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_CONFIGURATOR.get().getDefaultInstance());

        scene.world().modifyBlock(constSignalBlockPos, (blockState ->
            blockState.setValue(ConstSignalBlock.OUTPUT_DIRECTION,
                Direction.NORTH
            )), false);

        scene.idle(40);

        scene.overlay().showText(20).colored(PonderPalette.WHITE)
            .text("In order to change the value it output, right click it without holding the configurator to open the GUI")
            .pointAt(constSignalBlockPos.getCenter());

        scene.overlay().showControls(constSignalBlockPos.getCenter(), Pointing.DOWN, 20)
            .rightClick();

        scene.idle(40);

        scene.overlay().showText(20).colored(PonderPalette.WHITE)
            .text("Enter the desired signal value in the text box, then press ESC to apply the new value")
            .independent();

        scene.idle(40);

        scene.overlay().showText(20).colored(PonderPalette.WHITE)
            .text("If the entered text is not a valid integer, the value will remain unchanged")
            .independent();

        scene.idle(40);

        scene.markAsFinished();
    }
    public static void redstoneToSignal(SceneBuilder scene, SceneBuildingUtil util) {
        Object highlightUpdatedBlocks = new Object();
        BlockPos chestPos = util.grid().at(4, 1, 2);
        BlockPos poweredBlockPos = util.grid().at(2, 1, 3);
        BlockPos undirectedRedstoneWirePos = util.grid().at(1, 1, 2);
        AABB chestInputBox = new AABB(chestPos.getCenter().add(-1.5f, -0.5f, -0.5f), chestPos.getCenter().add(0.5f, 0.5, 0.5f));
        AABB powerBlockBox = new AABB(poweredBlockPos.getCenter().add(2.5f, 1.5f, 1.5f), poweredBlockPos.getCenter().add(-0.5f, -0.5f, -0.5f));
        AABB undirectRedstoneWireBox = new AABB(undirectedRedstoneWirePos.getCenter().add(-1.5f, -0.5f, -1.5f), undirectedRedstoneWirePos.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.title("redstone_to_signal", "Redstone to Signal Converter Tutorial");
        scene.showBasePlate();

        BlockPos rtsBlockPos = util.grid().at(2, 1, 2);

        scene.world().showSection(util.select().position(rtsBlockPos), Direction.DOWN);
        scene.world().modifyBlock(rtsBlockPos, (blockState ->
                blockState.setValue(RedstoneToSignalConverterBlock.OUTPUT_DIRECTION,
                        Direction.WEST
                )), false);
        scene.idle(40);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Redstone to Signal Converter is a block that converts redstone power into a signal value")
            .pointAt(rtsBlockPos.getCenter());
        scene.idle(40);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("It has an output port that can be rotated using a Signal Configurator")
            .pointAt(rtsBlockPos.getCenter());
        scene.idle(40);
        scene.addKeyframe();
        scene.overlay().showControls(rtsBlockPos.getCenter(), Pointing.DOWN, 5)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_CONFIGURATOR.get().getDefaultInstance());
        scene.world().modifyBlock(rtsBlockPos, (blockState ->
            blockState.setValue(RedstoneToSignalConverterBlock.OUTPUT_DIRECTION,
                Direction.UP
            )), false);
        scene.idle(40);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("It supports all directions except receiving redstone input on the signal output side")
            .pointAt(rtsBlockPos.getCenter());
        for (int x = 0; x < 5; x++) {
            for (int z = 0; z < 5; z++) {
                for (int y = 1; y <= 2; y++) {
                    scene.world().showSection(util.select().position(x, y, z), Direction.DOWN);
                    scene.idle(1);
                }
            }
        }
        scene.idle(40);
        // text
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("It supports all vanilla Minecraft redstone input methods")
            .pointAt(rtsBlockPos.getCenter());

        scene.idle(40);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("For example, direct redstone input")
            .pointAt(chestInputBox.getCenter());
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightUpdatedBlocks, chestInputBox, 30);
        scene.idle(40);
        scene.overlay().showText(20)
                .colored(PonderPalette.WHITE)
                .text("A neighboring block that is powered by redstone")
                .pointAt(powerBlockBox.getCenter());
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightUpdatedBlocks, powerBlockBox, 30);
        scene.idle(40);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("By the way, undirected redstone wire does not function as an input")
            .pointAt(undirectRedstoneWireBox.getCenter());
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightUpdatedBlocks, undirectRedstoneWireBox, 30);
        scene.idle(40);
        scene.addKeyframe();
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("If there is no redstone signal input, the converter will output a signal value of 0")
            .pointAt(rtsBlockPos.getCenter());
        scene.idle(40);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("If there is any redstone signal input, it will output the highest redstone power as the signal value")
            .pointAt(rtsBlockPos.getCenter());
        scene.idle(40);

        scene.markAsFinished();
    }
    public static void signalResearchStationTutorial(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("signal_research_station_tutorial", "Signal Research Station Tutorial");
        scene.showBasePlate();

        // Base positions for the multiblock structure
        BlockPos researchStationPos = util.grid().at(2, 1, 2);
        BlockPos input1Pos = util.grid().at(1, 1, 2);
        BlockPos input2Pos = util.grid().at(1, 1, 1);
        BlockPos input3Pos = util.grid().at(1, 1, 3);
        BlockPos output1Pos = util.grid().at(3, 1, 2);
        BlockPos output2Pos = util.grid().at(2, 1, 3);
        BlockPos output3Pos = util.grid().at(2, 1, 1);
        BlockPos chamberPos = util.grid().at(3, 1, 1);
        Vec3 researchStationTopCenter = researchStationPos.getCenter().add(0, 0.5, 0);

        // Show the base research station first
        scene.world().showSection(util.select().position(researchStationPos), Direction.DOWN);
        scene.idle(20);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("The Signal Research Station is a multi-block structure used for researching and discovering signal-based recipes")
            .pointAt(researchStationTopCenter);

        scene.idle(70);

        // Show input blocks one by one
        scene.world().showSection(util.select().position(input1Pos), Direction.DOWN);
        scene.idle(20);
        scene.world().showSection(util.select().position(input2Pos), Direction.DOWN);
        scene.idle(20);
        scene.world().showSection(util.select().position(input3Pos), Direction.DOWN);
        scene.idle(20);

        scene.overlay().showText(60)
            .colored(PonderPalette.BLUE)
            .text("You can add up to 3 input blocks - these will be checked against recipe requirements")
            .pointAt(input1Pos.getCenter().add(0, 0.5, 0));

        scene.idle(70);

        // Show all 3 outputs simultaneously to emphasize they're required
        scene.world().showSection(util.select().position(output1Pos), Direction.DOWN);
        scene.world().showSection(util.select().position(output2Pos), Direction.DOWN);
        scene.world().showSection(util.select().position(output3Pos), Direction.DOWN);
        scene.idle(20);

        scene.overlay().showText(60)
            .colored(PonderPalette.RED)
            .text("The structure requires exactly 3 output blocks - no more, no less")
            .pointAt(output1Pos.getCenter().add(0, 0.5, 0));

        scene.idle(70);

        // Show chamber last
        scene.world().showSection(util.select().position(chamberPos), Direction.DOWN);
        scene.idle(20);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("A Research Item Chamber is required - it must be connected and will hold research ingredients")
            .pointAt(chamberPos.getCenter().add(0, 0.5, 0));

        scene.idle(70);

        // Now show assembly with configurator after all blocks are placed
        scene.overlay().showControls(researchStationTopCenter, Pointing.DOWN, 40)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_CONFIGURATOR.get().getDefaultInstance());

        scene.addKeyframe();

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("Once all blocks are placed, right-click the station with a Signal Configurator to assemble the structure")
            .pointAt(researchStationTopCenter);

        scene.idle(70);

        scene.overlay().showText(60)
                .colored(PonderPalette.WHITE)
                .text("Inputs 1, 2, and 3, as well as Outputs 1, 2, and 3, will be outlined from light to dark, while the item chamber will be outlined in white")
                .pointAt(researchStationTopCenter);

        scene.idle(70);

        // Continue with connection requirement highlight
        Object connectionHighlight = new Object();
        AABB connectionBox = new AABB(
            researchStationPos.getCenter().add(-1.5, -0.5, -1.5),
            researchStationPos.getCenter().add(1.5, 0.5, 1.5)
        );
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, connectionHighlight, connectionBox, 80);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("All blocks must form a connected structure - each block must touch the station or another component")
            .pointAt(researchStationTopCenter);

        scene.idle(70);

        // Recipe research process
        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("To start research, first reassemble the multiblock structure")
            .pointAt(researchStationTopCenter);

        scene.idle(70);

        scene.overlay().showControls(chamberPos.getCenter().add(0, 0.5, 0), Pointing.DOWN, 40)
            .rightClick();

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("Then open the Research Item Chamber GUI and place your research ingredients inside")
            .pointAt(chamberPos.getCenter().add(0, 0.5, 0));

        scene.idle(70);

        scene.addKeyframe();

        scene.overlay().showText(60)
                .colored(PonderPalette.GREEN)
                .text("The recipe inputs will be stored inside the signal research station when the recipe matches")
                .pointAt(chamberPos.getCenter().add(0, 0.5, 0));

        scene.idle(70);

        scene.overlay().showControls(chamberPos.getCenter(), Pointing.DOWN, 40)
                .withItem(Items.DIAMOND_PICKAXE.getDefaultInstance());

        scene.overlay().showText(60)
                .colored(PonderPalette.WHITE)
                .text("To retrieve the stored inputs, simply break the Research Item Chamber")
                .pointAt(chamberPos.getCenter());

        scene.idle(70);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("The chamber's GUI will indicate when research begins")
            .pointAt(chamberPos.getCenter().add(0, 0.5, 0));

        scene.idle(70);

        scene.addKeyframe();

        // Show GUI functionality
        scene.overlay().showControls(researchStationTopCenter, Pointing.DOWN, 40)
            .rightClick();

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("Right-click the station to view the real-time signal graph GUI")
            .pointAt(researchStationTopCenter);

        scene.idle(70);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("The GUI shows signal inputs on the left and outputs on the right")
            .pointAt(researchStationTopCenter);

        scene.idle(70);

        scene.overlay().showText(60)
            .colored(PonderPalette.GREEN)
            .text("When researching, three green lines on the left show the required signal values")
            .pointAt(researchStationTopCenter);

        scene.idle(70);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("Match your input signals to the green lines to progress research")
            .pointAt(researchStationTopCenter);

        scene.idle(70);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("With no active research, the required signal line stays at 0")
            .pointAt(researchStationTopCenter);

        scene.idle(70);

        // Final key points
        scene.overlay().showText(60)
            .colored(PonderPalette.RED)
            .text("Remember: Assemble structure > Add ingredients > Match required signals to research")
            .pointAt(researchStationTopCenter);

        scene.idle(70);

        scene.addKeyframe();

        // Place connected wires and operation block at y-level 2
        Selection wiresSelection = util.select().fromTo(0, 2, 0, 4, 2, 4);
        scene.world().showSection(util.select().fromTo(0, 2, 0, 4, 2, 4), Direction.DOWN);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("Each recipe requires you to build a circuit that processes signals")
            .pointAt(wiresSelection.getCenter());

        scene.idle(70);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("The circuit must take signals from the output blocks and transform them correctly")
            .pointAt(wiresSelection.getCenter());

        scene.idle(70);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("Then send the processed signals to the input blocks to match the recipe requirements")
            .pointAt(input1Pos.getCenter().add(0, 0.5, 0));

        scene.idle(70);

        scene.overlay().showText(60)
            .colored(PonderPalette.BLUE)
            .text("Here's an example circuit - you'll need to design different circuits for different recipes")
            .pointAt(wiresSelection.getCenter());

        scene.idle(70);

        scene.markAsFinished();
    }
    public static void signalWorldPortal(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("signal_world_portal", "Signal World Portal");
        // 8 8 6
        scene.idle(10);

        BlockPos portalBlockPos = util.grid().at(4, 3, 3);

        Selection portalStructure = util.select().fromTo(0, 0, 0, 8, 8, 6);
        scene.world().showSection(portalStructure, Direction.DOWN);
        scene.idle(20);
        scene.rotateCameraY(360);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("The Signal World Portal naturally generates in the overworld between Y levels -10 and 10")
            .pointAt(portalBlockPos.getCenter());
        scene.idle(70);

        Selection portalBlockSelection = util.select().fromTo(portalBlockPos, portalBlockPos);
        scene.addKeyframe();
        scene.overlay().showOutline(PonderPalette.BLUE, null, portalBlockSelection, 80);
        scene.overlay().showText(60)
            .colored(PonderPalette.BLUE)
            .text("The portal block in the center is your gateway to the Signal World")
            .pointAt(portalBlockPos.getCenter());

        scene.idle(70);
        scene.overlay().showText(60)
            .colored(PonderPalette.GREEN)
            .text("Crouch inside the portal block to be teleported to the Signal World")
            .pointAt(portalBlockPos.getCenter());
        scene.idle(70);

        scene.markAsFinished();
    }
    public static void signalWorldResources(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("signal_world_resources", "Signal World Resources");
        scene.showBasePlate();
        scene.idle(10);

        BlockPos blockingMaterialPos = util.grid().at(1, 1, 2);
        BlockPos aetheriteBallPos = util.grid().at(3, 1, 2);

        // Show both resources
        scene.world().showSection(util.select().position(blockingMaterialPos), Direction.DOWN);
        scene.idle(20);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("Signal Blocking Material can be found throughout the Signal World")
            .pointAt(blockingMaterialPos.getCenter());

        scene.idle(70);

        scene.overlay().showControls(blockingMaterialPos.getCenter(), Pointing.DOWN, 40)
            .withItem(Items.IRON_PICKAXE.getDefaultInstance());

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("You'll need at least an Iron Pickaxe to mine it")
            .pointAt(blockingMaterialPos.getCenter());

        scene.idle(70);

        // Show block breaking and item drops
        scene.world().destroyBlock(blockingMaterialPos);
        scene.idle(5);
        scene.world().createItemEntity(
            blockingMaterialPos.getCenter(),
            util.vector().of(0.1, 0.2, 0.05),
            RegisterItems.SIGNAL_BLOCKING_MATERIAL.get().getDefaultInstance()
        );

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("Breaking it drops Signal Blocking Material, which is used in crafting signal-related blocks")
            .pointAt(blockingMaterialPos.getCenter());

        scene.idle(70);

        scene.addKeyframe();

        scene.world().showSection(util.select().position(aetheriteBallPos), Direction.DOWN);
        scene.idle(20);

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("Aetherite Ceramic Balls can be found below Y level 0 in the Signal World")
            .pointAt(aetheriteBallPos.getCenter());

        scene.idle(70);

        scene.overlay().showControls(aetheriteBallPos.getCenter(), Pointing.DOWN, 40)
            .withItem(Items.IRON_SHOVEL.getDefaultInstance());

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("You can mine them with any type of shovel")
            .pointAt(aetheriteBallPos.getCenter());

        scene.idle(70);

        // Show block breaking and item drops
        scene.world().destroyBlock(aetheriteBallPos);
        scene.idle(5);
        scene.world().createItemEntity(
            aetheriteBallPos.getCenter(),
            util.vector().of(-0.1, 0.2, 0.05),
            RegisterItems.AETHERITE_CERAMIC_BALL.get().getDefaultInstance()
        );

        scene.overlay().showText(60)
            .colored(PonderPalette.WHITE)
            .text("Breaking it drops an Aetherite Ceramic Ball, a crucial component in signal research")
            .pointAt(aetheriteBallPos.getCenter());

        scene.idle(70);

        scene.markAsFinished();
    }
}
