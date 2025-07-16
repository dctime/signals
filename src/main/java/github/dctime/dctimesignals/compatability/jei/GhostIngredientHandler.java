package github.dctime.dctimesignals.compatability.jei;

import github.dctime.dctimesignals.block.GroundPenetratingSignalEmitterBlockEntity;
import github.dctime.dctimesignals.payload.JeiGhostGroundEmitterPayload;
import github.dctime.dctimesignals.screen.GroundPenetratingSignalEmitterScreen;
import mezz.jei.api.gui.handlers.IGhostIngredientHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class GhostIngredientHandler implements IGhostIngredientHandler<GroundPenetratingSignalEmitterScreen> {

    @Override
    public <I> List<Target<I>> getTargetsTyped(GroundPenetratingSignalEmitterScreen gui, ITypedIngredient<I> ingredient, boolean doStart) {
        // System.out.println("Ingredient:" + ingredient.getItemStack().get().toString());
        // when the cursor touches jei items it triggers with the target range shows up
        return List.of(new ExampleTarget<>(gui, ingredient.getItemStack().get()));
    }

    @Override
    public void onComplete() {
        // run even when drag fails
        // System.out.println("Ghost ingredient on complete.");
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
            if (!(itemStack.getItem() instanceof BlockItem)) return new Rect2i(0, 0, 0, 0);
            Slot filterSlot = screen.getMenu().slots.get(GroundPenetratingSignalEmitterBlockEntity.ITEMS_FILTER);
            return new Rect2i(screen.getGuiLeft() + filterSlot.x, screen.getGuiTop() + filterSlot.y, 16, 16);
        }

        @Override
        public void accept(I ingredient) {
            // if the item drags into it
            // System.out.println("Accepted ingredient: " + ingredient.toString());
            Tag itemTag = ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, itemStack).getOrThrow();
            PacketDistributor.sendToServer(new JeiGhostGroundEmitterPayload(itemTag));
        }
    }
}
