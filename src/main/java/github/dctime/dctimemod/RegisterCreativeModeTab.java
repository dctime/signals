package github.dctime.dctimemod;

import github.dctime.dctimemod.block.RegisterBlockItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegisterCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DCtimeMod.MODID);

    public static final Supplier<CreativeModeTab> DCTIME_MOD_TAB = CREATIVE_MODE_TABS.register(
        "dctimemodtab", ()->CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + DCtimeMod.MODID + ".dctimemodtab"))
            .icon(()->RegisterBlockItems.BUILD_HELPER_BLOCK_ITEM.get().getDefaultInstance())
            .withSearchBar()
            .displayItems((params, output)->{
                output.accept(RegisterBlockItems.BUILD_HELPER_BLOCK_ITEM);
            })
            .build()
    );

}
