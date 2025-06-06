package github.dctime.dctimesignals.compatability.create;

import github.dctime.dctimesignals.RegisterBlockItems;
import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterItems;
import github.dctime.dctimesignals.block.SignalOperationBlock;
import github.dctime.dctimesignals.block.SignalToRedstoneConverter;
import github.dctime.dctimesignals.block.SignalWireBlock;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.Ponder;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DCtimeModPonderScenes {

    public static void signalWireConnection(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("signal_wire_connection", "Setup the connections of signal wires");
        scene.showBasePlate();
        scene.idle(10);

        BlockPos wirePos = util.grid().at(2, 1, 2);
        scene.world().showSection(util.select().position(2, 1, 2), Direction.DOWN);
        scene.idle(10);
        scene.addKeyframe();
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("When signal wire is not connect to other blocks, it has no signal.")
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
        scene.overlay().showControls(wireConnectionSurface, Pointing.UP, 20).rightClick();
        scene.idle(20);
        scene.world().replaceBlocks(util.select().position(2, 1, 2), RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.NORTH, true), false);
        scene.idle(20);

        Object connectionHighlight2 = new Object();
        Vec3 wireConnectionSurface2 = util.vector().blockSurface(wirePos, Direction.WEST).add(0.4f, 0, 0);
        AABB wireClickSurface2 = new AABB(wireConnectionSurface2, wireConnectionSurface2);
        AABB wireClickSurfaceExpanded2 = wireClickSurface2.inflate(1 / 128f, 1 / 10f, 1 / 10f);
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, connectionHighlight2, wireClickSurfaceExpanded2, 60);
        scene.overlay().showControls(wireConnectionSurface2, Pointing.UP, 20).rightClick();
        scene.idle(20);
        scene.world().replaceBlocks(util.select().position(2, 1, 2), RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.NORTH, true).setValue(SignalWireBlock.WEST, true), false);
        scene.world().replaceBlocks(util.select().position(1, 1, 2), RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.EAST, true), false);
        scene.idle(20);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Right click the side of the signal wire with an empty hand to connect the wires")
            .pointAt(wireConnectionSurface2);
        scene.idle(40);

        scene.addKeyframe();

        Object connectionHighlight3 = new Object();
        Vec3 wireConnectionSurface3 = util.vector().blockSurface(wirePos, Direction.NORTH).add(0, 0, 0.15f);
        AABB wireClickSurface3 = new AABB(wireConnectionSurface3, wireConnectionSurface3);
        AABB wireClickSurfaceExpanded3 = wireClickSurface3.inflate(1 / 10f, 1 / 10f, 1 / 6f);
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, connectionHighlight3, wireClickSurfaceExpanded3, 40);
        scene.overlay().showControls(wireConnectionSurface3, Pointing.UP, 20).rightClick();
        scene.idle(20);
        scene.world().replaceBlocks(util.select().position(2, 1, 2), RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true), false);
        scene.idle(20);

        Object connectionHighlight4 = new Object();
        Vec3 wireConnectionSurface4 = util.vector().blockSurface(wirePos, Direction.WEST).add(0, 0, 0);
        AABB wireClickSurface4 = new AABB(wireConnectionSurface4, wireConnectionSurface4);
        AABB wireClickSurfaceExpanded4 = wireClickSurface4.inflate(1 / 3f, 1 / 10f, 1 / 10f);
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, connectionHighlight4, wireClickSurfaceExpanded4, 40);
        scene.overlay().showControls(wireConnectionSurface4, Pointing.UP, 20).rightClick();
        scene.idle(20);
        scene.world().replaceBlocks(util.select().position(2, 1, 2), RegisterBlocks.SINGAL_WIRE.get().defaultBlockState(), false);
        scene.world().replaceBlocks(util.select().position(1, 1, 2), RegisterBlocks.SINGAL_WIRE.get().defaultBlockState(), false);
        scene.idle(20);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Right click the connection of the wire with empty hand to disconnect the wire")
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

        scene.world().replaceBlocks(wireSelection1, RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true), false);
        scene.world().replaceBlocks(wireSelection2, RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true).setValue(SignalWireBlock.EAST, true), false);
        scene.world().replaceBlocks(wireSelection3, RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.EAST, true), false);

        scene.overlay().showText(20)
            .independent(10)
            .text("Signal is a state that a wire holds just like a redstone dust on the ground can hold redstone signal. It can be represented as an integer. This signal isn't related to real world signals.");

        scene.idle(20);
        scene.overlay().showControls(wireSelection2.getCenter(), Pointing.UP, 20).rightClick().withItem(RegisterItems.SIGNAL_DETECTOR.get().getDefaultInstance());
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Right click the wire with signal detector to detect the signal it holds.")
            .pointAt(wireSelection2.getCenter());
        scene.idle(30);


        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Connected wires will always have the same signal when stable")
            .pointAt(wireSelection2.getCenter());
        Object highlightConnectionWires = new Object();
        Vec3 wirePos2Center = wirePos2.getCenter();
        AABB wireClickSurface4 = new AABB(wirePos2Center.add(-(1.5f - 3 / 8f), -1 / 8f, -1 / 8f), wirePos2Center.add(1.5f - 3 / 8f, 1 / 8f, 1 / 8f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightConnectionWires, wireClickSurface4, 60);

        scene.idle(30);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Because the wires does not connect to a signal source in this case, it will prints out \"No Signal\"")
            .pointAt(wireSelection2.getCenter());

        scene.idle(30);
        scene.addKeyframe();

        scene.world().replaceBlocks(wireSelection1, RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true).setValue(SignalWireBlock.SOUTH, true), false);
        scene.idle(20);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Now the wires are connected to the signal source.")
            .pointAt(wireSelection1.getCenter());
        scene.world().replaceBlocks(util.select().position(wirePos1.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.RED_WOOL.defaultBlockState(), true);

        scene.idle(30);
        scene.overlay().showControls(wireSelection2.getCenter(), Pointing.UP, 20).rightClick().withItem(RegisterItems.SIGNAL_DETECTOR.get().getDefaultInstance());
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("If the signal source exports signal value 30, then the wires would say \"Signal Value: 30\" when right clicking with a signal detector")
            .pointAt(wireSelection2.getCenter());
        scene.idle(30);

        scene.addKeyframe();

        scene.overlay().showText(20)
            .independent(10)
            .text("Now we represent signal 30 as red wool while no signal as white wool");

        scene.idle(30);

        scene.world().replaceBlocks(wireSelection2, RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true), false);
        scene.world().replaceBlocks(wireSelection1, RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.SOUTH, true), false);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("When we try to disconnect the wires")
            .pointAt(wireSelection2.getCenter().add(0.5f, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The disconnected wires will become \"No Signal\"")
            .pointAt(wireSelection3.getCenter().add(0.5f, 0, 0));
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .text("What happened in just a few ticks?");
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.RED_WOOL.defaultBlockState(), true);

        scene.idle(30);

        scene.world().replaceBlocks(util.select().position(wirePos1.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The two wires which got disconnected with each other will try to tell other neighbor wires that the signal we hold might be \"No Signal\"")
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
            .text("Now these two got updated because the wires got updated is connected to them");

        scene.idle(30);

        scene.world().replaceBlocks(util.select().position(wirePos1.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The signal source would try to update the connected wire to its emitted signal value, causing the wire to be 30")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightUpdatedWire, updateWireBox, 20);
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("This wire got the command to set itself to no signal. While the connected wire is already set to no signal, the update chain stops")
            .pointAt(wirePos3.getCenter());

        scene.idle(30);

        Object highlightUpdatedWire1 = new Object();
        Vec3 highlightUpdatedWirePos1 = wirePos1.getCenter();
        AABB updateWireBox11 = new AABB(highlightUpdatedWirePos1.add(-1 / 8f, -1 / 8f, -1 / 8f), highlightUpdatedWirePos1.add(1 / 8f, 1 / 8f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, highlightUpdatedWire1, updateWireBox11, 60);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("For the left part, the wire tries to send signal value 30 to its connected neighbor.")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("There is no more neighbors connected that is below 30 or hold no signal so the update chain ends")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.addKeyframe();

        scene.overlay().showText(20)
            .colored(PonderPalette.RED)
            .text("This process should took only some ticks depends on the wire length")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.RED)
            .text("But we knows that the wire hold \"No signal\" for a few ticks then get back to signal value 30")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.RED)
            .text("This means that the wire might \"drop\" which may cause issues in your signal network")
            .pointAt(wirePos1.getCenter());

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.RED)
            .text("Most of the signal related blocks should have ignore \"No Signal\" but just in case")
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

        scene.world().replaceBlocks(wireSelection1, RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.WEST, true).setValue(SignalWireBlock.SOUTH, true), false);
        scene.world().replaceBlocks(wireSelection2, RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.EAST, true), false);
        scene.world().replaceBlocks(wireSelection3, RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.SOUTH, true), false);

        scene.overlay().showText(20)
            .independent(10)
            .text("When two sources want to overwrite the wires value, Higher signal value overwrites lower signal value");

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .text("In this scene, two sources high value source marked as red wool and low value source marked as yellow wool outputs to two different circuits");

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .text("When the circuit is stable, wires that connected to each other will have the same value");

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .text("The net which is connected to high value source would be high while low value source would be low");

        scene.world().replaceBlocks(util.select().position(wirePos1.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos2.below()), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().replaceBlocks(util.select().position(wirePos3.below()), Blocks.YELLOW_WOOL.defaultBlockState(), true);

        scene.idle(30);

        scene.addKeyframe();

        scene.world().replaceBlocks(wireSelection2, RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.EAST, true).setValue(SignalWireBlock.WEST, true), false);
        scene.world().replaceBlocks(wireSelection3, RegisterBlocks.SINGAL_WIRE.get().defaultBlockState().setValue(SignalWireBlock.SOUTH, true).setValue(SignalWireBlock.EAST, true), false);
        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("When we connect the wires together...")
            .pointAt(wireSelection2.getCenter().add(-0.5f, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The connected wires would reset its wire value to \"No Signal\" then telling its connected neighbors to update itself to \"No Signal\"")
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
            .text("For the left part the wire reset its wire value to \"No Signal\" then telling its connected neighbors to update itself to \"No Signal\" when received the command")
            .pointAt(wireSelection1.getCenter().add(0, 0, 0));

        AABB updateBlockBox2 = new AABB(sourceHighPos.getCenter().add(-0.5f, -0.5f, -0.5f), sourceHighPos.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.WHITE, highlightUpdatedBlocks, updateBlockBox2, 210);
        AABB updateBlockBox3 = new AABB(wirePos2.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos2.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.WHITE, highlightUpdatedBlocks2, updateBlockBox3, 50);
        scene.world().replaceBlocks(util.select().position(wirePos1.below()), Blocks.WHITE_WOOL.defaultBlockState(), true);

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("This wire is already set to \"No Signal\" so the update chain ends")
            .pointAt(wireSelection2.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.addKeyframe();

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Now for the right part.")
            .pointAt(sourceLowPos.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The source would try to overwrite the connected wire value to its value")
            .pointAt(sourceLowPos.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("It cannot overwrite the wire when the signal value the wire holds is larger than the value it overwrites")
            .pointAt(sourceLowPos.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("In this case the wire holds \"No Signal\" that the source can overwrite the value and tell the wire to update it's connected neighbors")
            .pointAt(sourceLowPos.getCenter().add(0, 0, 0));

        AABB updateBlockBox5 = new AABB(wirePos3.getCenter().add(-0.5f, -0.5f, -0.5f), wirePos3.getCenter().add(0.5f, 0.5f, 0.5f));
        scene.overlay().chaseBoundingBoxOutline(PonderPalette.OUTPUT, highlightUpdatedBlocks3, updateBlockBox5, 30);

        scene.idle(30);

        scene.addKeyframe();

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The wire updated itself to low value and telling it's neighbor to update itself to low value")
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
            .text("Fast forward a bit");

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
            .text("This wire got a command to overwrite to low value but it holds high value.")
            .pointAt(wirePos1.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Hence, the wire doesn't overwrite itself and stop the updating chain")
            .pointAt(wirePos1.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("This wire got a command to overwrite to high value and it holds low value.")
            .pointAt(wirePos2.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Hence it updates itself and tell connected neighbors to update to high value")
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
            .text("This wire got a command to overwrite to high value but it already holds high. Stops the updating chain")
            .pointAt(wirePos1.getCenter().add(0, 0, 0));

        scene.idle(30);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("This wire got a command to overwrite to high value and it holds low value. Update and tell neighbors")
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
            .text("Source tries to overwrite the output port's wire")
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
            .text("We can see that when there are two output source trying to output to the same net");

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .colored(PonderPalette.RED)
            .text("The net holds the highest signal value of all the sources");

        scene.idle(30);

        scene.overlay().showText(20)
            .independent(10)
            .colored(PonderPalette.RED)
            .text("And when the circuit is unstable, the value of the wire might bounce up and down which may cause unwanted behaviour");

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
            .text("Operation block is a block that can perform operations on the signal it receives")
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
            .text("Right click the operation block with a signal configurator to configure the side mode")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);
        // end of configNorthHighlightTime

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The side can be set to INPUT, INPUT2, and OUTPUT")
            .pointAt(operationBlockPosNorth);

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("INPUT port has a white arrow like texture pointing into the block")
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
            .text("INPUT2 port has a blue arrow like texture pointing into the block")
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
            .text("OUTPUT port has a white arrow like texture pointing out of the block")
            .pointAt(operationBlockPosNorth);

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Now we set east side to INPUT")
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
            .text("And west side to INPUT2")
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
            .text("When we connected the output port")
            .pointAt(outputWirePos.getCenter());

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Because it has an internal buffer in it that stores the last signal calculated")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("and it stores signal value 0 in default")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);

        scene.overlay().showControls(outputWirePos.getCenter(), Pointing.DOWN, 20)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_DETECTOR.get().getDefaultInstance());

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("so the output wire would say \"Signal Value: 0\" when right clicked with a signal detector")
            .pointAt(outputWirePos.getCenter());

        scene.world().setBlock(outputWirePos.below(), Blocks.BLACK_WOOL.defaultBlockState(), true);

        scene.idle(40);

        scene.overlay().showControls(operationBlockPos.getCenter(), Pointing.DOWN, 20)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_DETECTOR.get().getDefaultInstance());

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("By the way the operation block would say \"Output Signal Value: 0\" when right clicked with a signal detector")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);

        scene.addKeyframe();

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Now when we connects the input ports to the wires")
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
            .text("The output still remains 0 because we did not insert a operation card");

        scene.world().setBlock(inputWirePos.below(), Blocks.RED_WOOL.defaultBlockState(), true);
        scene.world().setBlock(inputWirePos2.below(), Blocks.YELLOW_WOOL.defaultBlockState(), true);

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("The block has a GUI attached to it. Right click to open the GUI")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);

        scene.overlay().showControls(operationBlockPos.getCenter(), Pointing.DOWN, 20)
            .withItem(RegisterItems.AND_CARD.get().getDefaultInstance());

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("There is a slot in it. Insert a card into it")
            .pointAt(operationBlockPos.getCenter());

        scene.idle(40);
        scene.addKeyframe();

        scene.overlay().showText(20)
            .independent()
            .text("There are a lot of types of cards with different operations and different input/output ports");

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .text("Some behaves different when the number of input/output ports are different");

        scene.idle(40);

        scene.overlay().showControls(outputWirePos.getCenter(), Pointing.DOWN, 20)
            .rightClick()
            .withItem(RegisterItems.SIGNAL_DETECTOR.get().getDefaultInstance());

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("Now the output wire should have the value of the operation result")
            .pointAt(outputWirePos.getCenter());

        scene.world().setBlock(outputWirePos.below(), Blocks.ORANGE_WOOL.defaultBlockState(), true);

        scene.idle(40);
        scene.addKeyframe();

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("If we disconnect the input wire to the source")
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
            .text("The output wire would still hold the last calculated value")
            .pointAt(outputWirePos.getCenter());

        scene.world().setBlock(inputWirePos.below(), Blocks.WHITE_WOOL.defaultBlockState(), true);

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .text("This is because the block was unable to do the operation with \"No Signal\" which stop updating the output");

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .colored(PonderPalette.RED)
            .text("From the ponder of signal wire, we know that the wire would hold \"No Signal\" for a few ticks when the input network got modified");

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .colored(PonderPalette.RED)
            .text("The operation block ignores \"No Signal\" and would not update the output wire to \"No Signal\" which makes it safe");

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .colored(PonderPalette.RED)
            .text("But if there are two sources connected to the same input network then the lower signal value that only exist for a few ticks");

        scene.idle(40);

        scene.overlay().showText(20)
            .independent()
            .colored(PonderPalette.RED)
            .text("WILL pass through the operation block and cause the output wire to hold a unexpected value than it should be for a few ticks");

        scene.idle(40);
        scene.markAsFinished();
    }

    public static void signalToRedstone(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("signal_to_redstone", "Signal to Redstone Tutorial");
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
            .text("Signal to Redstone Converter look identical to a signal wire but with more functionalities")
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
            .text("Right click it with a signal configurator to configure the side mode")
            .pointAt(wirePos.getCenter());

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("What it is different from a signal wire is that it can convert the signal value to redstone power")
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
            .text("Now if the wire holds 0 to 15 signal value, it would output 0 to 15 redstone power")
            .pointAt(wirePos.getCenter());

        scene.idle(40);

        scene.overlay().showText(20)
            .colored(PonderPalette.WHITE)
            .text("And signal below 0 would output 0 redstone power and signal above 15 would output 15 redstone power")
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
}
