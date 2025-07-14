package github.dctime.dctimesignals.events;

import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.RegisterItems;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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

@EventBusSubscriber(modid = DCtimeMod.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class SignalPickaxeRenderGuiLayerEvent {

    private static BlockPos orePosition;
    private static String oreName;
    private static int maxRange;

    public static void updateOrePosition(BlockPos position, String oreName, int maxRange) {
        orePosition = position;
        SignalPickaxeRenderGuiLayerEvent.oreName = oreName;
        SignalPickaxeRenderGuiLayerEvent.maxRange = maxRange;
    }

    public static void setNotFound() {
        orePosition = null;
    }

    private static double getFov(GameRenderer renderer, Camera activeRenderInfo, float partialTicks, boolean useFOVSetting) {
        if (renderer.isPanoramicMode()) {
            return (double) 90.0F;
        } else {
            double d0 = (double) 70.0F;
            if (useFOVSetting) {
                d0 = (double) (Integer) Minecraft.getInstance().options.fov().get();
//                d0 *= (double) Mth.lerp(partialTicks, this.oldFov, this.fov);
            }

            if (activeRenderInfo.getEntity() instanceof LivingEntity && ((LivingEntity) activeRenderInfo.getEntity()).isDeadOrDying()) {
                float f = Math.min((float) ((LivingEntity) activeRenderInfo.getEntity()).deathTime + partialTicks, 20.0F);
                d0 /= (double) ((1.0F - 500.0F / (f + 500.0F)) * 2.0F + 1.0F);
            }

            FogType fogtype = activeRenderInfo.getFluidInCamera();
            if (fogtype == FogType.LAVA || fogtype == FogType.WATER) {
                d0 *= Mth.lerp((Double) Minecraft.getInstance().options.fovEffectScale().get(), (double) 1.0F, (double) 0.85714287F);
            }

            return ClientHooks.getFieldOfView(renderer, activeRenderInfo, (double) partialTicks, d0, useFOVSetting);
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
        Vector4f pos = new Vector4f((float) relativePos.x, (float) relativePos.y, (float) relativePos.z, 1.0F);

        // Apply view transform
        pos.mul(viewMatrix);

        // Get view space coordinates
        float viewX = pos.x();
        float viewY = pos.y();
        float viewZ = pos.z();

        // If behind camera (z > 0), project to screen edge
        if (viewZ > 0) {
            return new Vec2(-1, -1);
        }

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
        screenX = Mth.clamp(screenX, 0, screenWidth - 20);
        screenY = Mth.clamp(screenY, 0, screenHeight - 10);

        return new Vec2(screenX, screenY);
    }

    @SubscribeEvent
    public static void render(RenderGuiLayerEvent.Pre event) {

        if (Minecraft.getInstance().player == null) return;
        if (Minecraft.getInstance().player.isHolding(RegisterItems.SIGNAL_PICKAXE.get()))
            showSignalPickaxeScreen(event);

    }

    private static void showSignalPickaxeScreen(RenderGuiLayerEvent.Pre event) {
        GuiGraphics graphics = event.getGuiGraphics();

        GameRenderer renderer = Minecraft.getInstance().gameRenderer;
        Camera camera = renderer.getMainCamera();

        Quaternionf quaternionf = camera.rotation().conjugate(new Quaternionf());
        Matrix4f frustumMatrix = new Matrix4f().rotation(quaternionf);

        DeltaTracker timer = Minecraft.getInstance().getTimer();
        float f = timer.getGameTimeDeltaPartialTick(true);
        double d0 = getFov(renderer, camera, f, true);
        Matrix4f projectionMatrix = renderer.getProjectionMatrix(Math.max(d0, (double) Minecraft.getInstance().options.fov().get().intValue()));

//            System.out.println("Fov: " + Math.max(d0, (double)Minecraft.getInstance().options.fov().get().intValue()));
//            System.out.println("Camera Position: " + camera.getPosition());
//        System.out.println("Screen Size Y: " + Minecraft.getInstance().getWindow().getGuiScaledWidth() + ", x: " + Minecraft.getInstance().getWindow().getGuiScaledHeight());
        LocalPlayer player = Minecraft.getInstance().player;
        int textWidth = Minecraft.getInstance().font.width("Signal Undetected");
        int guiWidth = (int) Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int guiHeight = (int) Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int backgroundBoarder = 5;
        int textHeight = Minecraft.getInstance().font.lineHeight;
        graphics.fill(guiWidth / 2 - textWidth / 2 - backgroundBoarder, guiHeight / 5 - textHeight / 2 - backgroundBoarder, guiWidth / 2 + textWidth / 2 + backgroundBoarder, guiHeight / 5 + textHeight / 2 + backgroundBoarder, 0x03000000);

        if (player == null) return;

        if (orePosition != null) {
            double distance = Mth.sqrt((float) player.distanceToSqr(orePosition.getX() + 0.5, orePosition.getY() + 0.5, orePosition.getZ() + 0.5));
            Vec2 resultVec2 = blockToScreenCoord(orePosition, frustumMatrix, projectionMatrix, camera, Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight());
            if (resultVec2 != null && distance <= maxRange) {

                graphics.drawString(Minecraft.getInstance().font, "Signal Detected", (int) Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - Minecraft.getInstance().font.width("Signal Detected") / 2, (int) Minecraft.getInstance().getWindow().getGuiScaledHeight() / 5 - textHeight / 2, 0xFFFF0000);
                if (resultVec2.x < 0 || resultVec2.y < 0) {
                    // behind the camera
                    graphics.drawString(Minecraft.getInstance().font, "⟳", (int) Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2, (int) Minecraft.getInstance().getWindow().getGuiScaledHeight() / 10, 0xFFFF0000);
                } else {
                    int iconWidth = Minecraft.getInstance().font.width("●");
                    int iconHeight = Minecraft.getInstance().font.lineHeight;

                    String blockName = Component.translatable(oreName).getString();
                    String extraInfoText = "Distance: " + String.format("%.2f", distance) + "m, Block: " + blockName;

                    int distanceX = (int) resultVec2.x;
                    int distanceY = (int) resultVec2.y + Minecraft.getInstance().getWindow().getGuiScaledHeight() / 20;
                    int minDistanceX = 0;
                    int minDistanceY = 0;
                    int maxDistanceX = Minecraft.getInstance().getWindow().getGuiScaledWidth() - Minecraft.getInstance().font.width(extraInfoText);
                    int maxDistanceY = Minecraft.getInstance().getWindow().getGuiScaledHeight() - textHeight;
                    graphics.drawString(Minecraft.getInstance().font, extraInfoText, Mth.clamp(distanceX, minDistanceX + iconWidth, maxDistanceX - iconWidth), Mth.clamp(distanceY, minDistanceY + iconHeight, maxDistanceY - iconHeight), 0xFFFF0000);


                    graphics.drawString(Minecraft.getInstance().font, "●", (int) resultVec2.x, (int) resultVec2.y, 0xFFFF0000);
                }

                return;
            }
        }

        graphics.drawString(Minecraft.getInstance().font, "Signal Undetected", (int) Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 - Minecraft.getInstance().font.width("Signal Undetected") / 2, (int) Minecraft.getInstance().getWindow().getGuiScaledHeight() / 5 - textHeight / 2, 0xFFFF0000);

    }
}
