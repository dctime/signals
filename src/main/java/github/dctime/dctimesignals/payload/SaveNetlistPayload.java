package github.dctime.dctimesignals.payload;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.block.NetlistEditorBlockEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SaveNetlistPayload(BlockPos pos, String netlist) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SaveNetlistPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "save_netlist"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, SaveNetlistPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            SaveNetlistPayload::pos,
            ByteBufCodecs.STRING_UTF8,
            SaveNetlistPayload::netlist,
            SaveNetlistPayload::new
    );

    public static void handleDataOnMain(final SaveNetlistPayload payload, final IPayloadContext context) {
        // Do something with the data, on the main thread
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }

//        context.enqueueWork(() -> {
            if (!(player.level().getBlockEntity(payload.pos) instanceof NetlistEditorBlockEntity be)) return;
            be.setNetlist(payload.netlist);
//        });
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
