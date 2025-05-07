package github.dctime.dctimemod.block;

import github.dctime.dctimemod.RegisterBlockEntities;
import github.dctime.dctimemod.RegisterCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SignalOperationBlockEntity extends BlockEntity {

    private int outputValue;


    public void setOutputValue(int value) { outputValue = value; }
    public int getOutputValue() { return outputValue; }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        tag.putInt("outputValue", outputValue);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        outputValue = tag.getInt("outputValue");
    }

    // The signature of this method matches the signature of the BlockEntityTicker functional interface.
    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (level.isClientSide()) return;

        SignalOperationBlockEntity operationEntity = (SignalOperationBlockEntity) blockEntity;

        Direction inputDirection = SignalOperationBlock.getInputDirection(pos, level);
        Direction input2Direction = SignalOperationBlock.getInput2Direction(pos, level);
        Direction outputDirection = SignalOperationBlock.getOutputDirection(pos, level);

        if (inputDirection == null || input2Direction == null || outputDirection == null) return;

        BlockPos inputPos = pos.relative(inputDirection);
        BlockPos input2Pos = pos.relative(input2Direction);

        SignalWireInformation inputInfo = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, inputPos, inputDirection);
        SignalWireInformation input2Info = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, input2Pos, input2Direction);

        int inputValue;
        int input2Value;

        if (inputInfo == null) inputValue = 0;
        else inputValue = inputInfo.getSignalValue();

        if (input2Info == null) input2Value = 0;
        else input2Value = input2Info.getSignalValue();

        int outputValue = inputValue + input2Value;
        if (outputValue != operationEntity.getOutputValue()) {
            operationEntity.setOutputValue(outputValue);
            SignalOperationBlock.detectSignalWireAndUpdate(state, level, pos, true, outputValue, outputDirection);
        }
    }

    public SignalOperationBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.SIGNAL_OPERATION_BLOCK_ENTITY.get(), pos, blockState);
    }





    
}
