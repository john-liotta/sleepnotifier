package hawkshock.nightnotifier.client.ui;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

/**
 * GeneralTab bridge — open MidnightLib's generated config screen.
 * No reflection, no custom ModMenu integration required.
 */
public final class GeneralTab {
    private GeneralTab() {}

    public static void build(NightNotifierConfigScreen screen, int left, int right, int yLeft, int yRight, int w, int h) {
        try {
            MinecraftClient.getInstance().setScreen(MidnightConfig.getScreen((Screen) screen, "nightnotifier"));
        } catch (Throwable ignored) {}
    }
}