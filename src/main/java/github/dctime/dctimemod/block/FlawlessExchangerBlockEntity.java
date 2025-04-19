package github.dctime.dctimemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FlawlessExchangerBlockEntity extends BlockEntity {
    public FlawlessExchangerBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.FLAWLESS_EXCHANGER_BLOCK_ENTITY.get(), pos, blockState);
    }

    private int processTime = 0;
    public int getProcessTime() {
        return processTime;
    }

    public void resetProcessTime() {
        processTime = 0;
    }

    public void incProcessTime() {
        processTime++;
    }

    // nbts storing data
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        processTime = tag.getInt("processTime");
        System.out.println("processTime loaded" + processTime);

    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("processTime", processTime);
        setChanged();
        System.out.println("ProcessTime saved" + processTime);
        // important setChanged();
    }

    //ticker
    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        if (level.isClientSide()) return;
        if (!(blockEntity instanceof FlawlessExchangerBlockEntity)) return;
        FlawlessExchangerBlockEntity flawlessExchangerBlockEntity = (FlawlessExchangerBlockEntity) blockEntity;
        if (flawlessExchangerBlockEntity.getProcessTime() == 200) {
            flawlessExchangerBlockEntity.resetProcessTime();
        }
        flawlessExchangerBlockEntity.incProcessTime();
    }

    // sync
    // load chunk and block update, get an update
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    // block update send packet, load chunk saveAdditional is called
    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
