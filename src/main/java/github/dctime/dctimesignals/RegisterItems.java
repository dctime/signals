package github.dctime.dctimesignals;

import github.dctime.dctimesignals.item.AndCardItem;
import github.dctime.dctimesignals.item.NotCardItem;
import github.dctime.dctimesignals.item.OrCardItem;
import github.dctime.dctimesignals.item.PlusCardItem;
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

    public static final DeferredItem<Item> SIGNAL_CONFIGURATOR = ITEMS.registerItem(
            "signal_configurator",
            Item::new, // The factory that the properties will be passed into.
            new Item.Properties() // The properties to use.
    );

    public static final DeferredItem<Item> AND_CARD = ITEMS.registerItem(
        "and_card",
        AndCardItem::new, // The factory that the properties will be passed into.
        new Item.Properties() // The properties to use.
    );
    public static final DeferredItem<Item> OR_CARD = ITEMS.registerItem(
        "or_card",
        OrCardItem::new, // The factory that the properties will be passed into.
        new Item.Properties() // The properties to use.
    );
    public static final DeferredItem<Item> NOT_CARD = ITEMS.registerItem(
        "not_card",
        NotCardItem::new, // The factory that the properties will be passed into.
        new Item.Properties() // The properties to use.
    );
    public static final DeferredItem<Item> PLUS_CARD = ITEMS.registerItem(
            "plus_card",
            PlusCardItem::new, // The factory that the properties will be passed into.
            new Item.Properties() // The properties to use.
    );
    public static final DeferredItem<Item> SIGNAL_BLOCKING_MATERIAL = ITEMS.registerItem(
        "signal_blocking_material",
        Item::new, // The factory that the properties will be passed into.
        new Item.Properties() // The properties to use.
    );




}
