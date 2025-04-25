package github.dctime.dctimemod;

import github.dctime.dctimemod.block.FlawlessExchangerMenu;
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
}
