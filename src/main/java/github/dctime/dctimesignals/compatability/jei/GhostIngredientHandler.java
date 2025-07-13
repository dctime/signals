package github.dctime.dctimesignals.compatability.jei;

import github.dctime.dctimesignals.screen.GroundPenetratingSignalEmitterScreen;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class GhostIngredientHandler implements IGhostIngredientHandler<GroundPenetratingSignalEmitterScreen> {

    @Override
    public <I> List<Target<I>> getTargetsTyped(GroundPenetratingSignalEmitterScreen gui, ITypedIngredient<I> ingredient, boolean doStart) {
        System.out.println("Ingredient:" + ingredient.getItemStack().get().toString());
        // when the cursor touches jei items it triggers with the target range shows up
        return List.of(new ExampleTarget<>(gui, ingredient.getItemStack().get()));
    }

    @Override
    public void onComplete() {
        // run even when drag fails
        System.out.println("Ghost ingredient on complete.");
    }

    public static class ExampleTarget<I>  implements Target<I> {
        GroundPenetratingSignalEmitterScreen screen;
        ItemStack itemStack;
        public ExampleTarget(GroundPenetratingSignalEmitterScreen screen, ItemStack stack) {
            this.screen = screen;
            itemStack = stack;
        }

        @Override
        public Rect2i getArea() {
            return new Rect2i(0, 0, 100, 100);
        }

        @Override
        public void accept(I ingredient) {
            // if the item drags into it
            System.out.println("Accepted ingredient: " + ingredient.toString());
            screen.getMenu().setFilter(itemStack.copyWithCount(1));
        }
    }
}
