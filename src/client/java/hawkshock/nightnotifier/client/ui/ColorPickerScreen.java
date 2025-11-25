package hawkshock.nightnotifier.client.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

/**
 * Small color picker screen. Returns a validated hex string via the onPick callback.
 * Formats: #RRGGBB or #AARRGGBB (alpha included only when != 0xFF).
 */
public class ColorPickerScreen extends Screen {
    private final Screen parent;
    private final Consumer<String> onPick;

    private int red = 255, green = 255, blue = 255, alpha = 255;
    private TextFieldWidget hexField;

    protected ColorPickerScreen(Screen parent, String initialHex, Consumer<String> onPick) {
        super(Text.literal("Color Picker"));
        this.parent = parent;
        this.onPick = onPick;
        parseInitial(initialHex);
    }

    public static ColorPickerScreen open(Screen parent, String initialHex, Consumer<String> onPick) {
        return new ColorPickerScreen(parent, initialHex, onPick);
    }

    private void parseInitial(String hex) {
        if (hex == null) return;
        String s = hex.trim().toUpperCase();
        try {
            if (!s.startsWith("#")) return;
            if (s.length() == 7) {
                red = Integer.parseInt(s.substring(1, 3), 16);
                green = Integer.parseInt(s.substring(3, 5), 16);
                blue = Integer.parseInt(s.substring(5, 7), 16);
                alpha = 255;
            } else if (s.length() == 9) {
                alpha = Integer.parseInt(s.substring(1, 3), 16);
                red = Integer.parseInt(s.substring(3, 5), 16);
                green = Integer.parseInt(s.substring(5, 7), 16);
                blue = Integer.parseInt(s.substring(7, 9), 16);
            }
        } catch (Exception ignored) { }
    }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int y = 40;
        int w = 200;
        int h = 20;

        hexField = new TextFieldWidget(this.textRenderer, cx - w / 2, y, w, h, Text.literal("Hex"));
        hexField.setText(formatHex());
        hexField.setChangedListener(s -> {
            String v = s.trim().toUpperCase();
            String valid = validateColor(v, null);
            if (valid != null) applyHexToSliders(valid);
        });
        addSelectableChild(hexField);
        y += 28;

        addDrawableChild(new ByteSlider(cx - w / 2, y, w, h, Text.literal("R"), red / 255f, val -> {
            red = (int)Math.round(val * 255f);
            hexField.setText(formatHex());
        }));
        y += 24;

        addDrawableChild(new ByteSlider(cx - w / 2, y, w, h, Text.literal("G"), green / 255f, val -> {
            green = (int)Math.round(val * 255f);
            hexField.setText(formatHex());
        }));
        y += 24;

        addDrawableChild(new ByteSlider(cx - w / 2, y, w, h, Text.literal("B"), blue / 255f, val -> {
            blue = (int)Math.round(val * 255f);
            hexField.setText(formatHex());
        }));
        y += 24;

        addDrawableChild(new ByteSlider(cx - w / 2, y, w, h, Text.literal("A"), alpha / 255f, val -> {
            alpha = (int)Math.round(val * 255f);
            hexField.setText(formatHex());
        }));
        y += 30;

        addDrawableChild(ButtonWidget.builder(Text.literal("OK"), b -> {
            String out = formatHex();
            onPick.accept(out);
            MinecraftClient.getInstance().setScreen(parent);
        }).dimensions(cx - 105, y, 100, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Cancel"), b -> {
            MinecraftClient.getInstance().setScreen(parent);
        }).dimensions(cx + 5, y, 100, 20).build());
    }

    private void applyHexToSliders(String hex) {
        if (hex.length() == 7) {
            try {
                red = Integer.parseInt(hex.substring(1, 3), 16);
                green = Integer.parseInt(hex.substring(3, 5), 16);
                blue = Integer.parseInt(hex.substring(5, 7), 16);
                alpha = 255;
            } catch (Exception ignored) { }
        } else if (hex.length() == 9) {
            try {
                alpha = Integer.parseInt(hex.substring(1, 3), 16);
                red = Integer.parseInt(hex.substring(3, 5), 16);
                green = Integer.parseInt(hex.substring(5, 7), 16);
                blue = Integer.parseInt(hex.substring(7, 9), 16);
            } catch (Exception ignored) { }
        }
        if (hexField != null) hexField.setText(formatHex());
    }

    private String formatHex() {
        if (alpha == 255) {
            return String.format("#%02X%02X%02X", red, green, blue);
        } else {
            return String.format("#%02X%02X%02X%02X", alpha, red, green, blue);
        }
    }

    private String validateColor(String in, String fallback) {
        if (in == null) return fallback;
        String s = in.trim().toUpperCase();
        if (!s.startsWith("#")) return fallback;
        int len = s.length();
        if (len != 7 && len != 9) return fallback;
        for (int i = 1; i < len; i++) {
            char c = s.charAt(i);
            boolean hex = (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F');
            if (!hex) return fallback;
        }
        return s;
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0, 0, this.width, this.height, 0x88000000);
        super.render(ctx, mouseX, mouseY, delta);
        ctx.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 10, 0xFFFFFF);

        int px = this.width / 2 + 120;
        int py = 60;
        int pw = 40;
        int ph = 40;
        int col = ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
        ctx.fill(px, py, px + pw, py + ph, col);
    }

    private static class ByteSlider extends SliderWidget {
        private final java.util.function.Consumer<Double> onDrag;

        ByteSlider(int x, int y, int width, int height, Text label, double initial, java.util.function.Consumer<Double> onDrag) {
            super(x, y, width, height, label, initial);
            this.onDrag = onDrag;
            updateMessage();
        }

        @Override
        protected void applyValue() {
            onDrag.accept(this.value);
        }

        @Override
        protected void updateMessage() {
            int v = Math.round((float)this.value * 255f);
            setMessage(Text.literal(getMessage().getString().split(":")[0] + ": " + v));
        }
    }
}