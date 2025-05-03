package github.dctime.dctimemod.block;

import github.dctime.dctimemod.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SignalOperationBlockEntity extends BlockEntity {
    public SignalOperationBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.SIGNAL_OPERATION_BLOCK_ENTITY.get(), pos, blockState);
    }

    
}
