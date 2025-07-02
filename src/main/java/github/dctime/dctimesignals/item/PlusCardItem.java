package github.dctime.dctimesignals.item;

import javax.annotation.Nullable;

public class PlusCardItem extends SignalOperationBaseCardItem{

    public PlusCardItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean checkIfPortsValid(int requiresPortConfig) {
        return (requiresPortConfig == (
            SignalOperationBaseCardItem.INPUT_BASE |
                SignalOperationBaseCardItem.INPUT2_BASE |
                SignalOperationBaseCardItem.OUTPUT_BASE)
            );
    }

    @Override
    @Nullable
    public Integer operation(@Nullable Integer value1, @Nullable Integer value2) {
        if (value1 == null) return null;
        if (value2 == null) return null;
        if (value1 > 0 && value2 > 0 && value1+value2 < 0) return null;
        if (value1 < 0 && value2 < 0 && value1+value2 > 0) return null;
        if (Math.abs(value1 + value2) > Integer.MAX_VALUE / 2 - 1) return null;
        return value1 + value2;
    }


}
