package github.dctime.dctimesignals;

import github.dctime.dctimesignals.screen.ConstSignalScreen;
import github.dctime.dctimesignals.screen.FlawlessExchangerScreen;
import github.dctime.dctimesignals.screen.SignalOperationScreen;
import github.dctime.dctimesignals.screen.SignalResearchScreen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid= DCtimeMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterScreens {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(RegisterMenuTypes.FLAWLESS_EXCHANGER_MENU.get(), FlawlessExchangerScreen::new);
        event.register(RegisterMenuTypes.CONST_SIGNAL_MENU.get(), ConstSignalScreen::new);
        event.register(RegisterMenuTypes.SIGNAL_OPERATION_MENU.get(), SignalOperationScreen::new);
        event.register(RegisterMenuTypes.SIGNAL_RESEARECH_MENU.get(), SignalResearchScreen::new);
    }
}
