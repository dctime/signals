package github.dctime.dctimesignals.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nullable;
import java.util.List;

public class NotCardItem extends SignalOperationBaseCardItem{

    public NotCardItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean checkIfPortsValid(int requiresPortConfig) {
        return (requiresPortConfig == (
            SignalOperationBaseCardItem.INPUT_BASE |
                SignalOperationBaseCardItem.INPUT2_BASE |
                SignalOperationBaseCardItem.OUTPUT_BASE)
        ) || (requiresPortConfig == (
            SignalOperationBaseCardItem.INPUT_BASE |
                SignalOperationBaseCardItem.OUTPUT_BASE
            ));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if (!tooltipFlag.hasShiftDown())
            tooltipComponents.add(Component.empty()
                    .append(Component.literal("Press ").withStyle(ChatFormatting.WHITE))
                    .append(Component.literal("[SHIFT] ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("for more info").withStyle(ChatFormatting.WHITE)));

        if (tooltipFlag.hasShiftDown()) {
            tooltipComponents.add(Component.empty()
                    .append(Component.literal("Gray Input / Blue Input / Gray Output ").withStyle(ChatFormatting.GOLD))
                    .append(Component.literal("Port Configuration Supported ").withStyle(ChatFormatting.GRAY)));

            tooltipComponents.add(Component.empty()
                    .append(Component.literal("NOTs Gray Input Signal ").withStyle(ChatFormatting.GOLD))
                    .append(Component.literal("with a mask defined by ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("Blue Input Signal ").withStyle(ChatFormatting.GOLD)));

            tooltipComponents.add(Component.empty()
                    .append(Component.literal("Export result to ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("Gray Output").withStyle(ChatFormatting.GOLD)));

            tooltipComponents.add(Component.empty());

            tooltipComponents.add(Component.empty()
                    .append(Component.literal("Gray Input / Gray Output ").withStyle(ChatFormatting.GOLD))
                    .append(Component.literal("Port Configuration Supported ").withStyle(ChatFormatting.GRAY)));

            tooltipComponents.add(Component.empty()
                    .append(Component.literal("NOTs Gray Input Signal ").withStyle(ChatFormatting.GOLD)));

            tooltipComponents.add(Component.empty()
                    .append(Component.literal("Export result to ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("Gray Output").withStyle(ChatFormatting.GOLD)));
        }
    }

    @Override
    @Nullable
    public Integer operation(@Nullable Integer value1, @Nullable Integer value2) {
        if (value1 == null) return null;

        if (value2 == null) {
            return ~value1;
        }

        return ~value1 & value2 | value1 & ~value2;
    }
}

