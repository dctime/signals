package github.dctime.dctimesignals;

import github.dctime.dctimesignals.block.SignalOperationBlockEntityRenderer;
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
    }
}
