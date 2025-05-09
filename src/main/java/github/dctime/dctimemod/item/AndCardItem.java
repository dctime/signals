package github.dctime.dctimemod.item;

import net.minecraft.world.item.Item;

import java.util.function.BiFunction;

public class AndCardItem extends Item implements SignalOperationBaseCard{

    public AndCardItem(Properties properties) {
        super(properties);
    }

    @Override
    public BiFunction<Integer, Integer, Integer> operation() {
        return (a, b) -> a & b;
    }
}
