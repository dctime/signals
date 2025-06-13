package github.dctime.dctimesignals.compatability.jade;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.block.ConstSignalBlockEntity;
import github.dctime.dctimesignals.block.RedstoneToSignalConverterBlockEntity;
import github.dctime.dctimesignals.block.SignalOperationBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.function.BiConsumer;

public class OutputSignalValueBlockComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    public static final OutputSignalValueBlockComponentProvider INSTANCE = new OutputSignalValueBlockComponentProvider();

    public static final String PROVIDER_NAME = "output_signal_jade";
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, PROVIDER_NAME);
    public static final String outputSignalValueTranslationKey = "tooltip." + DCtimeMod.MODID + ".jade_output_signal_value";

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("outputSignalValue")) {
            iTooltip.add(Component.translatable(outputSignalValueTranslationKey, blockAccessor.getServerData().getInt("outputSignalValue")));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof ConstSignalBlockEntity entity) {
            int value = entity.getOutputSignalValue();
            compoundTag.putInt("outputSignalValue", value);
        }

        if (blockAccessor.getBlockEntity() instanceof SignalOperationBlockEntity entity) {
            int value = entity.getOutputValue();
            compoundTag.putInt("outputSignalValue", value);
        }

        if (blockAccessor.getBlockEntity() instanceof RedstoneToSignalConverterBlockEntity entity) {
            int value = entity.getReceivedSignal();
            compoundTag.putInt("outputSignalValue", value);
        }
    }

    public static void dependenciesDataGen(BiConsumer<String, String> langConsumer) {
        // Register translations for Jade
        langConsumer.accept(outputSignalValueTranslationKey, "Current Stored Output Signal Value: %d");
        langConsumer.accept(DCtimeJadePlugin.getUIDTranslationKey(PROVIDER_NAME),
                "Show stored output values for Signal blocks with internal register in Jade");
    }
}
