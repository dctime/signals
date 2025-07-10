package github.dctime.dctimesignals.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.Set;

public class SignalResearchStationBlockEntityRenderer implements BlockEntityRenderer<SignalResearchStationBlockEntity> {
    BlockEntityRendererProvider.Context context;


    public SignalResearchStationBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public AABB getRenderBoundingBox(SignalResearchStationBlockEntity blockEntity) {
        return AABB.INFINITE;
    }

    @Override
    public void render(SignalResearchStationBlockEntity signalResearchStationBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        if (!signalResearchStationBlockEntity.checkIfInDebugOutline()) return;
        int showIndex = signalResearchStationBlockEntity.getInputOutputShowTimeIndex();
        VertexConsumer vertexBuilder = multiBufferSource.getBuffer(RenderType.lines());
        List<BlockPos> signalInputPositions = signalResearchStationBlockEntity.getSignalInputPositions();
        List<BlockPos> signalOutputPositions = signalResearchStationBlockEntity.getSignalOutputPositions();

        if (signalInputPositions.size() > showIndex) {
            BlockPos pos = signalInputPositions.get(showIndex);
            double relX = pos.getX() - signalResearchStationBlockEntity.getBlockPos().getX();
            double relY = pos.getY() - signalResearchStationBlockEntity.getBlockPos().getY();
            double relZ = pos.getZ() - signalResearchStationBlockEntity.getBlockPos().getZ();
            renderBox(relX, relY, relZ, 0, 0, 255, 255, poseStack, vertexBuilder);
        }

        if (signalOutputPositions.size() > showIndex) {
            BlockPos pos = signalOutputPositions.get(showIndex);
            double relX = pos.getX() - signalResearchStationBlockEntity.getBlockPos().getX();
            double relY = pos.getY() - signalResearchStationBlockEntity.getBlockPos().getY();
            double relZ = pos.getZ() - signalResearchStationBlockEntity.getBlockPos().getZ();
            renderBox(relX, relY, relZ, 0, 255, 0, 255, poseStack, vertexBuilder);
        }



//        int inputIndex = 0;
//        for (BlockPos pos : signalInputPositions) {
//            double relX = pos.getX() - signalResearchStationBlockEntity.getBlockPos().getX();
//            double relY = pos.getY() - signalResearchStationBlockEntity.getBlockPos().getY();
//            double relZ = pos.getZ() - signalResearchStationBlockEntity.getBlockPos().getZ();
//            renderBox(relX, relY, relZ, 0, 0, 255/SignalResearchStationBlockEntity.DATA_SIZE_INPUT_SIGNAL/2*(SignalResearchStationBlockEntity.DATA_SIZE_INPUT_SIGNAL*2-inputIndex), 255/SignalResearchStationBlockEntity.DATA_SIZE_INPUT_SIGNAL*(SignalResearchStationBlockEntity.DATA_SIZE_INPUT_SIGNAL-inputIndex), poseStack, vertexBuilder);
//            inputIndex++;
//        }
//
//        int outputIndex = 0;
//        for (BlockPos pos : signalOutputPositions) {
//            double relX = pos.getX() - signalResearchStationBlockEntity.getBlockPos().getX();
//            double relY = pos.getY() - signalResearchStationBlockEntity.getBlockPos().getY();
//            double relZ = pos.getZ() - signalResearchStationBlockEntity.getBlockPos().getZ();
//            renderBox(relX, relY, relZ, 0, 255/SignalResearchStationBlockEntity.DATA_SIZE_OUTPUT_SIGNAL/2*(SignalResearchStationBlockEntity.DATA_SIZE_OUTPUT_SIGNAL*2-outputIndex), 0, 255/SignalResearchStationBlockEntity.DATA_SIZE_OUTPUT_SIGNAL*(SignalResearchStationBlockEntity.DATA_SIZE_OUTPUT_SIGNAL-outputIndex), poseStack, vertexBuilder);
//            outputIndex++;
//        }
//
        if (signalResearchStationBlockEntity.getItemChamberPosition() != null) {
            BlockPos itemChamberPos = signalResearchStationBlockEntity.getItemChamberPosition();
            double relX = itemChamberPos.getX() - signalResearchStationBlockEntity.getBlockPos().getX();
            double relY = itemChamberPos.getY() - signalResearchStationBlockEntity.getBlockPos().getY();
            double relZ = itemChamberPos.getZ() - signalResearchStationBlockEntity.getBlockPos().getZ();
            renderBox(relX, relY, relZ, 255, 255, 255, 255, poseStack, vertexBuilder);
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
