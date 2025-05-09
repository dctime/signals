package github.dctime.dctimemod;

import github.dctime.dctimemod.block.ConstSignalScreen;
import github.dctime.dctimemod.block.FlawlessExchangerScreen;
import github.dctime.dctimemod.block.SignalOperationScreen;
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
    }
}
