package github.dctime.dctimesignals;

import github.dctime.dctimesignals.menu.*;
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

    public static final Supplier<MenuType<SignalResearchMenu>> SIGNAL_RESEARCH_MENU =
            MENUS.register("signal_research_menu", ()->new MenuType<>(SignalResearchMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static final Supplier<MenuType<SignalResearchItemChamberMenu>> SIGNAL_RESEARCH_ITEM_CHAMBER_MENU =
            MENUS.register("signal_research_item_chamber_menu", ()->new MenuType<>(SignalResearchItemChamberMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static final Supplier<MenuType<GroundPenetratingSignalEmitterMenu>> GROUND_PENETRATING_SIGNAL_EMITTER =
            MENUS.register("ground_penetrating_signal_emitter_menu", ()->new MenuType<>(GroundPenetratingSignalEmitterMenu::new, FeatureFlags.DEFAULT_FLAGS));

}
