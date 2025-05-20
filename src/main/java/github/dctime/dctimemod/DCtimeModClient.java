package github.dctime.dctimemod;

import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = DCtimeMod.MODID, dist = Dist.CLIENT)
public class DCtimeModClient {
    public DCtimeModClient(IEventBus modBus) {
        modBus.addListener(this::doClientStuff);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        PonderIndex.addPlugin(new DCtimeModPonderPlugin());
    }
}



