package github.dctime.dctimemod.block;

import github.dctime.dctimemod.RegisterBlockEntities;
import github.dctime.dctimemod.menu.FlawlessExchangerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class FlawlessExchangerBlockEntity extends BaseContainerBlockEntity{
    public final int ITEM_SIZE = 1;
    public static final int DATA_SIZE = 1;

    boolean PROCESSING = false;

    IItemHandler handler = new ItemStackHandler(NonNullList.withSize(ITEM_SIZE, ItemStack.EMPTY));
    EnergyStorage energyCap = new EnergyStorage(100000);
    ContainerData data = new SimpleContainerData(DATA_SIZE);

    // item id
    public final int ITEM_INPUT = 0;
    // data id
    public final int PROCESSING_TIME = 0;

    public FlawlessExchangerBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.FLAWLESS_EXCHANGER_BLOCK_ENTITY.get(), pos, blockState);
        data.set(PROCESSING_TIME, 0);
    }

    public EnergyStorage getEnergyCap() {
        return energyCap;
    }

    public IItemHandler getItemsHandler() {
        return handler;
    }

    public int getProcessTime() {
        return data.get(PROCESSING_TIME);
    }

    public void resetProcessTime() {
        data.set(PROCESSING_TIME, 0);
    }

    public void incProcessTime() {
        data.set(PROCESSING_TIME, getProcessTime() + 1);
    }

    // nbts storing data
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        data.set(PROCESSING_TIME, tag.getInt("processTime"));
        ((ItemStackHandler) handler).deserializeNBT(registries, tag.getCompound("items"));
        energyCap.deserializeNBT(registries, tag.get("energystored"));
        PROCESSING = tag.getBoolean("processing");

        System.out.println("Loaded energy: " + energyCap.getEnergyStored());
        System.out.println("processTime loaded" + data.get(PROCESSING_TIME));

    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.putInt("processTime", data.get(PROCESSING_TIME));
        tag.put("items", ((ItemStackHandler) handler).serializeNBT(registries));
        tag.put("energystored", energyCap.serializeNBT(registries));
        tag.putBoolean("processing", PROCESSING);

        setChanged();
        System.out.println("Stored energy" + energyCap.getEnergyStored() + " FE ");
        System.out.println("ProcessTime saved" + data.get(PROCESSING_TIME) + " ticks");
        // important setChanged();
    }


    //ticker
    public static void tick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        if (level.isClientSide()) return;

        // server
        IEnergyStorage energy = level.getCapability(Capabilities.EnergyStorage.BLOCK, blockPos, blockState, blockEntity, Direction.UP);
        if (!(blockEntity instanceof FlawlessExchangerBlockEntity)) return;
        FlawlessExchangerBlockEntity flawlessEntity = (FlawlessExchangerBlockEntity) blockEntity;

        System.out.println("Processing: " + flawlessEntity.PROCESSING);
        if (!flawlessEntity.PROCESSING) {

            // not processing
            if (flawlessEntity.getItem(flawlessEntity.ITEM_INPUT).is(Items.REDSTONE)) {
                flawlessEntity.PROCESSING = true;
                flawlessEntity.removeItem(flawlessEntity.ITEM_INPUT, 1);
            }
        } else {
            flawlessEntity.incProcessTime();
            energy.receiveEnergy(10, false);
            if (flawlessEntity.getProcessTime() == 200) {
                flawlessEntity.resetProcessTime();
                flawlessEntity.PROCESSING = false;
            }
        }

//        if (flawlessExchangerBlockEntity.getProcessTime() == 200) {
//            flawlessExchangerBlockEntity.resetProcessTime();
//            if ((flawlessExchangerBlockEntity.handler.getStackInSlot(0).getItem() == Items.DIRT &&
//                    flawlessExchangerBlockEntity.handler.getStackInSlot(0).getCount() != Items.DIRT.getDefaultMaxStackSize())
//                    || flawlessExchangerBlockEntity.handler.getStackInSlot(0).isEmpty()) {
//                ((ItemStackHandler) flawlessExchangerBlockEntity.handler).setStackInSlot(0,
//                        new ItemStack(Items.DIRT, flawlessExchangerBlockEntity.handler.getStackInSlot(0).getCount() + 1));
//                System.out.println("Given A dirt!");
//            } else {
//                System.out.println("Failed To give a dirt");
//
//                if (energy != null && energy.canReceive()) {
//                    System.out.println("Receive energy from flawless generated energy capability");
//                    energy.receiveEnergy(100, false);
//
//
//                    System.out.println("Energy Stored: " + energy.getEnergyStored() +
//                            " Max Energy Stored: " + energy.getMaxEnergyStored());
//                }
//            }
//            System.out.println("Energy Stored: " + energy.getEnergyStored() +
//                    " Max Energy Stored: " + energy.getMaxEnergyStored());
//        }
//
//        flawlessExchangerBlockEntity.incProcessTime();
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


    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.dctimemod.flawlessexchangerblockentity");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> nonNullList = NonNullList.withSize(ITEM_SIZE, ItemStack.EMPTY);
        for (int i = 0; i < ITEM_SIZE; i++) {
            nonNullList.set(i, handler.getStackInSlot(i));
        }
        return nonNullList;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        if (nonNullList.size() != ITEM_SIZE) return;
        if (handler instanceof ItemStackHandler itemStackHandler) {
            for (int i = 0; i < ITEM_SIZE; i++) {
                itemStackHandler.setStackInSlot(i, nonNullList.get(i));
            }
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new FlawlessExchangerMenu(i, inventory);
    }

    @Override
    public int getContainerSize() {
        return ITEM_SIZE;
    }

    private BlockCapabilityCache<IEnergyStorage, Direction> energyCache;

    @Override
    public void onLoad() {
        super.onLoad();

//        energyCache = BlockCapabilityCache.create(
//                Capabilities.EnergyStorage.BLOCK,
//                (ServerLevel) getLevel(),
//                getBlockPos(),
//                Direction.UP
//        );
    }

}
