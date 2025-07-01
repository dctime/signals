package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class SignalResearchStationBlockEntity extends BlockEntity {
    /*
        the main station of the multiblock
        every part of the multiblock should store this block
        if there is a part got updated/removed/neighbor changed, tell this block to reassemble the multiblock
        this block will also control all the parts of the multiblock.
        the main control block of the multiblock
    */

    private Set<BlockPos> signalInputPositions;
    private Set<BlockPos> signalOutputPositions;

    public Set<BlockPos> getSignalInputPositions() {
        return signalInputPositions;
    }

    public Set<BlockPos> getSignalOutputPositions() {
        return signalOutputPositions;
    }

    boolean multipleSignalResearchStations = false;

    public SignalResearchStationBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.SIGNAL_RESEARCH_STATION_BLOCK_ENTITY.get(), pos, blockState);
        signalInputPositions = new HashSet<>();
        signalOutputPositions = new HashSet<>();
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

    public void reassembleMultiblock() {
        signalOutputPositions.clear();
        signalInputPositions.clear();

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
                    toCheck.push(checkingPos);
                } else if (this.level.getBlockEntity(checkingPos) instanceof SignalResearchStationSignalOutputBlockEntity) {
                    signalOutputPositions.add(checkingPos);
                    checkedPositions.add(checkingPos);
                    toCheck.push(checkingPos);
                } else if (this.level.getBlockEntity(checkingPos) instanceof SignalResearchStationBlockEntity) {
                    multipleSignalResearchStations = true;
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
}
