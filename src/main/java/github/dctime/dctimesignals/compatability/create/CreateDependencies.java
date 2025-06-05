package github.dctime.dctimesignals.compatability.create;

import github.dctime.dctimesignals.DCtimeLanguageProvider;
import github.dctime.dctimesignals.DCtimeMod;
import net.createmod.ponder.foundation.PonderIndex;

import java.util.function.BiConsumer;

public class CreateDependencies {
    public static void addDependencies() {
        // ponder mod
        PonderIndex.addPlugin(new DCtimeModPonderPlugin());
    }

    public static void dependenciesDataGen(DCtimeLanguageProvider provider) {
        // create mod
        PonderIndex.addPlugin(new DCtimeModPonderPlugin());
        BiConsumer<String, String> langConsumer = provider::add;

        PonderIndex.getLangAccess().provideLang(DCtimeMod.MODID, langConsumer);
    }
}
