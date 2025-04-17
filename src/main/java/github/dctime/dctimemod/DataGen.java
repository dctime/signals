package github.dctime.dctimemod;

import com.nimbusds.jose.util.Resource;
import github.dctime.dctimemod.block.BlockLootSubProvider;
import github.dctime.dctimemod.block.BlockModelProvider;
import github.dctime.dctimemod.block.RegisterBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
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
        event.createProvider(BlockModelProvider::new);
    }
}
