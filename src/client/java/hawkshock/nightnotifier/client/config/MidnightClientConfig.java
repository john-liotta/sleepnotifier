package hawkshock.nightnotifier.client.config;

import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.lib.config.annotation.Entry;

/**
 * MidnightLib-backed client config.
 * Tabs: "General", "Message Style", "Sound Effects", "Progress Bar"
 *
 * Message/text related settings live under "Message Style".
 */
public class MidnightClientConfig extends MidnightConfig {
    public static final String GENERAL = "General";
    public static final String MESSAGE_STYLE = "Message Style";
    public static final String SOUND = "Sound Effects";
    public static final String PROGRESS = "Progress Bar";

    // === General ===
    @Entry(category = GENERAL, name = "Enable Notifications")
    public static boolean enableNotifications = true;

    @Entry(category = GENERAL, name = "Show in Nether")
    public static boolean showNetherNotifications = false;

    @Entry(category = GENERAL, name = "Show in End")
    public static boolean showEndNotifications = false;

    @Entry(category = GENERAL, name = "Include All Server Players in Notifications")
    public static boolean showAllOffenders = true;

    // === Message Style ===
    @Entry(category = MESSAGE_STYLE, name = "Use My Message Style")
    public static boolean useClientStyle = true;

    @Entry(category = MESSAGE_STYLE, name = "Color (#RRGGBB or #AARRGGBB)", isColor = true)
    public static String colorHex = "#FFFFFF";

    @Entry(category = MESSAGE_STYLE, name = "Anchor")
    public static String anchor = "TOP_CENTER";

    @Entry(category = MESSAGE_STYLE, name = "Offset X")
    public static int offsetX = 0;

    @Entry(category = MESSAGE_STYLE, name = "Offset Y")
    public static int offsetY = 80;

    @Entry(category = MESSAGE_STYLE, name = "Text Alignment")
    public static String textAlign = "CENTER";

    @Entry(category = MESSAGE_STYLE, name = "Text Scale", isSlider = true, min = 0.5f, max = 2.5f, precision = 100)
    public static float textScale = 1.7f;

    @Entry(category = MESSAGE_STYLE, name = "Default Duration (ticks)")
    public static int defaultDuration = 100;

    // === Sound Effects ===
    @Entry(category = SOUND, name = "Enable Phantom Screams")
    public static boolean enablePhantomScreams = true;

    @Entry(category = SOUND, name = "Night Scream Volume", isSlider = true, min = 0f, max = 3f, precision = 100)
    public static float nightScreamVolume = 1.0f;

    @Entry(category = SOUND, name = "Morning Scream Volume", isSlider = true, min = 0f, max = 3f, precision = 100)
    public static float morningScreamVolume = 2.0f;

    @Entry(category = SOUND, name = "Morning Warning Lead (ticks)")
    public static int morningWarningLeadTicks = 1200;

    // === Progress Bar ===
    @Entry(category = PROGRESS, name = "Enable Progress Bar")
    public static boolean enableProgressBar = false;

    @Entry(category = PROGRESS, name = "Disable Sun Icon")
    public static boolean disableSunIcon = false;

    @Entry(category = PROGRESS, name = "Disable Moon Icon")
    public static boolean disableMoonIcon = false;

    @Entry(category = PROGRESS, name = "Progress Bar Width (-1 auto)")
    public static int progressBarWidth = -1;

    @Entry(category = PROGRESS, name = "Progress Bar Height (-1 auto)")
    public static int progressBarHeight = -1;

    @Entry(category = PROGRESS, name = "Progress Bar Y Offset")
    public static int progressBarYOffset = 0;

    @Entry(category = PROGRESS, name = "Dusk Color (hex)", isColor = true)
    public static String progressSectionColor0 = "#FFAA33";

    @Entry(category = PROGRESS, name = "Mid Night Color (hex)", isColor = true)
    public static String progressSectionColor1 = "#3333FF";

    @Entry(category = PROGRESS, name = "Night Ending Color (hex)", isColor = true)
    public static String progressSectionColor2 = "#AA33FF";

    @Entry(category = PROGRESS, name = "Morning Warning Color (hex)", isColor = true)
    public static String progressSectionColor3 = "#FFDD66";
}