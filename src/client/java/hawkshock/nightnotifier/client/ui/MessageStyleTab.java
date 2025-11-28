package hawkshock.nightnotifier.client.ui;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public final class MessageStyleTab {
    private MessageStyleTab() {}
    public static void build(NightNotifierConfigScreen screen, int left, int right, int yLeft, int yRight, int w, int h) {
        try { MinecraftClient.getInstance().setScreen(MidnightConfig.getScreen((Screen) screen, "nightnotifier")); }
        catch (Throwable ignored) {}
    }
}