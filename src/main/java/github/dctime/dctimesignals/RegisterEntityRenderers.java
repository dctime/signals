package github.dctime.dctimesignals;

import github.dctime.dctimesignals.block.GroundPenetratingSignalEmitterBlockEntityRenderer;
import github.dctime.dctimesignals.block.SignalOperationBlockEntityRenderer;
import github.dctime.dctimesignals.block.SignalResearchStationBlockEntityRenderer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid= DCtimeMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterEntityRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(RegisterBlockEntities.SIGNAL_OPERATION_BLOCK_ENTITY.get(),
            // Pass the context to an empty (default) constructor call
            SignalOperationBlockEntityRenderer::new
        );

        event.registerBlockEntityRenderer(RegisterBlockEntities.SIGNAL_RESEARCH_STATION_BLOCK_ENTITY.get(),
                // Pass the context to an empty (default) constructor call
                SignalResearchStationBlockEntityRenderer::new
        );

        event.registerBlockEntityRenderer(RegisterBlockEntities.GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK_ENTITY.get(),
                GroundPenetratingSignalEmitterBlockEntityRenderer::new);
    }
}
