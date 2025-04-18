package github.dctime.dctimemod;

import github.dctime.dctimemod.block.BlockLootSubProvider;
import github.dctime.dctimemod.block.BuildHelperBlockModelProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid=DCtimeMod.MODID, bus= EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent.Client event) {
        // block loot tables
        event.createProvider(((packOutput, completableFuture) -> new LootTableProvider(
                packOutput,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(
                        BlockLootSubProvider::new,
                        LootContextParamSets.BLOCK
                )),
                completableFuture
        )));

        // block models
        event.createProvider(BuildHelperBlockModelProvider::new);
    }
}
