package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.*;
import github.dctime.dctimesignals.data_component.SignalPickaxeDataComponent;
import github.dctime.dctimesignals.menu.GroundPenetratingSignalEmitterMenu;
import net.minecraft.client.particle.SmokeParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GroundPenetratingSignalEmitterBlockEntity extends BaseContainerBlockEntity implements GeoBlockEntity {
    public GroundPenetratingSignalEmitterBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK_ENTITY.get(), pos, blockState);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    public static final int ITEMS_SIZE = 3;
    public static final int ITEMS_PICKAXE_INPUT = 0;
    public static final int ITEMS_PICKAXE_OUTPUT = 1;
    public static final int ITEMS_FILTER = 2;

    public static final int DATA_SIZE = 1;

    private ItemStackHandler itemStackHandler = new ItemStackHandler(ITEMS_SIZE);
    private SimpleContainerData data = new SimpleContainerData(DATA_SIZE);

    @Override
    protected NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(ITEMS_SIZE, ItemStack.EMPTY);
        for (int i = 0; i < ITEMS_SIZE; i++) {
            items.set(i, itemStackHandler.getStackInSlot(i));
        }
        return items;
    }

    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

    public SimpleContainerData getData() {
        return data;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        if (nonNullList.size() != ITEMS_SIZE) {
            throw new IllegalArgumentException("Expected " + ITEMS_SIZE + " items, but got " + nonNullList.size());
        }

        for (int i = 0; i < ITEMS_SIZE; i++) {
            itemStackHandler.setStackInSlot(i, nonNullList.get(i));
        }
    }

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        // Will default to 0 if absent. See the NBT article for more information.
//        this.value = tag.getInt("value");=
        itemStackHandler.deserializeNBT(registries, tag.getCompound("itemStackHandler"));
        this.scanTicksCounter = tag.getInt("scanTicksCounter");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
//        tag.putInt("value", this.value);
        tag.put("itemStackHandler", itemStackHandler.serializeNBT(registries));
        tag.putInt("scanTicksCounter", scanTicksCounter);
        setChanged();
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.ground_penetrating_signal_emitter");
    }



    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new GroundPenetratingSignalEmitterMenu(i, inventory);
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

    private final int SCAN_BETWEEN_TICKS = 100;
    private int scanTicksCounter = SCAN_BETWEEN_TICKS;
    private boolean shouldScan() {
        scanTicksCounter--;
        if (scanTicksCounter <= 0) {
            scanTicksCounter = SCAN_BETWEEN_TICKS;
            return true;
        }
        return false;
    }

    private @Nullable BlockPos lastFoundBlockPos;

    public @Nullable BlockPos getLastFoundBlockPos() {
        return lastFoundBlockPos;
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, GroundPenetratingSignalEmitterBlockEntity entity) {
        tickScan(level, blockPos, blockState, entity);
        tickCheckSignalPickaxeIn(level, blockPos, blockState, entity);
    }

    private static void tickCheckSignalPickaxeIn(Level level, BlockPos blockPos, BlockState blockState, GroundPenetratingSignalEmitterBlockEntity entity) {
        if (level.isClientSide()) return;
        // check if there is a signal pickaxe in the input slot
        ItemStack pickaxeInput = entity.itemStackHandler.getStackInSlot(GroundPenetratingSignalEmitterBlockEntity.ITEMS_PICKAXE_INPUT);
        ItemStack pickaxeOutput = entity.itemStackHandler.getStackInSlot(GroundPenetratingSignalEmitterBlockEntity.ITEMS_PICKAXE_OUTPUT);
        if (!pickaxeOutput.isEmpty()) return;
        if (!pickaxeInput.is(RegisterItems.SIGNAL_PICKAXE)) return;

        // if there is a pickaxe, copy it to the output slot
        entity.itemStackHandler.setStackInSlot(GroundPenetratingSignalEmitterBlockEntity.ITEMS_PICKAXE_INPUT, ItemStack.EMPTY);
        ItemStack modifiedPickaxe = pickaxeInput.copyWithCount(1);
        SignalPickaxeDataComponent oldDataComponent = modifiedPickaxe.get(RegisterDataComponents.SIGNAL_PICKAXE_DATA_COMPONENT);

        if (oldDataComponent == null) return;
        modifiedPickaxe.set(RegisterDataComponents.SIGNAL_PICKAXE_DATA_COMPONENT, new SignalPickaxeDataComponent(
                true,
                entity.getBlockPos(),
                oldDataComponent.mode()
        ));

        entity.itemStackHandler.setStackInSlot(GroundPenetratingSignalEmitterBlockEntity.ITEMS_PICKAXE_OUTPUT, modifiedPickaxe);
    }

    private static void tickScan(Level level, BlockPos blockPos, BlockState blockState, GroundPenetratingSignalEmitterBlockEntity entity) {
        if (!entity.shouldScan() || level.isClientSide()) return;
        // scan the chunk for ores
        BlockPos scannedPos = entity.getOrePositionByScan(blockPos);
        // store it waiting for pickaxe to get info
        entity.lastFoundBlockPos = scannedPos;
    }

    private @Nullable BlockPos getOrePositionByScan(BlockPos blockPos) {
        LevelChunk chunk = level.getChunkAt(blockPos);

        int minY = level.getMinBuildHeight();
        int maxY = blockPos.getY();

        int chunkX = chunk.getPos().x;
        int chunkZ = chunk.getPos().z;

        if (!(level instanceof ServerLevel serverLevel)) return null;


        triggerAnim(SPIN_CONTROLLER_NAME, SPIN_ANIM_NAME);

        for (int y = maxY; y > minY; y--) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    BlockPos scanPos = new BlockPos((chunkX << 4) + x, y, (chunkZ << 4) + z);
                    BlockState state = level.getBlockState(scanPos);
                    Item item = Item.BY_BLOCK.get(state.getBlock());
                    if (itemStackHandler.getStackInSlot(ITEMS_FILTER).is(item)) {
                        serverLevel.playSound(null, blockPos, RegisterSoundEvents.GPS_SUCCESS_SOUND.get(), SoundSource.BLOCKS);
                        serverLevel.sendParticles(RegisterParticleTypes.GROUND_PENETRATING_SIGNAL_EMITTER_PARTICLE.get(),
                                (double) blockPos.getX()+0.5,
                                (double) blockPos.getY(),
                                (double) blockPos.getZ()+0.5,
                                100,
                                0,
                                0,
                                0,
                                0
                        );
                        // System.out.println("Found ore at: " + scanPos + " with state: " + state);
                        return scanPos;
                    }
                }
            }
        }

        // failed to found block
        serverLevel.playSound(null, blockPos, RegisterSoundEvents.GPS_FAIL_SOUND.get(), SoundSource.BLOCKS);
        serverLevel.sendParticles(ParticleTypes.CLOUD,
                (double) blockPos.getX()+0.5,
                (double) blockPos.getY(),
                (double) blockPos.getZ()+0.5,
                100,
                0,
                0,
                0,
                0.05
        );
        // System.out.println("No ore found in chunk at: " + blockPos);
        return null;
    }

    @Override
    public int getContainerSize() {
        return 2;
    }

    // geckolib

    protected static final RawAnimation SPIN_ANIM  = RawAnimation.begin().thenPlay("animation.ground_penetrating_signal_emitter_block.spinning");;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final String SPIN_CONTROLLER_NAME = "spinController";
    private final String SPIN_ANIM_NAME = "spin";
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, SPIN_CONTROLLER_NAME, this::deployAnimController).triggerableAnim(SPIN_ANIM_NAME, SPIN_ANIM));
    }
    protected <E extends GroundPenetratingSignalEmitterBlockEntity> PlayState deployAnimController(final AnimationState<E> state) {
        return PlayState.STOP;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
