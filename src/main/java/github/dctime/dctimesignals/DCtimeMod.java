package github.dctime.dctimesignals;

import com.mojang.logging.LogUtils;
import github.dctime.dctimesignals.lib.NgSpiceRunner;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

import java.io.IOException;

@Mod(DCtimeMod.MODID)
public class DCtimeMod {
    public static final String MODID = "dctimesignals";

    public DCtimeMod(IEventBus modBus) {
        System.out.println("This is a mod made by DCtime");
        RegisterBlocks.BLOCKS.register(modBus);
        RegisterDataComponents.DATA_COMPONENTS.register(modBus);
        RegisterBlockItems.ITEMS.register(modBus);
        RegisterCreativeModeTab.CREATIVE_MODE_TABS.register(modBus);
        RegisterBlockEntities.BLOCK_ENTITY_TYPES.register(modBus);
        RegisterMenuTypes.MENUS.register(modBus);
        RegisterItems.ITEMS.register(modBus);
        RegisterFeatures.FEATURES.register(modBus);
        RegisterRecipeTypes.RECIPE_TYPES.register(modBus);
        RegisterRecipeSerializer.RECIPE_SERIALIZERS.register(modBus);
        RegisterParticleTypes.PARTICLE_TYPES.register(modBus);
        RegisterSoundEvents.SOUND_EVENTS.register(modBus);
    }

}