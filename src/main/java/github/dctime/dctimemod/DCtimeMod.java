package github.dctime.dctimemod;

import github.dctime.dctimemod.block.RegisterBlockItems;
import github.dctime.dctimemod.block.RegisterMenuTypes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(DCtimeMod.MODID)
public class DCtimeMod {
    public static final String MODID = "dctimemod";

    public DCtimeMod(IEventBus modBus) {
        System.out.println("This is a mod made by DCtime");
        RegisterBlocks.BLOCKS.register(modBus);
        RegisterBlockItems.ITEMS.register(modBus);
        RegisterCreativeModeTab.CREATIVE_MODE_TABS.register(modBus);
        RegisterBlockEntities.BLOCK_ENTITY_TYPES.register(modBus);
        RegisterMenuTypes.MENUS.register(modBus);
    }
}