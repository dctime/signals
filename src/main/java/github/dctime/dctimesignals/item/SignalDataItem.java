package github.dctime.dctimesignals.item;

import github.dctime.dctimesignals.RegisterDataComponents;
import github.dctime.dctimesignals.data_component.SignalWaveformDataComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SignalDataItem extends Item {

    public SignalDataItem(Item.Properties properties) {
        super(properties);
    }

    // ── 寫入 ────────────────────────────────────────────────────────────────

    public static void writeData(ItemStack stack, String label,
                                 double[] times, double[] values) {
        int len = Math.min(times.length, values.length);
        List<Double> tList = new ArrayList<>(len);
        List<Double> vList = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            tList.add(times[i]);
            vList.add(values[i]);
        }
        stack.set(RegisterDataComponents.WAVEFORM.value(),
                new SignalWaveformDataComponent(label, tList, vList));
    }

    // ── 讀取 ────────────────────────────────────────────────────────────────

    public static boolean hasData(ItemStack stack) {
        SignalWaveformDataComponent w = stack.get(RegisterDataComponents.WAVEFORM.value());
        return w != null && !w.isEmpty();
    }

    public static SignalWaveformDataComponent getWaveform(ItemStack stack) {
        SignalWaveformDataComponent w = stack.get(RegisterDataComponents.WAVEFORM.value());
        return w != null ? w : SignalWaveformDataComponent.EMPTY;
    }

    public static double[] getTimes(ItemStack stack)  { return getWaveform(stack).timesArray(); }
    public static double[] getValues(ItemStack stack) { return getWaveform(stack).valuesArray(); }
    public static String   getLabel(ItemStack stack)  { return getWaveform(stack).label(); }

    // ── Tooltip ─────────────────────────────────────────────────────────────

    @Override
    public void appendHoverText(
            ItemStack stack,
            Item.TooltipContext context,
            List<Component> tooltipComponents,
            TooltipFlag tooltipFlag) {
        if (hasData(stack)) {
            SignalWaveformDataComponent w = getWaveform(stack);
            tooltipComponents.add(Component.literal("訊號: " + w.label()).withStyle(ChatFormatting.AQUA));
            tooltipComponents.add(Component.literal("資料點: " + w.times().size()).withStyle(ChatFormatting.GRAY));
        } else {
            tooltipComponents.add(Component.literal("尚無模擬數據").withStyle(ChatFormatting.DARK_GRAY));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

//    @Override
//    public Component getName(ItemStack stack) {
//        return hasData(stack)
//                ? Component.literal("訊號碟片 [" + getLabel(stack) + "]")
//                : Component.literal("空白訊號碟片");
//    }
}
