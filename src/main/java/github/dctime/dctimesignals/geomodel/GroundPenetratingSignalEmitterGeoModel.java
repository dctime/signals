package github.dctime.dctimesignals.geomodel;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.block.GroundPenetratingSignalEmitterBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class GroundPenetratingSignalEmitterGeoModel extends GeoModel<GroundPenetratingSignalEmitterBlockEntity> {

    @Override
    public ResourceLocation getModelResource(GroundPenetratingSignalEmitterBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "geo/ground_penetrating_signal_emitter_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GroundPenetratingSignalEmitterBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "textures/block/ground_penetrating_signal_emitter_block.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GroundPenetratingSignalEmitterBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "animations/ground_penetrating_signal_emitter_block.animation.json");
    }
}
