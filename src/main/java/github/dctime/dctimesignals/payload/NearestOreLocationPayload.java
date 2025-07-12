package github.dctime.dctimesignals.payload;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.block.ConstSignalBlock;
import github.dctime.dctimesignals.block.ConstSignalBlockEntity;
import github.dctime.dctimesignals.events.SignalPickaxeRenderGuiLayerEvent;
import github.dctime.dctimesignals.menu.ConstSignalMenu;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record NearestOreLocationPayload(int x, int y, int z) implements CustomPacketPayload {
    public static final Type<NearestOreLocationPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "neareset_ore_location"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, NearestOreLocationPayload> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT,
        NearestOreLocationPayload::x,
        ByteBufCodecs.VAR_INT,
        NearestOreLocationPayload::y,
        ByteBufCodecs.VAR_INT,
        NearestOreLocationPayload::z,
        NearestOreLocationPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleDataInServer(final NearestOreLocationPayload data, final IPayloadContext context) {
        System.out.println("Calling Server to do something");
    }

    public static void handleDataInClient(final NearestOreLocationPayload data, final IPayloadContext context) {
        System.out.println("Calling Client to do something");
        BlockPos orePosition = new BlockPos(data.x(), data.y(), data.z());
        SignalPickaxeRenderGuiLayerEvent.updateOrePosition(orePosition);
    }

}
