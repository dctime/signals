package github.dctime.dctimesignals;

import com.simibubi.create.api.packager.InventoryIdentifier;
import github.dctime.dctimesignals.data_component.SignalPickaxeDataComponent;
import github.dctime.dctimesignals.data_component.SignalPickaxeHudDataComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RegisterDataComponents {
    // The specialized DeferredRegister.DataComponents simplifies data component registration and avoids some generic inference issues with the `DataComponentType.Builder` within a `Supplier`
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, DCtimeMod.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SignalPickaxeDataComponent>> SIGNAL_PICKAXE_DATA_COMPONENT = DATA_COMPONENTS.registerComponentType(
            "signal_pickaxe_data_component",
            builder -> builder
                    // The codec to read/write the data to disk
                    .persistent(SignalPickaxeDataComponent.BASIC_CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(SignalPickaxeDataComponent.BASIC_STREAM_CODEC)
    );

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SignalPickaxeHudDataComponent>> SIGNAL_PICKAXE_HUD_DATA_COMPONENT = DATA_COMPONENTS.registerComponentType(
            "signal_pickaxe_hud_data_component",
            builder -> builder
                    // The codec to read/write the data to disk
                    .persistent(SignalPickaxeHudDataComponent.BASIC_CODEC)
                    // The codec to read/write the data across the network
                    .networkSynchronized(SignalPickaxeHudDataComponent.BASIC_STREAM_CODEC)
    );

//    /// Component will not be saved to disk
//    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SignalPickaxeDataComponent>> TRANSIENT_EXAMPLE = DATA_COMPONENTS.registerComponentType(
//            "transient",
//            builder -> builder.networkSynchronized(SignalPickaxeDataComponent.BASIC_STREAM_CODEC)
//    );
//
//    // No data will be synced across the network
//    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SignalPickaxeDataComponent>> NO_NETWORK_EXAMPLE = DATA_COMPONENTS.registerComponentType(
//            "no_network",
//            builder -> builder
//                    .persistent(SignalPickaxeDataComponent.BASIC_CODEC)
//                    // Note we use a unit stream codec here
//                    .networkSynchronized(SignalPickaxeDataComponent.UNIT_STREAM_CODEC)
//    );
}
