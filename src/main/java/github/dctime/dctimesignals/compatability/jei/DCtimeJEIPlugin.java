package github.dctime.dctimesignals.compatability.jei;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterMenuTypes;
import github.dctime.dctimesignals.RegisterRecipeTypes;
import github.dctime.dctimesignals.compatability.jei.categories.SignalResearchCategory;
import github.dctime.dctimesignals.menu.SignalResearchItemChamberMenu;
import github.dctime.dctimesignals.menu.SignalResearchMenu;
import github.dctime.dctimesignals.recipe.SignalResearchRecipe;
import github.dctime.dctimesignals.screen.GroundPenetratingSignalEmitterScreen;
import github.dctime.dctimesignals.screen.SignalResearchItemChamberScreen;
import github.dctime.dctimesignals.screen.SignalResearchScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;

import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.*;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import sun.misc.Signal;

import java.util.List;


@JeiPlugin
public class DCtimeJEIPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SignalResearchCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager manager = ServerLifecycleHooks.getCurrentServer().getRecipeManager();
        List<SignalResearchRecipe> recipes = manager.getAllRecipesFor(RegisterRecipeTypes.SIGNAL_RESEARCH_RECIPE_TYPE.get())
                .stream().map(element->element.value()).toList();

        registration.addRecipes(RegisterRecipeTypeForJEI.SIGNAL_RESEARCH_RECIPE_TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalysts(
                RegisterRecipeTypeForJEI.SIGNAL_RESEARCH_RECIPE_TYPE,
                RegisterBlocks.SIGNAL_RESEARCH_ITEM_CHAMBER,
                RegisterBlocks.SIGNAL_RESEARCH_STATION,
                RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT,
                RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT
        );
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SignalResearchItemChamberScreen.class, 80, 34, 18, 22, RegisterRecipeTypeForJEI.SIGNAL_RESEARCH_RECIPE_TYPE);
        registration.addGhostIngredientHandler(GroundPenetratingSignalEmitterScreen.class, new GhostIngredientHandler());
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(
                SignalResearchItemChamberMenu.class,
                RegisterMenuTypes.SIGNAL_RESEARCH_ITEM_CHAMBER_MENU.get(),
                RegisterRecipeTypeForJEI.SIGNAL_RESEARCH_RECIPE_TYPE,
                0, 4,
                4, 30
        );
    }
}
