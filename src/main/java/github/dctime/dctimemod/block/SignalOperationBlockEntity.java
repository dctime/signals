package github.dctime.dctimemod.block;

import github.dctime.dctimemod.RegisterBlockEntities;
import github.dctime.dctimemod.RegisterCapabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class SignalOperationBlockEntity extends BlockEntity {
    public static final int DATA_SIZE = 0;
    public static final int ITEM_SIZE = 1;

    private IItemHandler handler = new ItemStackHandler(NonNullList.withSize(ITEM_SIZE, ItemStack.EMPTY));
    private ContainerData data = new SimpleContainerData(DATA_SIZE);

    public IItemHandler getItems() {
        return handler;
    }

    public ContainerData getData() {
        return data;
    }


    private int outputValue = 0;


    public void setOutputValue(int value) {
        outputValue = value;
//        System.out.println("Output Value Changed to: " + outputValue);
    }
    public int getOutputValue() { return outputValue; }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        setOutputValue(tag.getInt("outputValue"));
//        System.out.println("Output Value Changed to from saveAdditional: " + outputValue);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("outputValue", outputValue);
//        System.out.println("Output Value Loaded from loadAdditional: " + outputValue);


    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    // Return our packet here. This method returning a non-null result tells the game to use this packet for syncing.
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // The signature of this method matches the signature of the BlockEntityTicker functional interface.
    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (level.isClientSide()) return;

        SignalOperationBlockEntity operationEntity = (SignalOperationBlockEntity) blockEntity;

        SignalOperationBlock block = (SignalOperationBlock) blockEntity.getBlockState().getBlock();
        if (!block.checkIfSideModesValid(state)) return;

        Direction inputDirection = SignalOperationBlock.getInputDirection(state);
        Direction input2Direction = SignalOperationBlock.getInput2Direction(state);
        Direction outputDirection = SignalOperationBlock.getOutputDirection(state);

        if (inputDirection == null || input2Direction == null || outputDirection == null) return;

        BlockPos inputPos = pos.relative(inputDirection);
        BlockPos input2Pos = pos.relative(input2Direction);

        SignalWireInformation inputInfo = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, inputPos, inputDirection);
        SignalWireInformation input2Info = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, input2Pos, input2Direction);

        int inputValue;
        int input2Value;

        if (inputInfo == null) return;
        else if (inputInfo.getSignalValue() == null) return;
        else inputValue = inputInfo.getSignalValue();

        if (input2Info == null) return;
        else if (input2Info.getSignalValue() == null) return;
        else input2Value = input2Info.getSignalValue();

//        System.out.println("Input Value: " + inputValue + ", " + input2Value);
        int outputValue = inputValue & input2Value;
        if (outputValue != operationEntity.getOutputValue()) {
            operationEntity.setOutputValue(outputValue);
            SignalOperationBlock.detectSignalWireAndUpdate(state, level, pos, true, false, outputValue, outputDirection);
        }
    }

    public SignalOperationBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.SIGNAL_OPERATION_BLOCK_ENTITY.get(), pos, blockState);
    }





    
}
