package github.dctime.dctimesignals.item;

import github.dctime.dctimesignals.payload.NearestOreLocationPayload;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector4f;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class SignalPickaxe extends PickaxeItem {

    public SignalPickaxe(Properties properties) {
        super(Tiers.DIAMOND, properties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onCraftedBy(net.minecraft.world.item.ItemStack itemStack, Level level, Player player) {
        super.onCraftedBy(itemStack, level, player);
    }



    @Override
    @ParametersAreNonnullByDefault
    public void inventoryTick(net.minecraft.world.item.ItemStack itemStack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean isSelected) {


        super.inventoryTick(itemStack, level, entity, slot, isSelected);
    }

    private boolean isOreAt(Level level, BlockPos pos) {
        return level.getBlockState(pos).is(Tags.Blocks.ORES);
    }

    private BlockPos getNearestOrePosition(int max_radius, Level level, ServerPlayer player) {
        BlockPos playerPos = player.blockPosition();

        for (int radius = 1; radius <= max_radius; radius++) {
            // Check top and bottom faces (y = ±radius)
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos topPos = playerPos.offset(x, radius, z);
                    if (isOreAt(level, topPos)) return topPos;

                    BlockPos bottomPos = playerPos.offset(x, -radius, z);
                    if (isOreAt(level, bottomPos)) return bottomPos;
                }
            }

            // Check front and back faces (z = ±radius), excluding edges already checked
            for (int x = -radius; x <= radius; x++) {
                for (int y = -(radius-1); y <= (radius-1); y++) {
                    BlockPos frontPos = playerPos.offset(x, y, radius);
                    if (isOreAt(level, frontPos)) return frontPos;

                    BlockPos backPos = playerPos.offset(x, y, -radius);
                    if (isOreAt(level, backPos)) return backPos;
                }
            }

            // Check left and right faces (x = ±radius), excluding edges already checked
            for (int z = -(radius-1); z <= (radius-1); z++) {
                for (int y = -(radius-1); y <= (radius-1); y++) {
                    BlockPos rightPos = playerPos.offset(radius, y, z);
                    if (isOreAt(level, rightPos)) return rightPos;

                    BlockPos leftPos = playerPos.offset(-radius, y, z);
                    if (isOreAt(level, leftPos)) return leftPos;
                }
            }
        }

        return null;

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        int maxDistance = 8;
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            BlockPos orePosition = getNearestOrePosition(maxDistance, level, serverPlayer);
            if (orePosition == null) {
                PacketDistributor.sendToPlayer(serverPlayer, new NearestOreLocationPayload(false, "null", 0, 0, 0, 0));
                player.getCooldowns().addCooldown(this, 20);
                return InteractionResultHolder.fail(itemstack);
            }
            PacketDistributor.sendToPlayer(serverPlayer, new NearestOreLocationPayload(true, level.getBlockState(orePosition).getBlock().getDescriptionId(), orePosition.getX(), orePosition.getY(), orePosition.getZ(), maxDistance));

        }
        player.getCooldowns().addCooldown(this, 20);
        return InteractionResultHolder.success(itemstack);
    }
}
