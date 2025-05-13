package github.dctime.dctimemod.item;

import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

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
    @Nullable
    public Integer operation(@Nullable Integer value1, @Nullable Integer value2) {
        if (value1 == null) return null;

        if (value2 == null) {
            return ~value1;
        }

        return ~value1 & value2 | value1 & ~value2;
    }
}

