package github.dctime.dctimesignals.item;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4f;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class SignalPickaxe extends PickaxeItem {

    public SignalPickaxe(Properties properties) {
        super(Tiers.IRON, properties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onCraftedBy(net.minecraft.world.item.ItemStack itemStack, Level level, Player player) {
        super.onCraftedBy(itemStack, level, player);
    }

    private double getFov(GameRenderer renderer, Camera activeRenderInfo, float partialTicks, boolean useFOVSetting) {
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

    @Override
    @ParametersAreNonnullByDefault
    public void inventoryTick(net.minecraft.world.item.ItemStack itemStack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean isSelected) {
        if (!level.isClientSide && entity instanceof ServerPlayer player && isSelected) {
            BlockPos playerPos = player.blockPosition();
            int radius = 8;
            int verticalRadius = 4;

            List<BlockPos> orePositions = new ArrayList<>();

            for (int x = -radius; x <= radius; x++) {
                for (int y = -verticalRadius; y <= verticalRadius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos targetPos = playerPos.offset(x, y, z);
                        if (level.getBlockState(targetPos).is(Tags.Blocks.ORES)) {
                            orePositions.add(targetPos);
                        }
                    }
                }
            }

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
            System.out.println("Screen Size Y: " + Minecraft.getInstance().getWindow().getGuiScaledWidth() + ", x: " + Minecraft.getInstance().getWindow().getGuiScaledHeight());

            if (orePositions.size() > 0) {
                Vec2 resultVec2 = blockToScreenCoord(orePositions.get(0), frustumMatrix, projectionMatrix, camera, Minecraft.getInstance().getWindow().getGuiScaledWidth(), Minecraft.getInstance().getWindow().getGuiScaledHeight());
                if (resultVec2 != null) {
                    System.out.println("Ore Position Screen: " + resultVec2.x + ", " + resultVec2.y);
                }
            }
        }

        super.inventoryTick(itemStack, level, entity, slot, isSelected);
    }

    @Nullable
    public static Vec2 blockToScreenCoord(BlockPos blockPos, Matrix4f viewMatrix, Matrix4f projectionMatrix, Camera camera, int screenWidth, int screenHeight) {
        // Center of the block
        Vec3 worldPos = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);

        // World position relative to camera (view space input)
        Vec3 cameraPos = camera.getPosition();
        Vec3 relativePos = worldPos.subtract(cameraPos);

        // Homogeneous 4D vector
        Vector4f pos = new Vector4f((float)relativePos.x, (float)relativePos.y, (float)relativePos.z, 1.0F);

        // Apply view and projection transforms
        pos.mul(viewMatrix);
        pos.mul(projectionMatrix);

        float w = pos.w();
        if (w <= 0) return null; // Behind camera

        // Normalize (NDC)
        float ndcX = pos.x() / w;
        float ndcY = pos.y() / w;

        // Convert to screen coordinates
        float screenX = (ndcX * 0.5f + 0.5f) * screenWidth;
        float screenY = (1.0f - (ndcY * 0.5f + 0.5f)) * screenHeight;

        // Optional: clamp or discard off-screen
        if (screenX < 0 || screenX > screenWidth || screenY < 0 || screenY > screenHeight) return null;

        return new Vec2(screenX, screenY);
    }
}
