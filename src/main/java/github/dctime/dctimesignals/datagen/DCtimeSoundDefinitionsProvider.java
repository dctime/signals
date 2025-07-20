package github.dctime.dctimesignals.datagen;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterSoundEvents;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class DCtimeSoundDefinitionsProvider extends SoundDefinitionsProvider {
    protected DCtimeSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, DCtimeMod.MODID, helper);
    }

    @Override
    public void registerSounds() {
        // Accepts a Supplier<SoundEvent>, a SoundEvent, or a ResourceLocation as the first parameter.
        add(RegisterSoundEvents.CONFIGURATOR_SOUND, SoundDefinition.definition()
                // Add sound objects to the sound definition. Parameter is a vararg.
                .with(
                        // Accepts either a string or a ResourceLocation as the first parameter.
                        // The second parameter can be either SOUND or EVENT, and can be omitted if the former.
                        sound(DCtimeMod.MODID + ":configurator_sound", SoundDefinition.SoundType.SOUND)
                                // Sets the volume. Also has a double counterpart.
                                .volume(0.8f)
                                // Sets the pitch. Also has a double counterpart.
                                .pitch(1)
                                // Sets the weight.
                                .weight(1)
                                // Sets the attenuation distance.
                                .attenuationDistance(8)
                                // Enables streaming.
                                // Also has a parameterless overload that defers to stream(true).
                                .stream(true)
                                // Enables preloading.
                                // Also has a parameterless overload that defers to preload(true).
                                .preload(true)
                        // The shortest we can get.
//                        sound("examplemod:sound_2")
                )
                // Sets the subtitle.
                .subtitle("sound." + DCtimeMod.MODID + ".configurator_sound")
                // Enables replacing.
                .replace(false)
        );
    }
}
