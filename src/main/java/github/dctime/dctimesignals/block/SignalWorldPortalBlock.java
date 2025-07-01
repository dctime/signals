package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.DCtimeLevel;
import github.dctime.dctimesignals.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SignalWorldPortalBlock extends Block implements Portal {
    public SignalWorldPortalBlock(Properties properties) {
        super(properties);
    }



    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.isCrouching())
            entity.setAsInsidePortal(this, pos);
    }

    @Override
    public @Nullable DimensionTransition getPortalDestination(ServerLevel serverLevel, Entity entity, BlockPos blockPos) {
        ResourceKey<Level> targetLevelResource;
        if (serverLevel.dimension() == Level.OVERWORLD) {
            targetLevelResource = DCtimeLevel.SIGNAL_WORLD;
        } else if (serverLevel.dimension() == DCtimeLevel.SIGNAL_WORLD) {
            targetLevelResource = Level.OVERWORLD;
        } else {
            targetLevelResource = Level.OVERWORLD;
        }

        ServerLevel targetLevel = serverLevel.getServer().getLevel(targetLevelResource);
        if (targetLevel == null) {
            return null;
        }

        if (targetLevelResource == DCtimeLevel.SIGNAL_WORLD) {
            boolean portalNearby = false;
            BlockPos portalPos = null;
            for (int x = -5; x <= 5; x++) {
                for (int z = -5; z <= 5; z++) {
                    for (int y = -5; y <= 6; y++) {
                       if (targetLevel.getBlockState(blockPos.offset(x, y, z)).is(RegisterBlocks.SINGAL_WORLD_PORTAL.get())) {
                            portalNearby = true;
                            portalPos = blockPos.offset(x, y, z);
                            break;
                        }
                    }
                }
            }
            if (portalNearby) return new DimensionTransition(targetLevel, portalPos.getBottomCenter(), new Vec3(0,0, 0), 0, 0, DimensionTransition.PLACE_PORTAL_TICKET);
            for (int x = -2; x <= 2; x++) {
                for (int y = -1; y <= 4; y++) {
                    for (int z = -2; z <= 2; z++) {
                        if (x == -2 || x == 2 || y == -1 || y == 4 || z == -2 || z == 2) {
                            BlockPos targetPos = blockPos.offset(x, y, z);
                            targetLevel.setBlockAndUpdate(targetPos, Blocks.STONE.defaultBlockState());
                        } else {
                            BlockPos targetPos = blockPos.offset(x, y, z);
                            targetLevel.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                        }
                    }
                }
            }

            targetLevel.setBlockAndUpdate(blockPos, RegisterBlocks.SINGAL_WORLD_PORTAL.get().defaultBlockState());

        } else if (targetLevelResource == Level.OVERWORLD) {
            if (entity instanceof ServerPlayer serverplayer) {
//                return serverplayer.findRespawnPositionAndUseSpawnBlock(false, DimensionTransition.DO_NOTHING);
            }
        }

        return new DimensionTransition(targetLevel, blockPos.getBottomCenter(), new Vec3(0,0, 0), 0, 0, DimensionTransition.PLACE_PORTAL_TICKET);
    }
}
