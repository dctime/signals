package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SignalResearchStationSignalOutputBlockEntity extends BlockEntity {
    public SignalResearchStationSignalOutputBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT_BLOCK_ENTITY.get(), pos, blockState);
    }

    private int outputValue = 0;

    public int getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(int outputValue) {
        this.outputValue = outputValue;
    }



    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.outputValue = tag.getInt("outputValue");

    }

    private int tempTicks = 0;
    public static void tick(Level level, BlockPos pos, BlockState state, SignalResearchStationSignalOutputBlockEntity blockEntity) {
        if (blockEntity.tempTicks < 20) {
            blockEntity.tempTicks++;
            return;
        }
        blockEntity.tempTicks = 0;
        RandomSource randomSource = level.getRandom();
        if (level.getBlockState(pos) .getBlock() instanceof SignalResearchStationSignalOutputBlock block) {
            block.detectSignalWireAndUpdate(state, level, pos, true, false, blockEntity.updateOutputValueRandom(-15, 15, blockEntity, randomSource));
        }
    }

    public int updateOutputValueRandom(int minValue, int maxValue, SignalResearchStationSignalOutputBlockEntity entity, RandomSource randomSource) {
        // Update the output value to a random value between minValue and maxValue
        entity.setOutputValue(randomSource.nextIntBetweenInclusive(minValue, maxValue));
        return entity.getOutputValue();
    }


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("outputValue", this.outputValue);
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
}
