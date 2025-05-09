package github.dctime.dctimemod.item;

import net.minecraft.world.item.Item;

import java.util.function.BiFunction;

public class NotCardItem extends Item implements SignalOperationBaseCard{

    public NotCardItem(Properties properties) {
        super(properties);
    }

    @Override
    public BiFunction<Integer, Integer, Integer> operation() {
        return (value, mask) -> (~value & mask) | (value & ~mask);
    }
}

