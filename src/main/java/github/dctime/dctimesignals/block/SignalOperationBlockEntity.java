package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import github.dctime.dctimesignals.RegisterCapabilities;
import github.dctime.dctimesignals.item.SignalOperationBaseCardItem;
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
    public static class CardItemStackHandler extends ItemStackHandler {
        public CardItemStackHandler(int size) {
            super(size);
        }

        public CardItemStackHandler(NonNullList<ItemStack> stacks) {
            super(stacks);
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        // server side check SignalOperationMenu CardSlotItemHandler for client side
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.getItem() instanceof SignalOperationBaseCardItem;
        }


    }

    public static final int DATA_SIZE = 0;
    public static final int ITEM_SIZE = 1;

    public static final int CARD_SLOT_INDEX = 0;

    private IItemHandler handler = new CardItemStackHandler(NonNullList.withSize(ITEM_SIZE, ItemStack.EMPTY));
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
        ((CardItemStackHandler) handler).deserializeNBT(registries, tag.getCompound("items"));
//        System.out.println("Output Value Changed to from saveAdditional: " + outputValue);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("outputValue", outputValue);
        tag.put("items", ((CardItemStackHandler) handler).serializeNBT(registries));
//        System.out.println("Output Value Loaded from loadAdditional: " + outputValue);
        setChanged();


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

        Integer inputValue;
        Integer input2Value;

        // get inputValue

        Direction inputDirection = SignalOperationBlock.getInputDirection(state);
        if (inputDirection == null) inputValue = null;
        else {
            BlockPos inputPos = pos.relative(inputDirection);
            SignalWireInformation inputInfo = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, inputPos, inputDirection);
            if (inputInfo == null) inputValue = null;
            else inputValue = inputInfo.getSignalValue();
        }

        Direction input2Direction = SignalOperationBlock.getInput2Direction(state);
        if (input2Direction == null) input2Value = null;
        else {
            BlockPos input2Pos = pos.relative(input2Direction);
            SignalWireInformation input2Info = level.getCapability(RegisterCapabilities.SIGNAL_VALUE, input2Pos, input2Direction);
            if (input2Info == null) input2Value = null;
            else input2Value = input2Info.getSignalValue();
        }

        Direction outputDirection = SignalOperationBlock.getOutputDirection(state);
        if (outputDirection == null) return;

        if (operationEntity.getItems().getStackInSlot(CARD_SLOT_INDEX).isEmpty()) return;
        if (!(operationEntity.getItems().getStackInSlot(CARD_SLOT_INDEX).getItem() instanceof SignalOperationBaseCardItem cardItem)) return;
        if (!block.checkIfSideModesValid(state, cardItem)) return;

//        System.out.println("Input Value: " + inputValue + ", " + input2Value);
        // input and input2 might be null
        Integer outputValue = cardItem.operation(inputValue, input2Value);
        if (outputValue == null) return;

        if (outputValue != operationEntity.getOutputValue()) {
            operationEntity.setOutputValue(outputValue);
            SignalOperationBlock.detectSignalWireAndUpdate(state, level, pos, true, false, outputValue, outputDirection);
        }
    }

    public SignalOperationBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.SIGNAL_OPERATION_BLOCK_ENTITY.get(), pos, blockState);
    }





    
}
