package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ConstSignalBlockEntity extends BlockEntity {
    public static final int DATA_SIZE = 1;
    public static final int OUTPUT_SIGNAL_VALUE_INDEX = 0;
    public final SimpleContainerData data = new SimpleContainerData(DATA_SIZE);


    public ConstSignalBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.CONST_SIGNAL_BLOCK_ENTITY.get(), pos, blockState);
        data.set(OUTPUT_SIGNAL_VALUE_INDEX, 30);
    }

    public int getOutputSignalValue() {
        return this.data.get(OUTPUT_SIGNAL_VALUE_INDEX);
    }

    public BlockPos getPos() {
        return worldPosition;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.data.set(OUTPUT_SIGNAL_VALUE_INDEX, tag.getInt("outputSignalValue"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("outputSignalValue", this.data.get(OUTPUT_SIGNAL_VALUE_INDEX));

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

}
