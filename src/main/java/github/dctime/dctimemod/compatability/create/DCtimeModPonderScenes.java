package github.dctime.dctimemod.compatability.create;

import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class DCtimeModPonderScenes {
    public static void signalWire(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("signal_wire", "Propagate Signal Using Signal Wire");
        scene.showBasePlate();
        scene.idle(10);

        BlockPos wirePos = util.grid().at(2, 1, 2);
        scene.world().showSection(util.select().position(2, 1, 2), Direction.DOWN);
        scene.idle(10);
        scene.overlay().showText(40)
                .colored(PonderPalette.RED)
                .text("When signal wire is not connect to other blocks, it has no signal.")
                .pointAt(wirePos.getCenter());

        scene.addKeyframe();
        scene.idle(10);
        scene.markAsFinished();
    }
}
