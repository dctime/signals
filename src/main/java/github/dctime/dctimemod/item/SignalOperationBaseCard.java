package github.dctime.dctimemod.item;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public interface SignalOperationBaseCard {
    BiFunction<Integer, Integer, Integer> operation();
}
