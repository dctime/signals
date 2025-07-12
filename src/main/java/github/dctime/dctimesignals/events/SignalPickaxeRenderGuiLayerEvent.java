package github.dctime.dctimesignals.events;

import github.dctime.dctimesignals.DCtimeMod;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid= DCtimeMod.MODID, bus= EventBusSubscriber.Bus.GAME, value= Dist.CLIENT)
public class SignalPickaxeRenderGuiLayerEvent {

    private static BlockPos orePosition;

    public static void updateOrePosition(BlockPos position) {
        orePosition = position;
    }

    private static double getFov(GameRenderer renderer, Camera activeRenderInfo, float partialTicks, boolean useFOVSetting) {
        if (renderer.isPanoramicMode()) {
            return (double)90.0F;
        } else {
            double d0 = (double)70.0F;
            if (useFOVSetting) {
                d0 = (double)(Integer)Minecraft.getInstance().options.fov().get();
//                d0 *= (double) Mth.lerp(partialTicks, this.oldFov, this.fov);
            }

            if (activeRenderInfo.getEntity() instanceof LivingEntity && ((LivingEntity)activeRenderInfo.getEntity()).isDeadOrDying()) {
                float f = Math.min((float)((LivingEntity)activeRenderInfo.getEntity()).deathTime + partialTicks, 20.0F);
                d0 /= (double)((1.0F - 500.0F / (f + 500.0F)) * 2.0F + 1.0F);
            }

            FogType fogtype = activeRenderInfo.getFluidInCamera();
            if (fogtype == FogType.LAVA || fogtype == FogType.WATER) {
                d0 *= Mth.lerp((Double)Minecraft.getInstance().options.fovEffectScale().get(), (double)1.0F, (double)0.85714287F);
            }

            return ClientHooks.getFieldOfView(renderer, activeRenderInfo, (double)partialTicks, d0, useFOVSetting);
        }
    }

    @Nullable
    public static Vec2 blockToScreenCoord(BlockPos blockPos, Matrix4f viewMatrix, Matrix4f projectionMatrix, Camera camera, int screenWidth, int screenHeight) {
        // Center of the block
        Vec3 worldPos = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);

        // World position relative to camera
        Vec3 cameraPos = camera.getPosition();
        Vec3 relativePos = worldPos.subtract(cameraPos);

        // Homogeneous 4D vector
        Vector4f pos = new Vector4f((float)relativePos.x, (float)relativePos.y, (float)relativePos.z, 1.0F);

        // Apply view transform
        pos.mul(viewMatrix);

        // Get view space coordinates
        float viewX = pos.x();
        float viewY = pos.y();
        float viewZ = pos.z();

        // If behind camera (z > 0), project to screen edge
        if (viewZ > 0) { return new Vec2(-1, -1); }

        // Block is in front of camera, use normal projection
        pos.mul(projectionMatrix);

        float w = pos.w();
        if (w == 0) return null;

        // Normalize (NDC)
        float ndcX = pos.x() / w;
        float ndcY = pos.y() / w;

        // Convert to screen coordinates
        float screenX = (ndcX * 0.5f + 0.5f) * screenWidth;
        float screenY = (1.0f - (ndcY * 0.5f + 0.5f)) * screenHeight;

        // Clamp to screen edges
        screenX = Mth.clamp(screenX, 0, screenWidth-20);
        screenY = Mth.clamp(screenY, 0, screenHeight-10);

        return new Vec2(screenX, screenY);
    }

    @SubscribeEvent
    public static void render(RenderGuiLayerEvent.Post event) {
        GuiGraphics graphics = event.getGuiGraphics();

        GameRenderer renderer = Minecraft.getInstance().gameRenderer;
        Camera camera = renderer.getMainCamera();

        Quaternionf quaternionf = camera.rotation().conjugate(new Quaternionf());
        Matrix4f frustumMatrix= new Matrix4f().rotation(quaternionf);

        DeltaTracker timer = Minecraft.getInstance().getTimer();
        float f = timer.getGameTimeDeltaPartialTick(true);
        double d0 = getFov(renderer, camera, f, true);
        Matrix4f projectionMatrix = renderer.getProjectionMatrix(Math.max(d0, (double)Minecraft.getInstance().options.fov().get().intValue()));

//            System.out.println("Fov: " + Math.max(d0, (double)Minecraft.getInstance().options.fov().get().intValue()));
//            System.out.println("Camera Position: " + camera.getPosition());
//        System.out.println("Screen Size Y: " + Minecraft.getInstance().getWindow().getGuiScaledWidth() + ", x: " + Minecraft.getInstance().getWindow().getGuiScaledHeight());

        if (orePosition != null) {
            Vec2 resultVec2 = blockToScreenCoord(orePosition, frustumMatrix, projectionMatrix, camera, Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight());
            if (resultVec2 != null) {
                if (resultVec2.x < 0 || resultVec2.y < 0) {
                    // behind the camera
                    graphics.drawString(Minecraft.getInstance().font, "Behind", (int)Minecraft.getInstance().getWindow().getGuiScaledWidth()/2, (int)Minecraft.getInstance().getWindow().getGuiScaledHeight()/10, 0xFFFFFFFF);
                } else {
                    graphics.drawString(Minecraft.getInstance().font, "Here", (int)resultVec2.x, (int)resultVec2.y, 0xFFFFFFFF);
                }

            }
        }


    }
}
