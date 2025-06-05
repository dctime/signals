package github.dctime.dctimesignals.item;

import net.minecraft.world.item.Item;

import javax.annotation.Nullable;

public abstract class SignalOperationBaseCardItem extends Item {
    public SignalOperationBaseCardItem(Properties properties) {
        super(properties);
    }

    public static final int INPUT_BASE = 1;
    public static final int INPUT2_BASE = 2;
    public static final int OUTPUT_BASE = 4;

    abstract public boolean checkIfPortsValid(int requiresPortConfig);

    @Nullable
    public abstract Integer operation(@Nullable Integer value1, @Nullable Integer value2);
}
