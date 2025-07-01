package github.dctime.dctimesignals.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;

import java.util.Set;

public class SignalResearchStationBlockEntityRenderer implements BlockEntityRenderer<SignalResearchStationBlockEntity> {
    BlockEntityRendererProvider.Context context;
    public SignalResearchStationBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(SignalResearchStationBlockEntity signalResearchStationBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        VertexConsumer vertexBuilder = multiBufferSource.getBuffer(RenderType.lines());
        Set<BlockPos> signalInputPositions = signalResearchStationBlockEntity.getSignalInputPositions();
        Set<BlockPos> signalOutputPositions = signalResearchStationBlockEntity.getSignalOutputPositions();
        renderBox(0, 0, 0, 255 ,0, 0, 255, poseStack, vertexBuilder);

        for (BlockPos pos : signalInputPositions) {
            double relX = pos.getX() - signalResearchStationBlockEntity.getBlockPos().getX();
            double relY = pos.getY() - signalResearchStationBlockEntity.getBlockPos().getY();
            double relZ = pos.getZ() - signalResearchStationBlockEntity.getBlockPos().getZ();
            renderBox(relX, relY, relZ, 255, 0, 0, 255, poseStack, vertexBuilder);
        }

        for (BlockPos pos : signalOutputPositions) {
            double relX = pos.getX() - signalResearchStationBlockEntity.getBlockPos().getX();
            double relY = pos.getY() - signalResearchStationBlockEntity.getBlockPos().getY();
            double relZ = pos.getZ() - signalResearchStationBlockEntity.getBlockPos().getZ();
            renderBox(relX, relY, relZ, 0, 255, 0, 255, poseStack, vertexBuilder);
        }
    }

    private void renderBox(double relX, double relY, double relZ, int r, int g, int b, int a, PoseStack poseStack, VertexConsumer vertexBuilder) {
        poseStack.pushPose();
        poseStack.translate(relX, relY, relZ);
        LevelRenderer.renderLineBox(
                poseStack,
                vertexBuilder,
                0, 0, 0,
                1, 1, 1,
                r / 255f, g / 255f, b / 255f, a / 255f);
        poseStack.popPose();
    }
}
