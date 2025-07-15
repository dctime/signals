package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.geomodel.GroundPenetratingSignalEmitterGeoModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GroundPenetratingSignalEmitterBlockEntityRenderer extends GeoBlockRenderer<GroundPenetratingSignalEmitterBlockEntity> {
    public GroundPenetratingSignalEmitterBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(new GroundPenetratingSignalEmitterGeoModel());
    }


}
