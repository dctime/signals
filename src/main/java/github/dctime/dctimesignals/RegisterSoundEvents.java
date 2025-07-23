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

    public static final DeferredHolder<SoundEvent, SoundEvent> CARD_INSERT_SOUND = SOUND_EVENTS.register(
            "card_insert_sound", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "card_insert_sound"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> GPS_SUCCESS_SOUND = SOUND_EVENTS.register(
            "gps_success_sound", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "gps_success_sound"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> GPS_FAIL_SOUND = SOUND_EVENTS.register(
            "gps_fail_sound", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "gps_fail_sound"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SWITCH_MODE_SOUND = SOUND_EVENTS.register(
            "switch_mode_sound", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "switch_mode_sound"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> PICKAXE_ERROR_SOUND = SOUND_EVENTS.register(
            "pickaxe_error_sound", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "pickaxe_error_sound"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> PICKAXE_UPDATE_HUD_SOUND = SOUND_EVENTS.register(
            "pickaxe_update_hud_sound", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "pickaxe_update_hud_sound"))
    );

    // There is a currently unused method to register fixed range (= non-attenuating) events as well:
//    public static final DeferredHolder<SoundEvent, SoundEvent> CONFIGURATOR_SOUND = SOUND_EVENTS.register("configurator_sound",
//            // 16 is the default range of sounds. Be aware that due to OpenAL limitations,
//            // values above 16 have no effect and will be capped to 16.
//            () -> SoundEvent.createFixedRangeEvent(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "configurator_sound"), 16)
//    );
}
