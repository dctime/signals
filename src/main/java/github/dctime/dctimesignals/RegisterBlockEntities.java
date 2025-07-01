package github.dctime.dctimesignals;

import github.dctime.dctimesignals.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

    public static final Supplier<BlockEntityType<SignalOperationBlockEntity>> SIGNAL_OPERATION_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
        "signal_operation_block_entity",
        ()-> BlockEntityType.Builder.of(
            SignalOperationBlockEntity::new,
            RegisterBlocks.SIGNAL_OPERATION_BLOCK.get()
        ).build(null)
    );

    public static final Supplier<BlockEntityType<RedstoneToSignalConverterBlockEntity>> REDSTONE_TO_SIGNAL_CONVERTER_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "redstone_to_signal_converter_block_entity",
            ()-> BlockEntityType.Builder.of(
                    RedstoneToSignalConverterBlockEntity::new,
                    RegisterBlocks.REDSTONE_TO_SIGNAL_CONVERTER.get()
            ).build(null)
    );

    public static final Supplier<BlockEntityType<SignalResearchStationSignalInputBlockEntity>> SIGNAL_RESEARCH_STATION_SIGNAL_INPUT_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "signal_research_station_signal_input_block_entity",
            ()-> BlockEntityType.Builder.of(
                    SignalResearchStationSignalInputBlockEntity::new,
                    RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_INPUT.get()
            ).build(null)
    );

    public static final Supplier<BlockEntityType<SignalResearchStationSignalOutputBlockEntity>> SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "signal_research_station_signal_output_block_entity",
            ()-> BlockEntityType.Builder.of(
                    SignalResearchStationSignalOutputBlockEntity::new,
                    RegisterBlocks.SIGNAL_RESEARCH_STATION_SIGNAL_OUTPUT.get()
            ).build(null)
    );

    public static final Supplier<BlockEntityType<SignalResearchStationBlockEntity>> SIGNAL_RESEARCH_STATION_BLOCK_ENTITY = BLOCK_ENTITY_TYPES.register(
            "signal_research_station_block_entity",
            ()-> BlockEntityType.Builder.of(
                    SignalResearchStationBlockEntity::new,
                    RegisterBlocks.SIGNAL_RESEARCH_STATION.get()
            ).build(null)
    );
}
