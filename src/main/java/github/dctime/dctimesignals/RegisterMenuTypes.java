package github.dctime.dctimesignals;

import github.dctime.dctimesignals.menu.ConstSignalMenu;
import github.dctime.dctimesignals.menu.FlawlessExchangerMenu;
import github.dctime.dctimesignals.menu.SignalOperationMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegisterMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, DCtimeMod.MODID);
    // client menu
    public static final Supplier<MenuType<FlawlessExchangerMenu>> FLAWLESS_EXCHANGER_MENU =
            MENUS.register("flawless_exchanger_menu", ()->new MenuType<>(FlawlessExchangerMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static final Supplier<MenuType<ConstSignalMenu>> CONST_SIGNAL_MENU =
            MENUS.register("const_signal_menu", ()->new MenuType<>(ConstSignalMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static final Supplier<MenuType<SignalOperationMenu>> SIGNAL_OPERATION_MENU =
        MENUS.register("signal_operation_menu", ()->new MenuType<>(SignalOperationMenu::new, FeatureFlags.DEFAULT_FLAGS));

}
