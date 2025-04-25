package github.dctime.dctimemod;

import github.dctime.dctimemod.block.FlawlessExchangerScreen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid= DCtimeMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterScreens {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(RegisterMenuTypes.FLAWLESS_EXCHANGER_MENU.get(), FlawlessExchangerScreen::new);
    }
}
