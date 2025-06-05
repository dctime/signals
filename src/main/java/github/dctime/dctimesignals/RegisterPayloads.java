package github.dctime.dctimesignals;

import github.dctime.dctimesignals.payload.ConstSignalValueChangePayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid= DCtimeMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterPayloads {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
            ConstSignalValueChangePayload.TYPE,
            ConstSignalValueChangePayload.STREAM_CODEC,
            new DirectionalPayloadHandler<>(
                ConstSignalValueChangePayload::handleDataInClient,
                ConstSignalValueChangePayload::handleDataInServer
            )
        );
    }
}
