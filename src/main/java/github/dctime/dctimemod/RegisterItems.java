package github.dctime.dctimemod;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegisterItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DCtimeMod.MODID);

    public static final DeferredItem<Item> SIGNAL_DETECTOR = ITEMS.registerItem(
        "signal_detector",
        Item::new, // The factory that the properties will be passed into.
        new Item.Properties() // The properties to use.
    );
}
