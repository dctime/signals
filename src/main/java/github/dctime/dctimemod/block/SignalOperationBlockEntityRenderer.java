package github.dctime.dctimemod.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import github.dctime.dctimemod.RegisterBlockItems;
import github.dctime.dctimemod.RegisterEntityRenderers;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.data.ModelData;

public class SignalOperationBlockEntityRenderer implements BlockEntityRenderer<SignalOperationBlockEntity> {
    BlockEntityRendererProvider.Context context;
    public SignalOperationBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(SignalOperationBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0, 1, 0);
        ItemRenderer renderer = context.getItemRenderer();
        BakedModel bakedModel = renderer.getModel(RegisterBlockItems.BUILD_HELPER_BLOCK_ITEM.toStack(),entity.getLevel(),null,0);
        renderer.render(
            RegisterBlockItems.BUILD_HELPER_BLOCK_ITEM.toStack(),
            ItemDisplayContext.GUI,
            false,
            poseStack,
            multiBufferSource,
            LightTexture.FULL_BRIGHT,
            OverlayTexture.NO_OVERLAY,
            bakedModel
            );

        poseStack.popPose();
    }
}
