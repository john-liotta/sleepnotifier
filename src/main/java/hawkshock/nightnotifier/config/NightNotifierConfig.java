package hawkshock.nightnotifier.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Server configuration for Night Notifier.
 * Extended with configurable rest threshold and multi-offender listing.
 */
public final class NightNotifierConfig {

    public boolean sendTitle = true;
    public boolean sendSubtitle = true;
    public boolean sendActionBar = false;

    public boolean sendVanillaToModdedClients = false;

    public int overlayDuration = 100;

    public boolean enablePhantomScreams = true;
    public float nightScreamVolume = 1.0f;
    public float morningScreamVolume = 2.0f;

    public int morningWarningLeadTicks = 1200;

    public boolean enableNetherNotifications = false;
    public boolean enableEndNotifications = false;

    public int titleFadeIn = 10;
    public int titleStay = 60;
    public int titleFadeOut = 10;

    // New: configurable rest threshold (default 56000 ticks ~= 2.33 days) and multi-offender list sizing.
    public int restThresholdTicks = 56000;
    public int maxOffenderNames = 5; // how many additional offenders to list after top player (excluding the top)

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = Paths.get("config", "nightnotifier.json");

    public static NightNotifierConfig loadOrCreate() {
        try {
            if (Files.notExists(CONFIG_PATH)) {
                NightNotifierConfig cfg = new NightNotifierConfig();
                save(cfg);
                return cfg;
            }
            try (Reader r = Files.newBufferedReader(CONFIG_PATH)) {
                NightNotifierConfig cfg = GSON.fromJson(r, NightNotifierConfig.class);
                return cfg != null ? cfg : new NightNotifierConfig();
            }
        } catch (IOException e) {
            return new NightNotifierConfig();
        }
    }

    public static void save(NightNotifierConfig cfg) {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer w = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(cfg, w);
            }
        } catch (IOException ignored) {
        }
    }
}