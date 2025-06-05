package github.dctime.dctimesignals.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class SignalOperationBlockEntityRenderer implements BlockEntityRenderer<SignalOperationBlockEntity> {
    BlockEntityRendererProvider.Context context;
    public SignalOperationBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(SignalOperationBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

//        poseStack.translate(0, 1, 0);
//        ItemRenderer renderer = context.getItemRenderer();
//        BakedModel bakedModel = renderer.getModel(RegisterBlockItems.BUILD_HELPER_BLOCK_ITEM.toStack(),entity.getLevel(),null,0);
//        renderer.render(
//            RegisterBlockItems.BUILD_HELPER_BLOCK_ITEM.toStack(),
//            ItemDisplayContext.GUI,
//            false,
//            poseStack,
//            multiBufferSource,
//            LightTexture.FULL_BRIGHT,
//            OverlayTexture.NO_OVERLAY,
//            bakedModel
//            );

//        VertexConsumer vertexBuilder = multiBufferSource.getBuffer(RenderType.lines());
//        LevelRenderer.renderLineBox(
//            poseStack,
//            vertexBuilder,
//            0, 0, 0,
//            3, 3, 3,
//            1, 0, 1, 1, 1, 0, 1);

//        poseStack.scale(2, 2, 2);
//        int renderId = (int) entity.getBlockPos().asLong();
//        context.getItemRenderer().renderStatic(
//                RegisterBlockItems.BUILD_HELPER_BLOCK_ITEM.toStack(),
//                ItemDisplayContext.FIXED,
//                LightTexture.FULL_BRIGHT,
//                packedOverlay,
//                poseStack,
//                multiBufferSource,
//                entity.getLevel(),
//                renderId
//        );

        // right hand method
        // uv 1 = 16 pixels
//        poseStack.translate(0, 0, -0.01);
//        RenderType renderType = RenderType.entitySolid(ResourceLocation.fromNamespaceAndPath(DCtimeMod.MODID, "textures/block/signal_input_side.png"));
//        VertexConsumer vertexBuilder = multiBufferSource.getBuffer(renderType);
//        Matrix4f matrix4f = poseStack.last().pose();
//        vertexBuilder.addVertex(matrix4f, 0, 0, 0)
//                .setColor(255, 255, 255, 255)
//                .setUv(0, 0)
//                .setOverlay(OverlayTexture.NO_OVERLAY)
//                .setLight(LightTexture.FULL_BRIGHT)
//                .setNormal(0, 0,-1);
//        vertexBuilder.addVertex(matrix4f, 0, 1, 0)
//                .setColor(255, 255, 255, 255)
//                .setUv(1, 0)
//                .setOverlay(OverlayTexture.NO_OVERLAY)
//                .setLight(LightTexture.FULL_BRIGHT)
//                .setNormal(0, 0,-1);
//
//        vertexBuilder.addVertex(matrix4f, 1, 1, 0)
//                .setColor(255, 255, 255, 255)
//                .setUv(1, 1)
//                .setOverlay(OverlayTexture.NO_OVERLAY)
//                .setLight(packedLight)
//                .setNormal(0, 0,-1);
//        vertexBuilder.addVertex(matrix4f, 1, 0, 0)
//                .setColor(255, 255, 255, 255)
//                .setUv(0, 1)
//                .setOverlay(OverlayTexture.NO_OVERLAY)
//                .setLight(packedLight)
//                .setNormal(0, 0,-1);


        poseStack.popPose();
    }
}
