package github.dctime.dctimesignals.block;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class SignalOperationBlockItem extends BlockItem {
    public SignalOperationBlockItem(Block block, Properties properties) {
        super(block, properties);
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
                    .append(Component.literal("Use ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("Configurator ").withStyle(ChatFormatting.GOLD))
                    .append("to set side mode").withStyle(ChatFormatting.GRAY));

            tooltipComponents.add(Component.empty()
                    .append(Component.literal("Only works when the ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("ports configuration matches the card").withStyle(ChatFormatting.GOLD)));

            tooltipComponents.add(Component.empty()
                    .append(Component.literal("Always connect ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("wires ").withStyle(ChatFormatting.GOLD))
                    .append(Component.literal("or ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("Signal to Redstone Converters ").withStyle(ChatFormatting.GOLD))
                    .append(Component.literal("to the ports.").withStyle(ChatFormatting.GRAY)));

            tooltipComponents.add(Component.empty()
                    .append(Component.literal("Connect directly to the ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("signal sources ").withStyle(ChatFormatting.RED))
                    .append(Component.literal("or another ").withStyle(ChatFormatting.GRAY))
                    .append(Component.literal("Operation block ").withStyle(ChatFormatting.RED))
                    .append(Component.literal("wont work.").withStyle(ChatFormatting.GRAY)));
        }
    }
}
