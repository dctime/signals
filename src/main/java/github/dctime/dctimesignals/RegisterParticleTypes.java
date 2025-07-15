package github.dctime.dctimesignals;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegisterParticleTypes {
    // Assuming that your mod id is examplemod
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, DCtimeMod.MODID);

    // The easiest way to add new particle types is reusing vanilla's SimpleParticleType.
    // Implementing a custom ParticleType is also possible, see below.
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GROUND_PENETRATING_SIGNAL_EMITTER_PARTICLE = PARTICLE_TYPES.register(
            // The name of the particle type.
            "ground_penetrating_signal_emitter_particle",
            // The supplier. The boolean parameter denotes whether setting the Particles option in the
            // video settings to Minimal will affect this particle type or not; this is false for
            // most vanilla particles, but true for e.g. explosions, campfire smoke, or squid ink.
            () -> new SimpleParticleType(false)
    );
}
