package github.dctime.dctimesignals.item;

import github.dctime.dctimesignals.RegisterDataComponents;
import github.dctime.dctimesignals.RegisterSoundEvents;
import github.dctime.dctimesignals.block.GroundPenetratingSignalEmitterBlockEntity;
import github.dctime.dctimesignals.data_component.SignalPickaxeDataComponent;
import github.dctime.dctimesignals.data_component.SignalPickaxeHudDataComponent;
import github.dctime.dctimesignals.payload.NearestOreLocationPayload;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class SignalPickaxe extends PickaxeItem {

    public SignalPickaxe(Item.Properties properties) {
        super(Tiers.DIAMOND, properties);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void onCraftedBy(net.minecraft.world.item.ItemStack itemStack, Level level, Player player) {
        super.onCraftedBy(itemStack, level, player);
    }

    private ItemStack lastSendItemStack = ItemStack.EMPTY;
    @Override
    @ParametersAreNonnullByDefault
    public void inventoryTick(net.minecraft.world.item.ItemStack itemStack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean isSelected) {
        super.inventoryTick(itemStack, level, entity, slot, isSelected);
        if (level.isClientSide() || !(entity instanceof ServerPlayer player)) return;

        if (isSelected && !itemStack.equals(lastSendItemStack)) {
            SignalPickaxeHudDataComponent component = itemStack.get(RegisterDataComponents.SIGNAL_PICKAXE_HUD_DATA_COMPONENT);
            sendDataToHud(component.found(),
                    component.oreName(),
                    component.x(),
                    component.y(),
                    component.z(),
                    component.maxDistance(),
                    itemStack,
                    player
            );
            lastSendItemStack = itemStack;
        }
    }

    private void sendDataToHud(boolean found, String oreName, int x, int y,
                               int z, int maxDistance, ItemStack pickaxeStack, ServerPlayer serverPlayer) {
        pickaxeStack.set(RegisterDataComponents.SIGNAL_PICKAXE_HUD_DATA_COMPONENT, new SignalPickaxeHudDataComponent(found, oreName, x, y, z, maxDistance));
        PacketDistributor.sendToPlayer(serverPlayer, new NearestOreLocationPayload(found, oreName, x, y, z, maxDistance));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        SignalPickaxeDataComponent dataComponent = stack.get(RegisterDataComponents.SIGNAL_PICKAXE_DATA_COMPONENT);
        if (dataComponent == null) return;
        tooltipComponents.add(Component.empty()
                .append(Component.literal("Mode: ").withStyle(ChatFormatting.GRAY))
                .append(Component.literal((dataComponent.mode().intValue() == SignalPickaxeDataComponent.ACTIVE_MODE.intValue()) ? "Active Mode" : "Passive Mode").withStyle(ChatFormatting.GOLD)));

        tooltipComponents.add(
                Component.empty()
                        .append(Component.literal("Emitter Bound: ").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal(dataComponent.hasGroundEmitter() ? "Yes" : "No").withStyle(ChatFormatting.GOLD))
                        .append(Component.literal((dataComponent.hasGroundEmitter() ? " at: " +  dataComponent.groundPenSignalEmitterPosition() : "")).withStyle(ChatFormatting.GRAY)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        SignalPickaxeDataComponent dataComponent = itemstack.get(RegisterDataComponents.SIGNAL_PICKAXE_DATA_COMPONENT);
        if (dataComponent == null) return InteractionResultHolder.fail(itemstack);
        if (player.isCrouching()) return switchMode(level, player, usedHand, itemstack);
        if (dataComponent.mode().intValue() == SignalPickaxeDataComponent.ACTIVE_MODE.intValue()) {
            int maxDistance = 5;
            if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
                BlockPos orePosition = getNearestOrePosition(maxDistance, level, serverPlayer);

                if (orePosition == null) {
                    sendDataToHud(false, "null", 0, 0, 0, 0, itemstack, serverPlayer);
                    level.playSound(null, player.blockPosition(), RegisterSoundEvents.PICKAXE_ERROR_SOUND.get(), SoundSource.PLAYERS);
                    player.displayClientMessage(Component.literal("No ores found within " + maxDistance + " blocks."), false);
                    player.getCooldowns().addCooldown(this, 20);
                    return InteractionResultHolder.success(itemstack);
                }

                player.displayClientMessage(Component.literal("Nearest Ore Found! Update HUD."), false);
                level.playSound(null, player.blockPosition(), RegisterSoundEvents.PICKAXE_UPDATE_HUD_SOUND.get(), SoundSource.PLAYERS);
                sendDataToHud(true, level.getBlockState(orePosition).getBlock().getDescriptionId(), orePosition.getX(), orePosition.getY(), orePosition.getZ(), (int)Math.ceil(Math.pow((Math.pow(maxDistance, 2) + Math.pow(maxDistance, 2)), 1.0/2)), itemstack, serverPlayer);
            }
            player.getCooldowns().addCooldown(this, 20);
            return InteractionResultHolder.success(itemstack);
        } else if (dataComponent.mode().intValue() == SignalPickaxeDataComponent.PASSIVE_MODE.intValue()) {
            return useInPassiveMode(level, player, usedHand, itemstack, dataComponent);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    private InteractionResultHolder<ItemStack> useInPassiveMode(Level level, Player player, InteractionHand usedHand, ItemStack playerMainHandItemStack, SignalPickaxeDataComponent component) {
        if (level.isClientSide()) return InteractionResultHolder.fail(playerMainHandItemStack);
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResultHolder.fail(playerMainHandItemStack);
        BlockPos groundPenSignalEmitterPosition = component.groundPenSignalEmitterPosition();
        // get groundpenemiiter
        if (!component.hasGroundEmitter()) {
            level.playSound(null, player.blockPosition(), RegisterSoundEvents.PICKAXE_ERROR_SOUND.get(), SoundSource.PLAYERS);
            player.displayClientMessage(Component.literal("Ground Penetrating Signal Emitter not bounded to pickaxe."), false);
            sendDataToHud(false, "null", 0, 0, 0, 0, playerMainHandItemStack, serverPlayer);
            return InteractionResultHolder.success(playerMainHandItemStack);
        }

        if (!(level.getBlockEntity(groundPenSignalEmitterPosition) instanceof GroundPenetratingSignalEmitterBlockEntity emitterEntity)) {
            SignalPickaxeDataComponent noEmitterComponent = new SignalPickaxeDataComponent(
                    false,
                    new BlockPos(0, 0, 0),
                    component.mode()
            );
            playerMainHandItemStack.set(RegisterDataComponents.SIGNAL_PICKAXE_DATA_COMPONENT, noEmitterComponent);
            player.displayClientMessage(Component.literal("Ground Penetrating Signal Emitter invalid. Maybe it is destroyed"), false);
            sendDataToHud(false, "null", 0, 0, 0, 0, playerMainHandItemStack, serverPlayer);
            level.playSound(null, player.blockPosition(), RegisterSoundEvents.PICKAXE_ERROR_SOUND.get(), SoundSource.PLAYERS);
            return InteractionResultHolder.success(playerMainHandItemStack);
        }
        // get current found ore info
        BlockPos targetPos = emitterEntity.getLastFoundBlockPos();
        if (targetPos == null) {
            sendDataToHud(false, "null", 0, 0, 0, 0, playerMainHandItemStack, serverPlayer);
            player.displayClientMessage(Component.literal("No filtered block found in this chunk or filter not found"), false);
            level.playSound(null, player.blockPosition(), RegisterSoundEvents.PICKAXE_ERROR_SOUND.get(), SoundSource.PLAYERS);
            return InteractionResultHolder.success(playerMainHandItemStack);
        }
        // if found ore is not null, send packet to client
        level.playSound(null, player.blockPosition(), RegisterSoundEvents.PICKAXE_UPDATE_HUD_SOUND.get(), SoundSource.PLAYERS);
        player.displayClientMessage(Component.literal("Found Block! Update HUD."), false);
        sendDataToHud(true, level.getBlockState(targetPos).getBlock().getDescriptionId(), targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1000, playerMainHandItemStack, serverPlayer);
        return InteractionResultHolder.success(playerMainHandItemStack);
    }

    private InteractionResultHolder<ItemStack> switchMode(Level level, Player player, InteractionHand usedHand, ItemStack handItemStack) {
        ItemStack itemstack = player.getItemInHand(usedHand);
        SignalPickaxeDataComponent dataComponent = itemstack.get(RegisterDataComponents.SIGNAL_PICKAXE_DATA_COMPONENT);
        if (dataComponent == null) return InteractionResultHolder.fail(itemstack);
        if (!level.isClientSide()) {
            if (player instanceof ServerPlayer serverPlayer)
                sendDataToHud(false, "null", 0, 0, 0, 0, itemstack, serverPlayer);
            String modeName = dataComponent.isPassiveMode() ? "Active Mode" : "Passive Mode";
            player.displayClientMessage(
                    Component.empty()
                            .append(Component.literal("Switch mode To: ").withStyle(ChatFormatting.GRAY))
                            .append(Component.literal(modeName)).withStyle(ChatFormatting.GOLD),
                    false
            );
            level.playSound(null, player.blockPosition(), RegisterSoundEvents.SWITCH_MODE_SOUND.get(), SoundSource.PLAYERS);

            if (dataComponent.mode().intValue() == SignalPickaxeDataComponent.ACTIVE_MODE) {
                itemstack.set(RegisterDataComponents.SIGNAL_PICKAXE_DATA_COMPONENT, new SignalPickaxeDataComponent(dataComponent.hasGroundEmitter(), dataComponent.groundPenSignalEmitterPosition(), SignalPickaxeDataComponent.PASSIVE_MODE));
            } else {
                itemstack.set(RegisterDataComponents.SIGNAL_PICKAXE_DATA_COMPONENT, new SignalPickaxeDataComponent(dataComponent.hasGroundEmitter(), dataComponent.groundPenSignalEmitterPosition(), SignalPickaxeDataComponent.ACTIVE_MODE));
            }
        }

        return InteractionResultHolder.success(handItemStack);
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

}
