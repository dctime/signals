package github.dctime.dctimemod.compatability.create;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.ponder.CreatePonderPlugin;
import com.tterrag.registrate.providers.ProviderType;
import github.dctime.dctimemod.DCtimeLanguageProvider;
import github.dctime.dctimemod.DCtimeMod;
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
