package github.dctime.dctimesignals;

import github.dctime.dctimesignals.compatability.create.CreateDependencies;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = DCtimeMod.MODID, dist = Dist.CLIENT)
public class DCtimeModClient {
    public DCtimeModClient(IEventBus modBus) {
        modBus.addListener(this::doClientStuff);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        if (ModList.get().isLoaded("ponder")) {
            CreateDependencies.addDependencies();
        }
    }
}



