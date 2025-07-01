package github.dctime.dctimesignals.menu;

import github.dctime.dctimesignals.RegisterBlocks;
import github.dctime.dctimesignals.RegisterMenuTypes;
import github.dctime.dctimesignals.block.SignalResearchStationBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class SignalResearchMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;
    private final ContainerData inputSignalData;
    private final ContainerData outputSignalData;

    public ContainerData getInputSignalData() {
        return inputSignalData;
    }

    public ContainerData getOutputSignalData() {
        return outputSignalData;
    }

    // Server
    public SignalResearchMenu(int containerId, Inventory playerInv, ContainerLevelAccess access, ContainerData inputSignalData, ContainerData outputSignalData) {
        super(RegisterMenuTypes.SIGNAL_RESEARECH_MENU.get(), containerId);
        this.access = access;
        this.inputSignalData = inputSignalData;
        this.outputSignalData = outputSignalData;

        checkContainerDataCount(inputSignalData, SignalResearchStationBlockEntity.DATA_SIZE_INPUT_SIGNAL);
        checkContainerDataCount(outputSignalData, SignalResearchStationBlockEntity.DATA_SIZE_OUTPUT_SIGNAL);

        addDataSlots(this.inputSignalData);
        addDataSlots(this.outputSignalData);

    }

    // Client menu constructor (Registered)
    public SignalResearchMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL, new SimpleContainerData(SignalResearchStationBlockEntity.DATA_SIZE_INPUT_SIGNAL), new SimpleContainerData(SignalResearchStationBlockEntity.DATA_SIZE_OUTPUT_SIGNAL));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(this.access, player, RegisterBlocks.SIGNAL_RESEARCH_STATION.get());
    }
}
