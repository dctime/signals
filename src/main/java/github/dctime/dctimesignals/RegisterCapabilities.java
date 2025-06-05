package github.dctime.dctimesignals;

import github.dctime.dctimesignals.block.SignalWireBlock;
import github.dctime.dctimesignals.block.SignalWireInformation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid= DCtimeMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterCapabilities {

    public static final BlockCapability<SignalWireInformation, @Nullable Direction> SIGNAL_VALUE =
            BlockCapability.create(
                    // Provide a name to uniquely identify the capability.
                    ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "signal_value"),
                    // Provide the queried type. Here, we want to look up `IItemHandler` instances.
                    SignalWireInformation.class,
                    Direction.class
            );

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                RegisterBlockEntities.FLAWLESS_EXCHANGER_BLOCK_ENTITY.get(),
                (entity, side)-> {
                    if (side == Direction.DOWN || side == Direction.UP || side == null) return entity.getEnergyCap();
                    else return null;
                }
        );

        event.registerBlockEntity(
                SIGNAL_VALUE,
                RegisterBlockEntities.SIGNAL_WIRE_BLOCK_ENTITY.get(),
                (entity, direction) -> {
                    if (SignalWireBlock.directionGotConnection(entity, direction)) {
                        return entity.getInformation();
                    } else return null;
                }
        );
    }


}
