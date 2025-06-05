package github.dctime.dctimemod.compatability.jade;

import github.dctime.dctimemod.DCtimeMod;
import github.dctime.dctimemod.block.SignalWireBlock;
import github.dctime.dctimemod.block.SignalWireBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.function.BiConsumer;

public enum SignalValueBlockComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_jade");
    public static final String signalValueTranslationKey = "tooltip." + DCtimeMod.MODID + ".jade_signal_value";
    public static final String noSignalValueTranslationKey = "tooltip." + DCtimeMod.MODID + ".jade_no_signal";

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("signalValue")) {
            iTooltip.add(Component.translatable(signalValueTranslationKey, blockAccessor.getServerData().getInt("signalValue")));
        }

        if (blockAccessor.getServerData().contains("noSignal")) {
            iTooltip.add(Component.translatable(noSignalValueTranslationKey));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof SignalWireBlockEntity entity) {
            Integer signalValue = entity.getSignalValue();
            if (signalValue != null) {
                compoundTag.putInt("signalValue", signalValue);
            } else {
                compoundTag.putBoolean("noSignal", true);
            }
        }
    }

    public static void dependenciesDataGen(BiConsumer<String, String> langConsumer) {
        // Register translations for Jade
        langConsumer.accept(signalValueTranslationKey, "Signal Value: %d");
        langConsumer.accept(noSignalValueTranslationKey, "No Signal");
    }
}
