package github.dctime.dctimesignals;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(DCtimeMod.MODID)
public class DCtimeMod {
    public static final String MODID = "dctimesignals";

    public DCtimeMod(IEventBus modBus) {
        System.out.println("This is a mod made by DCtime");
        RegisterBlocks.BLOCKS.register(modBus);
        RegisterBlockItems.ITEMS.register(modBus);
        RegisterCreativeModeTab.CREATIVE_MODE_TABS.register(modBus);
        RegisterBlockEntities.BLOCK_ENTITY_TYPES.register(modBus);
        RegisterMenuTypes.MENUS.register(modBus);
        RegisterItems.ITEMS.register(modBus);
        RegisterFeatures.FEATURES.register(modBus);

    }

}