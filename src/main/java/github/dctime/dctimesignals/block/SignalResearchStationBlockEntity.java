package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class SignalResearchStationBlockEntity extends BlockEntity {
    /*
        the main station of the multiblock
        every part of the multiblock should store this block
        if there is a part got updated/removed/neighbor changed, tell this block to reassemble the multiblock
        this block will also control all the parts of the multiblock.
        the main control block of the multiblock
    */

    private final SimpleContainerData inputSignalData = new SimpleContainerData(DATA_SIZE_INPUT_SIGNAL);
    private final SimpleContainerData outputSignalData = new SimpleContainerData(DATA_SIZE_OUTPUT_SIGNAL);

    public SimpleContainerData getInputSignalData() {
        return inputSignalData;
    }

    public SimpleContainerData getOutputSignalData() {
        return outputSignalData;
    }

    public void setInputSignalData(int index, int value) {
        if (index >= 0 && index < DATA_SIZE_INPUT_SIGNAL) {
            inputSignalData.set(index, value);
        } else {
            System.out.println("Invalid index for input signal data: " + index + ". Must be between 0 and " + (DATA_SIZE_INPUT_SIGNAL - 1));
        }
    }

    public void setOutputSignalData(int index, int value) {
        if (index >= 0 && index < DATA_SIZE_OUTPUT_SIGNAL) {
            outputSignalData.set(index, value);
        } else {
            System.out.println("Invalid index for output signal data: " + index + ". Must be between 0 and " + (DATA_SIZE_OUTPUT_SIGNAL - 1));
        }
    }

    public static final int DATA_SIZE_INPUT_SIGNAL = 3;
    public static final int DATA_SIZE_OUTPUT_SIGNAL = 3;


    private List<BlockPos> signalInputPositions;
    private List<BlockPos> signalOutputPositions;

    public List<BlockPos> getSignalInputPositions() {
        return signalInputPositions;
    }

    public List<BlockPos> getSignalOutputPositions() {
        return signalOutputPositions;
    }

    boolean multipleSignalResearchStations = false;

    public SignalResearchStationBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.SIGNAL_RESEARCH_STATION_BLOCK_ENTITY.get(), pos, blockState);
        signalInputPositions = new ArrayList<>();
        signalOutputPositions = new ArrayList<>();
    }

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        // Will default to 0 if absent. See the NBT article for more information.
//        this.value = tag.getInt("value");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
//        tag.putInt("value", this.value);
        setChanged();
    }

    // Create an update tag here. For block entities with only a few fields, this can just call #saveAdditional.
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

    public static void tick(Level level, BlockPos pos, BlockState state, SignalResearchStationBlockEntity blockEntity) {
        if (level.isClientSide()) return;

        int inputPortCounter = 0;
        for (BlockPos inputPos : blockEntity.getSignalInputPositions()) {
            if (level.getBlockEntity(inputPos) instanceof SignalResearchStationSignalInputBlockEntity inputEntity) {
                int inputValue = inputEntity.getStoredSignalValue();
                blockEntity.setInputSignalData(inputPortCounter, inputValue);
                inputPortCounter++;
            }
        }

        int outputPortCounter = 0;
        for (BlockPos outputPos : blockEntity.getSignalOutputPositions()) {
            if (level.getBlockEntity(outputPos) instanceof SignalResearchStationSignalOutputBlockEntity outputEntity) {
                int outputValue = outputEntity.getOutputValue();
                blockEntity.setOutputSignalData(outputPortCounter, outputValue);
                outputPortCounter++;
            }
        }
    }

    private void clearInputOutputPositions() {
        signalOutputPositions.clear();
        signalInputPositions.clear();
    }

    public void reassembleMultiblock(Player player) {
        clearInputOutputPositions();
        for (int i = 0; i < DATA_SIZE_INPUT_SIGNAL; i++) {
            this.setInputSignalData(i, 0);
        }

        for (int i = 0; i < DATA_SIZE_OUTPUT_SIGNAL; i++) {
            this.setOutputSignalData(i, 0);
        }

        BlockPos mainPos = this.getBlockPos();
        Set<BlockPos> checkedPositions = new HashSet<>();
        checkedPositions.add(mainPos);
        Stack<BlockPos> toCheck = new Stack<>();
        toCheck.push(mainPos);

        BlockPos currentPos;
        while (!toCheck.isEmpty()) {
            currentPos = toCheck.pop();
            for (Direction dir : Direction.values()) {
                BlockPos checkingPos = currentPos.relative(dir);
                if (checkedPositions.contains(checkingPos)) continue;
                if (this.level.getBlockEntity(checkingPos) instanceof SignalResearchStationSignalInputBlockEntity) {
                    signalInputPositions.add(checkingPos);
                    checkedPositions.add(checkingPos);
                    if (signalInputPositions.size() > DATA_SIZE_INPUT_SIGNAL) {
                        if (player instanceof ServerPlayer)
                            player.displayClientMessage(Component.literal("Exceeded maximum number of signal inputs for the multiblock at " + mainPos), false);
                        clearInputOutputPositions();
                        return;
                    }
                    toCheck.push(checkingPos);
                } else if (this.level.getBlockEntity(checkingPos) instanceof SignalResearchStationSignalOutputBlockEntity) {
                    signalOutputPositions.add(checkingPos);
                    checkedPositions.add(checkingPos);
                    if (signalOutputPositions.size() > DATA_SIZE_OUTPUT_SIGNAL) {
                        if (player instanceof ServerPlayer)
                            player.displayClientMessage(Component.literal("Exceeded maximum number of signal outputs for the multiblock at " + mainPos), false);
                        clearInputOutputPositions();
                        return;
                    }
                    toCheck.push(checkingPos);
                } else if (this.level.getBlockEntity(checkingPos) instanceof SignalResearchStationBlockEntity) {
                    multipleSignalResearchStations = true;
                    player.displayClientMessage(Component.literal("Multiple Research Stations at " + mainPos), false);
                    checkedPositions.add(checkingPos);
                    toCheck.push(checkingPos);
                } else {
                    // if there is a block that is not part of the multiblock, we stop checking
                    // this is to prevent infinite loops in case of broken multiblocks
                    continue;
                }
            }
        }

    }


    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SignalResearchItemChamberBlockEntity signalResearchItemChamberBlockEntity) {

    }
}
