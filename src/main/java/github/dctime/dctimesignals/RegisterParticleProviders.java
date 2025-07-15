package github.dctime.dctimesignals;

import github.dctime.dctimesignals.particle.GroundPenetratingSignalEmitterParticleProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = DCtimeMod.MODID, bus= EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterParticleProviders {
    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        // There are multiple ways to register providers, all differing in the functional type they provide in the
        // second parameter. For example, #registerSpriteSet represents a Function<SpriteSet, ParticleProvider<?>>:
        event.registerSpriteSet(RegisterParticleTypes.GROUND_PENETRATING_SIGNAL_EMITTER_PARTICLE.get(), GroundPenetratingSignalEmitterParticleProvider::new);
        // Other methods include #registerSprite, which is essentially a Supplier<TextureSheetParticle>,
        // and #registerSpecial, which maps to a Supplier<Particle>. See the source code of the event for further info.
    }
}
