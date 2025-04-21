package github.dctime.dctimemod.block;

import github.dctime.dctimemod.DCtimeMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Set;
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

//            () -> new BlockEntityType<>(
//                    FlawlessExchangerBlockEntity::new,
//                    Set.of(RegisterBlocks.FLAWLESS_EXCHANGER.get())
//            )
    );
}
