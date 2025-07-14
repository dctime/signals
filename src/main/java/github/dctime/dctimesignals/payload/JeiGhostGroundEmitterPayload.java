package github.dctime.dctimesignals.payload;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.events.SignalPickaxeRenderGuiLayerEvent;
import github.dctime.dctimesignals.menu.GroundPenetratingSignalEmitterMenu;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record JeiGhostGroundEmitterPayload(Tag itemstack) implements CustomPacketPayload {
    public static final Type<JeiGhostGroundEmitterPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "jei_ghost_ground_emitter"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, JeiGhostGroundEmitterPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.TAG,
            JeiGhostGroundEmitterPayload::itemstack,
            JeiGhostGroundEmitterPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleDataInServer(final JeiGhostGroundEmitterPayload data, final IPayloadContext context) {
        System.out.println("Calling Server to do something");
        if (context.player().containerMenu instanceof GroundPenetratingSignalEmitterMenu menu) {
            menu.setFilter(ItemStack.CODEC.parse(NbtOps.INSTANCE, data.itemstack).getOrThrow());
        }
    }

    public static void handleDataInClient(final JeiGhostGroundEmitterPayload data, final IPayloadContext context) {
        System.out.println("Calling Client to do something");
    }

}
