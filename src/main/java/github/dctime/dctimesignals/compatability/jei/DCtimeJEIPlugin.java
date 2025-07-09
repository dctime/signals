package github.dctime.dctimesignals.compatability.jei;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterRecipeTypes;
import github.dctime.dctimesignals.compatability.jei.categories.SignalResearchCategory;
import github.dctime.dctimesignals.recipe.SignalResearchRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
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
        List<RecipeHolder<SignalResearchRecipe>> recipes = manager.getAllRecipesFor(RegisterRecipeTypes.SIGNAL_RESEARCH_RECIPE_TYPE.get());

        registration.addRecipes(SignalResearchCategory.SIGNAL_RESEARCH_RECIPE_TYPE, recipes);
    }
}
