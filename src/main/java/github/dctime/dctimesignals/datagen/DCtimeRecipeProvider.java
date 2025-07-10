package github.dctime.dctimesignals.datagen;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterBlockItems;
import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterItems;
import github.dctime.dctimesignals.datagen.signal_research.SignalResearchRecipeBuilder;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;



public class DCtimeRecipeProvider extends RecipeProvider {


    public DCtimeRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlockItems.SIGNAL_WIRE.get())
                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                .define('R', Items.REDSTONE)
                .pattern(" B ")
                .pattern("BRB")
                .pattern(" B ")
                .unlockedBy("has_signal_blocking_material", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.SIGNAL_BLOCKING_MATERIAL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlockItems.CONSTANT_SIGNAL_BLOCK_ITEM.get())
                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                .define('R', Items.REDSTONE)
                .define('S', Items.REDSTONE_BLOCK)
                .pattern("BRB")
                .pattern("BSB")
                .pattern("BBB")
                .unlockedBy("has_signal_blocking_material", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.SIGNAL_BLOCKING_MATERIAL))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, RegisterBlockItems.SINGAL_TO_REDSTONE_CONVERTER.get())
                .requires(RegisterBlockItems.SIGNAL_WIRE.get())
                .unlockedBy("has_signal_wire", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterBlockItems.SIGNAL_WIRE.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, RegisterBlockItems.SIGNAL_WIRE.get())
                .requires(RegisterBlockItems.SINGAL_TO_REDSTONE_CONVERTER.get())
                .unlockedBy("has_signal_to_redstone_converter", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterBlockItems.SINGAL_TO_REDSTONE_CONVERTER.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "wire_to_converter"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlockItems.SIGNAL_OPERATION_BLOCK.get())
                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                .define('R', Items.REDSTONE)
                .pattern("BBB")
                .pattern("BRB")
                .pattern("BBB")
                .unlockedBy("has_signal_blocking_material", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.SIGNAL_BLOCKING_MATERIAL))
                .save(recipeOutput);

//        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlockItems.REDSTONE_TO_SIGNAL_CONVERTER.get())
//                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
//                .define('S', RegisterBlockItems.SINGAL_TO_REDSTONE_CONVERTER.get())
//                .define('R', Items.REDSTONE)
//                .pattern("BRB")
//                .pattern("RSR")
//                .pattern("BRB")
//                .unlockedBy("has_signal_blocking_material", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.SIGNAL_BLOCKING_MATERIAL))
//                .save(recipeOutput);

        new SignalResearchRecipeBuilder(
                new ItemStack(RegisterBlockItems.REDSTONE_TO_SIGNAL_CONVERTER.get(), 1),
                RegisterBlocks.SIGNAL_RESEARCH_ITEM_CHAMBER.get().defaultBlockState(),
                new ItemStack(RegisterItems.SIGNAL_BLOCKING_MATERIAL.get(), 4),
                new ItemStack(Items.REDSTONE, 4),
                new ItemStack(RegisterBlockItems.SINGAL_TO_REDSTONE_CONVERTER.get(), 1),
                "A&B",
                "",
                "",
                "Two Outputs One Operation"
        ).unlockedBy("has_signal_research_station", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterBlockItems.SIGNAL_RESEARCH_STATION.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "redstone_to_signal_converter"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterItems.SIGNAL_CONFIGURATOR.get())
                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                .pattern(" B ")
                .pattern(" BB")
                .pattern("B  ")
                .unlockedBy("has_signal_blocking_material", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.SIGNAL_BLOCKING_MATERIAL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterItems.AND_CARD.get())
                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                .define('R', Items.REDSTONE)
                .pattern(" B ")
                .pattern("BRB")
                .unlockedBy("has_signal_blocking_material", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.SIGNAL_BLOCKING_MATERIAL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterItems.OR_CARD.get())
                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                .define('R', Items.REDSTONE)
                .pattern("BRB")
                .pattern(" B ")
                .unlockedBy("has_signal_blocking_material", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.SIGNAL_BLOCKING_MATERIAL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterItems.NOT_CARD.get())
                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                .define('R', Items.REDSTONE)
                .pattern("BBB")
                .pattern(" RB")
                .unlockedBy("has_signal_blocking_material", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.SIGNAL_BLOCKING_MATERIAL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterItems.SIGNAL_DETECTOR.get())
                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                .define('R', Items.REDSTONE)
                .pattern("R R")
                .pattern("BBB")
                .pattern("BBB")
                .unlockedBy("has_signal_blocking_material", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.SIGNAL_BLOCKING_MATERIAL))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlocks.SIGNAL_RESEARCH_STATION.get())
                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                .define('A', RegisterItems.AETHERITE_CERAMIC_BALL.get())
                .define('S', RegisterBlocks.SIGNAL_OPERATION_BLOCK.get())
                .pattern("BAB")
                .pattern("ASA")
                .pattern("BAB")
                .unlockedBy("has_signal_operation_block", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterBlockItems.SIGNAL_OPERATION_BLOCK))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT.get())
                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                .define('A', RegisterItems.AETHERITE_CERAMIC_BALL.get())
                .define('C', RegisterBlocks.CONSTANT_SIGNAL_BLOCK.get())
                .pattern("BAB")
                .pattern("ACA")
                .pattern("BAB")
                .unlockedBy("has_const_signal", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterBlockItems.CONSTANT_SIGNAL_BLOCK_ITEM))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, RegisterBlockItems.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT.get())
                .requires(RegisterBlockItems.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT.get())
                .unlockedBy("has_research_station_signal_output", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterBlockItems.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "research_output_to_input"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, RegisterBlockItems.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT.get())
                .requires(RegisterBlockItems.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT.get())
                .unlockedBy("has_research_station_signal_input", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterBlockItems.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "research_input_to_output"));

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, RegisterBlockItems.SIGNAL_RESEARCH_ITEM_CHAMBER.get())
                .define('B', RegisterItems.SIGNAL_BLOCKING_MATERIAL.get())
                .define('A', RegisterItems.AETHERITE_CERAMIC_BALL.get())
                .define('C', Items.CHEST)
                .pattern("BAB")
                .pattern("ACA")
                .pattern("BAB")
                .unlockedBy("has_ceramic_ball", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterItems.AETHERITE_CERAMIC_BALL))
                .save(recipeOutput);

    }
}
