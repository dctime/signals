package github.dctime.dctimemod.compatability.jade;

import github.dctime.dctimemod.DCtimeMod;
import github.dctime.dctimemod.block.ConstSignalBlock;
import github.dctime.dctimemod.block.ConstSignalBlockEntity;
import github.dctime.dctimemod.block.SignalOperationBlockEntity;
import github.dctime.dctimemod.block.SignalWireBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.units.qual.C;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.function.BiConsumer;

public class OutputSignalValueBlockComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    public static final OutputSignalValueBlockComponentProvider INSTANCE = new OutputSignalValueBlockComponentProvider();

    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "output_signal_jade");
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
    }

    public static void dependenciesDataGen(BiConsumer<String, String> langConsumer) {
        // Register translations for Jade
        langConsumer.accept(outputSignalValueTranslationKey, "Current Stored Output Signal Value: %d");
        langConsumer.accept("config.jade.plugin_dctimemod.output_signal_jade",
                "Show stored output values for Signal blocks with internal register in Jade");
    }
}
