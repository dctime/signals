package github.dctime.dctimemod;

import github.dctime.dctimemod.block.BlockLootSubProvider;
import github.dctime.dctimemod.block.BuildHelperItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid=DCtimeMod.MODID, bus= EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

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
        generator.addProvider(
                event.includeClient(),
                new DCtimeBlockModelProvider(output, existingFileHelper)
        );

        // Will break
//        generator.addProvider(
//                event.includeClient(),
//                new SignalWireBlockModelProvider(output, existingFileHelper)
//        );

        //item models
        generator.addProvider(
                event.includeClient(),
                new BuildHelperItemModelProvider(output, existingFileHelper)
        );
        // language provider
        event.createProvider(DCtimeLanguageProvider::new);
    }
}
