package github.dctime.dctimemod;

import github.dctime.dctimemod.block.ConstSignalBlockEntity;
import github.dctime.dctimemod.block.FlawlessExchangerBlockEntity;
import github.dctime.dctimemod.block.SignalWireBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegisterBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, DCtimeMod.MODID);

    public static final Supplier<BlockEntityType<FlawlessExchangerBlockEntity>> FLAWLESS_EXCHANGER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "flawless_exchanger_block_entity",
            ()->BlockEntityType.Builder.of(
                    FlawlessExchangerBlockEntity::new,
                    RegisterBlocks.FLAWLESS_EXCHANGER.get()
            ).build(null)
    );

    public static final Supplier<BlockEntityType<SignalWireBlockEntity>> SIGNAL_WIRE_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "signal_wire_block_entity",
            ()-> BlockEntityType.Builder.of(
                    SignalWireBlockEntity::new,
                    RegisterBlocks.SINGAL_WIRE.get(),
                    RegisterBlocks.SINGAL_TO_REDSTONE_CONVERTER.get()
            ).build(null)
    );

    public static final Supplier<BlockEntityType<ConstSignalBlockEntity>> CONST_SIGNAL_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "const_signal_block_entity",
            ()-> BlockEntityType.Builder.of(
                    ConstSignalBlockEntity::new,
                    RegisterBlocks.CONSTANT_SIGNAL_BLOCK.get()
            ).build(null)
    );

}
