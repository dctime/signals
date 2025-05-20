package github.dctime.dctimemod;

import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.PonderStoryBoard;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class DCtimeModPonderScenes {
    public static void signalWire(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("signal_wire", "Propagate Signal Using Signal Wire");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.idle(10);

        BlockPos wire = util.grid().at(2, 1, 2);
        scene.world().showSection(util.select().position(wire), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().position(wire.above()), Direction.DOWN);
        scene.idle(10);
    }
}
