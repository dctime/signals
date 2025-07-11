package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import github.dctime.dctimesignals.lib.StringToSignalOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
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
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SignalResearchStationBlockEntity extends BlockEntity {
    /*
        the main station of the multiblock
        every part of the multiblock should store this block
        if there is a part got updated/removed/neighbor changed, tell this block to reassemble the multiblock
        this block will also control all the parts of the multiblock.
        the main control block of the multiblock
    */

    private final int DEBUG_OUTLINE_SHOW_TICKS = 100;
    private int debugOutlineTicks = 0;
    public void showDebugOutline() {
        debugOutlineTicks = DEBUG_OUTLINE_SHOW_TICKS;
    }

    public void decreaseDebugOutlineTicks() {
        if (debugOutlineTicks > 0) {
            debugOutlineTicks--;
        }
    }

    public boolean checkIfInDebugOutline() {
        if (debugOutlineTicks <= 0) return false;
        return true;
    }

    // return 1-3 0 if there is error
    public int getInputOutputShowTimeIndex() {
        if (DEBUG_OUTLINE_SHOW_TICKS * 1.0/DATA_SIZE_INPUT_SIGNAL > debugOutlineTicks) {
            return 2;
        } else if (DEBUG_OUTLINE_SHOW_TICKS * 2.0/DATA_SIZE_INPUT_SIGNAL > debugOutlineTicks) {
            return 1;
        } else if (DEBUG_OUTLINE_SHOW_TICKS * 3.0/DATA_SIZE_INPUT_SIGNAL > debugOutlineTicks) {
            return 0;
        } else {
            return 0;
        }
    }

    private final SimpleContainerData inputSignalData = new SimpleContainerData(DATA_SIZE_INPUT_SIGNAL);
    private final SimpleContainerData outputSignalData = new SimpleContainerData(DATA_SIZE_OUTPUT_SIGNAL);
    private final SimpleContainerData requiredInputSignalData = new SimpleContainerData(DATA_SIZE_REQUIRED_INPUT_SIGNAL);
    private final SimpleContainerData flagsData = new SimpleContainerData(DATA_SIZE_FLAGS);

    public SimpleContainerData getInputSignalData() {
        return inputSignalData;
    }

    public SimpleContainerData getOutputSignalData() {
        return outputSignalData;
    }

    public SimpleContainerData getRequiredInputSignalData() {
        return requiredInputSignalData;
    }

    public SimpleContainerData getFlagsData() {
        return flagsData;
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

    public void setRequiredInputSignalData(int index, int value) {
        if (index >= 0 && index < DATA_SIZE_REQUIRED_INPUT_SIGNAL) {
            requiredInputSignalData.set(index, value);
        } else {
            System.out.println("Invalid index for required input signal data: " + index + ". Must be between 0 and " + (DATA_SIZE_REQUIRED_INPUT_SIGNAL - 1));
        }
    }

    public void setFlagsData(int index, boolean value) {
        if (index >= 0 && index < DATA_SIZE_FLAGS) {
            flagsData.set(index, value ? 1 : 0);
        } else {
            System.out.println("Invalid index for flags data: " + index + ". Must be between 0 and " + (DATA_SIZE_FLAGS - 1));
        }
    }

    public static final int DATA_SIZE_INPUT_SIGNAL = 3;
    public static final int DATA_SIZE_OUTPUT_SIGNAL = 3;
    public static final int DATA_SIZE_REQUIRED_INPUT_SIGNAL = 3;
    public static final int DATA_SIZE_FLAGS = 1;

    public static final int DATA_FLAGS_MULTIBLOCK_INVALID_INDEX = 0;


    private List<BlockPos> signalInputPositions;
    private List<BlockPos> signalOutputPositions;
    private BlockPos itemChamberPosition;

    public List<BlockPos> getSignalInputPositions() {
        return signalInputPositions;
    }

    public List<BlockPos> getSignalOutputPositions() {
        return signalOutputPositions;
    }

    public @Nullable BlockPos getItemChamberPosition() { return itemChamberPosition; }

    boolean multipleSignalResearchStations = false;

    public SignalResearchStationBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.SIGNAL_RESEARCH_STATION_BLOCK_ENTITY.get(), pos, blockState);
        signalInputPositions = new ArrayList<>();
        signalOutputPositions = new ArrayList<>();
        setFlagsData(DATA_FLAGS_MULTIBLOCK_INVALID_INDEX, true);
    }

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        for (int i = 0; i < DATA_SIZE_INPUT_SIGNAL; i++) {
            inputSignalData.set(i, tag.getInt("inputSignal" + i));
        }
        for (int i = 0; i < DATA_SIZE_OUTPUT_SIGNAL; i++) {
            outputSignalData.set(i, tag.getInt("outputSignal" + i));
        }
        for (int i = 0; i < DATA_SIZE_REQUIRED_INPUT_SIGNAL; i++) {
            requiredInputSignalData.set(i, tag.getInt("requiredInputSignal" + i));
        }

        if (tag.contains("itemChamberPosition"))
            itemChamberPosition = BlockPos.CODEC.parse(NbtOps.INSTANCE, tag.get("itemChamberPosition")).getOrThrow();

        int inputCount = tag.getInt("signalInputCount");
        for (int i = 0; i < inputCount; i++) {
            BlockPos inputPos = BlockPos.CODEC.parse(NbtOps.INSTANCE, tag.get("signalInputPosition" + i)).getOrThrow();
            signalInputPositions.add(inputPos);
        }

        int outputCount = tag.getInt("signalOutputCount");
        for (int i = 0; i < outputCount; i++) {
            BlockPos outputPos = BlockPos.CODEC.parse(NbtOps.INSTANCE, tag.get("signalOutputPosition" + i)).getOrThrow();
            signalOutputPositions.add(outputPos);
        }

        debugOutlineTicks = tag.getInt("debugOutlineTicks");

        flagsData.set(DATA_FLAGS_MULTIBLOCK_INVALID_INDEX, tag.getBoolean("outputPortsInvalid") ? 1 : 0);
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        for (int i = 0; i < DATA_SIZE_INPUT_SIGNAL; i++) {
            tag.putInt("inputSignal" + i, inputSignalData.get(i));
        }
        for (int i = 0; i < DATA_SIZE_OUTPUT_SIGNAL; i++) {
            tag.putInt("outputSignal" + i, outputSignalData.get(i));
        }
        for (int i = 0; i < DATA_SIZE_REQUIRED_INPUT_SIGNAL; i++) {
            tag.putInt("requiredInputSignal" + i, requiredInputSignalData.get(i));
        }

        if (itemChamberPosition != null) {
            tag.put("itemChamberPosition", BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, itemChamberPosition).getOrThrow());
        }

        // signalInputPositions count and the values

        tag.putInt("signalInputCount", signalInputPositions.size());
        for (int i = 0; i < signalInputPositions.size(); i++) {
            tag.put("signalInputPosition" + i, BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, signalInputPositions.get(i)).getOrThrow());
        }

        tag.putInt("signalOutputCount", signalOutputPositions.size());
        for (int i = 0; i < signalOutputPositions.size(); i++) {
            tag.put("signalOutputPosition" + i, BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, signalOutputPositions.get(i)).getOrThrow());
        }

        tag.putInt("debugOutlineTicks", debugOutlineTicks);

        tag.putBoolean("outputPortsInvalid", flagsData.get(DATA_FLAGS_MULTIBLOCK_INVALID_INDEX) > 0);

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

    // a variable to check if output ports are valid so that there is no bypass of the multiblock
    public boolean areOutputPortsInvalid() {
        return flagsData.get(DATA_FLAGS_MULTIBLOCK_INVALID_INDEX) > 0;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SignalResearchStationBlockEntity blockEntity) {
        if (level.isClientSide()) blockEntity.decreaseDebugOutlineTicks();

        int outputPortCounter = 0;
        for (BlockPos outputPos : blockEntity.getSignalOutputPositions()) {
            if (level.getBlockEntity(outputPos) instanceof SignalResearchStationSignalOutputBlockEntity outputEntity) {
                int outputValue = outputEntity.getOutputValue();
                blockEntity.setOutputSignalData(outputPortCounter, outputValue);
                outputPortCounter++;
            } else {
                blockEntity.setFlagsData(DATA_FLAGS_MULTIBLOCK_INVALID_INDEX, true);
                return;
            }
        }

        int inputPortCounter = 0;

        for (BlockPos inputPos : blockEntity.getSignalInputPositions()) {
            if (level.getBlockEntity(inputPos) instanceof SignalResearchStationSignalInputBlockEntity inputEntity) {
                int inputValue = inputEntity.getStoredSignalValue();
                blockEntity.setInputSignalData(inputPortCounter, inputValue);
                inputPortCounter++;
            }
        }

        BlockPos itemChamberPosition = blockEntity.getItemChamberPosition();
        if (itemChamberPosition == null) {
            // System.out.println("No item chamber position set for the Signal Research Station at " + blockEntity.getBlockPos());
            return;
        }
        BlockEntity itemChamberEntity = level.getBlockEntity(itemChamberPosition);
        if (!(itemChamberEntity instanceof SignalResearchItemChamberBlockEntity signalResearchItemChamberBlockEntity)) {
            // System.out.println("Item chamber entity at " + itemChamberPosition + " is not a SignalResearchItemChamberBlockEntity");
            return;
        }

        int[] correctInputs = StringToSignalOperation.fromString(
                signalResearchItemChamberBlockEntity.getSignalRequired1(),
                signalResearchItemChamberBlockEntity.getSignalRequired2(),
                signalResearchItemChamberBlockEntity.getSignalRequired3()
        ).signalOperations(blockEntity.getOutputSignalData().get(0), blockEntity.getOutputSignalData().get(1), blockEntity.getOutputSignalData().get(2));
//        System.out.println("Correct input: " + correctInputs[0]);
//        System.out.println("Signal Required 1: " + signalResearchItemChamberBlockEntity.getSignalRequired1());

        for (int requiredInputPortCounter = 0; requiredInputPortCounter < DATA_SIZE_REQUIRED_INPUT_SIGNAL; requiredInputPortCounter++) {
                int requiredInputValue = correctInputs[requiredInputPortCounter];
                blockEntity.setRequiredInputSignalData(requiredInputPortCounter, requiredInputValue);
                requiredInputPortCounter++;
        }

        blockEntity.setFlagsData(DATA_FLAGS_MULTIBLOCK_INVALID_INDEX, false);
    }

    private void clearInputOutputAndItemChamberPositions() {
        signalOutputPositions.clear();
        signalInputPositions.clear();
        itemChamberPosition = null;
        setFlagsData(DATA_FLAGS_MULTIBLOCK_INVALID_INDEX, true);
    }

    public void reassembleMultiblock(Player player) {
        clearInputOutputAndItemChamberPositions();
        multipleSignalResearchStations = false;
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
                        clearInputOutputAndItemChamberPositions();
                        return;
                    }
                    toCheck.push(checkingPos);
                } else if (this.level.getBlockEntity(checkingPos) instanceof SignalResearchStationSignalOutputBlockEntity) {
                    signalOutputPositions.add(checkingPos);
                    checkedPositions.add(checkingPos);
                    if (signalOutputPositions.size() > DATA_SIZE_OUTPUT_SIGNAL) {
                        if (player instanceof ServerPlayer)
                            player.displayClientMessage(Component.literal("Exceeded maximum number of signal outputs for the multiblock at " + mainPos), false);
                        clearInputOutputAndItemChamberPositions();
                        return;
                    }
                    toCheck.push(checkingPos);
                } else if (this.level.getBlockEntity(checkingPos) instanceof SignalResearchStationBlockEntity) {
                    multipleSignalResearchStations = true;
                    player.displayClientMessage(Component.literal("Multiple Research Stations at " + mainPos), false);
                    checkedPositions.add(checkingPos);
                    toCheck.push(checkingPos);
                } else if (this.level.getBlockEntity(checkingPos) instanceof SignalResearchItemChamberBlockEntity) {
                    if (itemChamberPosition == null) {
                        itemChamberPosition = checkingPos;
                    } else {
                        // if there is already a item chamber position, we stop checking
                        // this is to prevent infinite loops in case of broken multiblocks
                        player.displayClientMessage(Component.literal("Multiple Item Chambers at " + mainPos), false);
                        itemChamberPosition = null;
                        clearInputOutputAndItemChamberPositions();
                        return;
                    }
                    checkedPositions.add(checkingPos);
                    toCheck.push(checkingPos);
                } else {
                    // if there is a block that is not part of the multiblock, we stop checking
                    // this is to prevent infinite loops in case of broken multiblocks
                    continue;
                }
            }
        }

        if (signalOutputPositions.size() != DATA_SIZE_OUTPUT_SIGNAL) {
            if (player instanceof ServerPlayer) {
                player.displayClientMessage(Component.literal("There should be 3 signal outputs but found " + signalOutputPositions.size()), false);
            }
            clearInputOutputAndItemChamberPositions();
            return;
        }

        if (itemChamberPosition != null) {
            assert level != null;
            BlockEntity itemChamberEntity = level.getBlockEntity(itemChamberPosition);
            if (itemChamberEntity instanceof SignalResearchItemChamberBlockEntity chamberBlockEntity) {
                chamberBlockEntity.setSignalResearchStationBlockEntityPos(this.getBlockPos());
            } else {
                itemChamberPosition = null; // reset if not a valid chamber
                clearInputOutputAndItemChamberPositions();
                return;
            }
        } else {
            assert level != null;
            if (!level.isClientSide())
                player.displayClientMessage(Component.literal("Item Chambers not found"), false);
            clearInputOutputAndItemChamberPositions();
            return;
        }

        if (!level.isClientSide())
            player.displayClientMessage(Component.literal("Multiblock reassembled"), false);
    }
}
