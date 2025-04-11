package github.dctime.dctimemod;

import github.dctime.dctimemod.block.RegisterBlocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(DCtimeMod.MODID)
public class DCtimeMod {
    public static final String MODID = "dctimemod";

    public DCtimeMod(IEventBus modBus) {
        System.out.println("This is a mod made by DCtime");
        RegisterBlocks.BLOCKS.register(modBus);
    }
}