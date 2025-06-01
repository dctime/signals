package github.dctime.dctimemod.block;

import github.dctime.dctimemod.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SignalWireBlockEntity extends BlockEntity {

    private final SignalWireInformation information = new SignalWireInformation();

    public void setNoSignal() {
        this.information.setNoSignal();
    }

    public void setSignalValue(int value) {
        this.information.setSignalValue(value);
    }

    @Nullable
    public Integer getSignalValue() {
        return this.information.getSignalValue();
    }

    public SignalWireInformation getInformation() {
        return this.information;
    }

    public SignalWireBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.SIGNAL_WIRE_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.getBoolean("noSignal")) {
            this.information.setNoSignal();
        } else {
            this.information.setSignalValue(tag.getInt("signalValue"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.information.getSignalValue() == null) {
            tag.putBoolean("noSignal", true);
            tag.putInt("signalValue", 0);
        } else {
            tag.putBoolean("noSignal", false);
            tag.putInt("signalValue", this.information.getSignalValue());
        }

        setChanged();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if (level.isClientSide()) return;
    }



    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


}
