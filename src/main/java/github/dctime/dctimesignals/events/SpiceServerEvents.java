package github.dctime.dctimesignals.events;

import com.mojang.logging.LogUtils;
import github.dctime.dctimesignals.DCtimeMod;
import github.dctime.dctimesignals.lib.NgSpiceRunner;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

import java.io.IOException;

@EventBusSubscriber(modid = DCtimeMod.MODID, bus = EventBusSubscriber.Bus.GAME)
public class SpiceServerEvents {
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        try {
            NgSpiceRunner.extractNatives();
        } catch (IOException e) {
            LogUtils.getLogger().error("Failed to extract ngspice", e);
        }

        // 測試用 RC 電路 netlist
        String spice = """
        
        V1 in 0 SIN(0 1 440)
        R1 in out 1k
        C1 out 0 1u
        
        .control
        tran 22u 1
        run
        wrdata out.txt v(out)
        quit
        .endc
        
        .end
        """;

        NgSpiceRunner.runSimulationAsync(spice)
                .thenAccept(result -> {
                    LogUtils.getLogger().info("ngspice output:\n{}", result);
                })
                .exceptionally(e -> {
                    LogUtils.getLogger().error("Simulation failed", e);
                    return null;
                });

    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        NgSpiceRunner.shutdown();
    }
}
