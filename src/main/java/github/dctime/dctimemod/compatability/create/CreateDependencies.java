package github.dctime.dctimemod.compatability.create;

import net.createmod.ponder.foundation.PonderIndex;

public class CreateDependencies {
    public static void addDependencies() {
        PonderIndex.addPlugin(new DCtimeModPonderPlugin());
    }
}
