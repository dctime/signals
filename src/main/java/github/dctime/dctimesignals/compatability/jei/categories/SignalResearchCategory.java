package github.dctime.dctimesignals.compatability.jei.categories;

import com.simibubi.create.compat.jei.ItemIcon;
import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterBlockItems;
import github.dctime.dctimesignals.RegisterRecipeTypes;
import github.dctime.dctimesignals.recipe.SignalResearchRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class SignalResearchCategory implements IRecipeCategory<RecipeHolder<SignalResearchRecipe>> {
    public static final RecipeType<RecipeHolder<SignalResearchRecipe>> SIGNAL_RESEARCH_RECIPE_TYPE =
            RecipeType.createFromDeferredVanilla(() -> RegisterRecipeTypes.SIGNAL_RESEARCH_RECIPE_TYPE.get()).get();
    private static final ResourceLocation CONTAINER_TEXTURE = ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "textures/gui/jei/signal_research_station_item_chamber_screen.png");
    private static final ResourceLocation BULB_TEXTURE = ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "textures/gui/container/signal_research_station_item_chamber_light_bulb.png");
    private final IGuiHelper helper;
    private final IDrawableAnimated bulb;

    int imageWidth = 176;
    int imageHeight = 86;

    public SignalResearchCategory(IGuiHelper helper) {
        this.helper = helper;
        IDrawableBuilder bulbBuilder = helper.drawableBuilder(BULB_TEXTURE, 0, 0, 18, 22).setTextureSize(18, 22);
        IDrawableStatic texture = bulbBuilder.build();
        bulb = helper.createAnimatedDrawable(texture,100, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public RecipeType<RecipeHolder<SignalResearchRecipe>> getRecipeType() {
        return SIGNAL_RESEARCH_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("title.jei." + DCtimeMod.MODID + ".signal_research");
    }

    @Override
    public void draw(RecipeHolder<SignalResearchRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(CONTAINER_TEXTURE, 0, 0+1, 0, 0, imageWidth, imageHeight);
        bulb.draw(guiGraphics, 80-1, 34);
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return new ItemIcon(() -> RegisterBlockItems.SIGNAL_RESEARCH_STATION.get().getDefaultInstance());
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<SignalResearchRecipe> recipe, IFocusGroup focuses) {
//        // input
//        this.addSlot(new SlotItemHandler(itemSlots, SignalResearchItemChamberBlockEntity.ITEMS_INPUT_1_INDEX, 80, 56+1));
//        this.addSlot(new SlotItemHandler(itemSlots, SignalResearchItemChamberBlockEntity.ITEMS_INPUT_2_INDEX, 80-18, 56+1));
//        this.addSlot(new SlotItemHandler(itemSlots, SignalResearchItemChamberBlockEntity.ITEMS_INPUT_3_INDEX, 80+18, 56+1));
//        // output
//        this.addSlot(new SignalResearchItemChamberMenu.OutputSlotItemHand(itemSlots, SignalResearchItemChamberBlockEntity.ITEMS_OUTPUT_INDEX, 80, 16+1));
        builder.addInputSlot(80, 56+1).addItemStack(recipe.value().getInput1ItemStack());
        builder.addInputSlot(80-18, 56+1).addItemStack(recipe.value().getInput2ItemStack());
        builder.addInputSlot(80+18, 56+1).addItemStack(recipe.value().getInput3ItemStack());
        builder.addOutputSlot(80, 16+1).addItemStack(recipe.value().getResult());
    }

    @Override
    public int getWidth() {
        return imageWidth;
    }

    @Override
    public int getHeight() {
        return imageHeight;
    }
}
