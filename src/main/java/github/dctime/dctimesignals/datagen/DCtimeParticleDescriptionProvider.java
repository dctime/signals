package github.dctime.dctimesignals.datagen;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class DCtimeParticleDescriptionProvider extends ParticleDescriptionProvider {
    protected DCtimeParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        spriteSet(RegisterParticleTypes.GROUND_PENETRATING_SIGNAL_EMITTER_PARTICLE.get(),
                ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "ground_penetrating_signal_emitter_particle/wave"));
    }
}
