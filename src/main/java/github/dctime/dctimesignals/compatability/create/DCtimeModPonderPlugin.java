package github.dctime.dctimesignals.compatability.create;

import github.dctime.dctimesignals.DCtimeMod;
import net.createmod.ponder.api.registration.*;
import net.minecraft.resources.ResourceLocation;

public class DCtimeModPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return DCtimeMod.MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        DCtimeModPonders.registerScenes(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        DCtimeModPonders.registerTags(helper);
    }

    @Override
    public void registerSharedText(SharedTextRegistrationHelper helper) {
        // helper.registerSharedText("rpm8", "8 RPM");
    }

    @Override
    public void indexExclusions(IndexExclusionHelper helper) {
        // helper.excludeBlockVariants(ValveHandleBlock.class, AllBlocks.COPPER_VALVE_HANDLE.get());
    }
}
