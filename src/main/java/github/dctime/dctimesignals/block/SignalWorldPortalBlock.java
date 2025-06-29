package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.DCtimeLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
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
        entity.setAsInsidePortal(this, pos);
    }

    @Override
    public @Nullable DimensionTransition getPortalDestination(ServerLevel serverLevel, Entity entity, BlockPos blockPos) {
        ResourceKey<Level> targetLevelResource = DCtimeLevel.SIGNAL_WORLD;
        ServerLevel targetLevel = serverLevel.getServer().getLevel(targetLevelResource);
        if (targetLevel == null) {
            return null;
        }

        for (int x = -3; x <= 3; x++) {
            for (int y = -1; y <= 4; y++) {
                for (int z = -3; z <= 3; z++) {
                    if (x == -3 || x == 3 || y == -1 || y == 4 || z == -3 || z == 3) {
                        BlockPos targetPos = blockPos.offset(x, y, z);
                        targetLevel.setBlockAndUpdate(targetPos, Blocks.STONE.defaultBlockState());
                    } else {
                        BlockPos targetPos = blockPos.offset(x, y, z);
                        targetLevel.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
                    }
                }
            }
        }

        return new DimensionTransition(targetLevel, blockPos.getBottomCenter(), new Vec3(0,0, 0), 0, 0, DimensionTransition.PLACE_PORTAL_TICKET);
    }
}
