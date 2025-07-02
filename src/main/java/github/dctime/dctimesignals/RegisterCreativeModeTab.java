package github.dctime.dctimesignals;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegisterCreativeModeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DCtimeMod.MODID);

    public static final Supplier<CreativeModeTab> DCTIME_MOD_TAB = CREATIVE_MODE_TABS.register(
        "dctimesignalstab", ()->CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + DCtimeMod.MODID + ".dctimesignalstab"))
            .icon(()->RegisterBlockItems.BUILD_HELPER_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((params, output)->{
                output.accept(RegisterBlockItems.BUILD_HELPER_BLOCK_ITEM);
                output.accept(RegisterBlockItems.SIGNAL_WIRE);
                output.accept(RegisterBlockItems.CONSTANT_SIGNAL_BLOCK_ITEM);
                output.accept(RegisterBlockItems.SINGAL_TO_REDSTONE_CONVERTER);
                output.accept(RegisterBlockItems.SIGNAL_OPERATION_BLOCK);
                output.accept(RegisterBlockItems.REDSTONE_TO_SIGNAL_CONVERTER);
                output.accept(RegisterBlockItems.SIGNAL_BLOCKING_MATERIAL_BLOCK);
                output.accept(RegisterBlockItems.SIGNAL_RESEARCH_STATION);
                output.accept(RegisterBlockItems.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT);
                output.accept(RegisterBlockItems.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT);
                output.accept(RegisterBlockItems.SIGNAL_RESEARCH_ITEM_CHAMBER);
                output.accept(RegisterItems.SIGNAL_DETECTOR);
                output.accept(RegisterItems.AND_CARD);
                output.accept(RegisterItems.OR_CARD);
                output.accept(RegisterItems.NOT_CARD);
                output.accept(RegisterItems.PLUS_CARD);
                output.accept(RegisterItems.SIGNAL_CONFIGURATOR);
                output.accept(RegisterItems.SIGNAL_BLOCKING_MATERIAL);
            })
            .build()
    );

}
