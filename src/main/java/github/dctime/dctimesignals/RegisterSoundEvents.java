package github.dctime.dctimesignals;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegisterSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, DCtimeMod.MODID);

    // All vanilla sounds use variable range events.
    public static final DeferredHolder<SoundEvent, SoundEvent> CONFIGURATOR_SOUND = SOUND_EVENTS.register(
            "configurator_sound", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "configurator_sound"))
    );

    // There is a currently unused method to register fixed range (= non-attenuating) events as well:
//    public static final DeferredHolder<SoundEvent, SoundEvent> CONFIGURATOR_SOUND = SOUND_EVENTS.register("configurator_sound",
//            // 16 is the default range of sounds. Be aware that due to OpenAL limitations,
//            // values above 16 have no effect and will be capped to 16.
//            () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "configurator_sound"), 16)
//    );
}
