package friends.helper.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import friends.helper.Colours;

public class GamepadEx {
    private static void panic() {
        throw new RuntimeException("PANIC: Multiple Bindings Of One Key");
    }
    private final Map<GamepadButton, BiConsumer<ButtonReader, Gamepad>> downs =
            new EnumMap<>(GamepadButton.class);
    private final Map<GamepadButton, BiConsumer<ButtonReader, Gamepad>> presses =
            new EnumMap<>(GamepadButton.class);
    private final Map<GamepadButton, BiConsumer<ButtonReader, Gamepad>> ups =
            new EnumMap<>(GamepadButton.class);
    private final Map<GamepadButton, BiConsumer<ButtonReader, Gamepad>> releases =
            new EnumMap<>(GamepadButton.class);

    private final Map<GamepadButton, ButtonReader> buttonReaders = new EnumMap<>(GamepadButton.class);

    private final Gamepad gamepad;

    public GamepadEx(Gamepad gp) {
        gamepad = gp;
    }

    /// Runs Continuously Every Update Cycle
    public void down(GamepadButton btn, BiConsumer<ButtonReader, Gamepad> callback) {
        if(downs.containsKey(btn)) panic();
        if(!buttonReaders.containsKey(btn)) {
            ButtonReader reader = new ButtonReader(this, btn);
            buttonReaders.put(btn, reader);
        }
        downs.put(btn, callback);
    }

    public void down(GamepadButton btn, Consumer<Gamepad> callback) {
        down(btn, (reader, gamepad) -> callback.accept(gamepad));
    }

    public void down(GamepadButton btn, Runnable callback) {
        down(btn, (reader, gamepad) -> callback.run());
    }


    /// Runs Once When Key Is Pressed
    public void pressed(GamepadButton btn, BiConsumer<ButtonReader, Gamepad> callback) {
        if(presses.containsKey(btn)) panic();
        if(!buttonReaders.containsKey(btn)) {
            ButtonReader reader = new ButtonReader(this, btn);
            buttonReaders.put(btn, reader);
        }
        presses.put(btn, callback);
    }

    public void pressed(GamepadButton btn, Consumer<Gamepad> callback) {
        pressed(btn, (reader, gamepad) -> callback.accept(gamepad));
    }

    public void pressed(GamepadButton btn, Runnable callback) {
        pressed(btn, (reader, gamepad) -> callback.run());
    }

    /// Runs Continuously Every Update Cycle
    public void up(GamepadButton btn, BiConsumer<ButtonReader, Gamepad> callback) {
        if(ups.containsKey(btn)) panic();
        if(!buttonReaders.containsKey(btn)) {
            ButtonReader reader = new ButtonReader(this, btn);
            buttonReaders.put(btn, reader);
        }
        ups.put(btn, callback);
    }

    public void up(GamepadButton btn, Consumer<Gamepad> callback) {
        up(btn, ( reader,gamepad) -> callback.accept(gamepad));
    }

    public void up(GamepadButton btn, Runnable callback) {
        up(btn, (reader, gamepad) -> callback.run());
    }
    /// Runs Once When Key Is Released
    public void released(GamepadButton btn, BiConsumer<ButtonReader, Gamepad> callback) {
        if(releases.containsKey(btn)) panic();
        if(!buttonReaders.containsKey(btn)) {
            ButtonReader reader = new ButtonReader(this, btn);
            buttonReaders.put(btn, reader);
        }
        releases.put(btn, callback);
    }

    public void released(GamepadButton btn, Consumer<Gamepad> callback) {
        released(btn, (reader, gamepad) -> callback.accept(gamepad));
    }

    public void released(GamepadButton btn, Runnable callback) {
        released(btn, (reader, gamepad) -> callback.run());
    }

    public void update() {
        for(GamepadButton btn : GamepadButton.values()) {
            ButtonReader reader = buttonReaders.get(btn);
            if(reader == null) continue;

            reader.read();

            if(reader.justPressed() && presses.containsKey(btn)) {
                Objects.requireNonNull(presses.get(btn)).accept(reader, gamepad);
            }

            if(reader.down() && downs.containsKey(btn)) {
                Objects.requireNonNull(downs.get(btn)).accept( reader, gamepad);
            }

            if(reader.up() && ups.containsKey(btn)) {
                Objects.requireNonNull(ups.get(btn)).accept(reader, gamepad);
            }

            if(reader.justReleased() && releases.containsKey(btn)) {
                Objects.requireNonNull(releases.get(btn)).accept(reader, gamepad);
            }
        }
    }

    public void setColour(Colours colour) {
        if(gamepad == null) return;

        gamepad.setLedColor(colour.R(), colour.G(), colour.B(), Gamepad.LED_DURATION_CONTINUOUS);
    }

    public boolean get(GamepadButton btn) {
        switch (btn) {
            case A:
            case CROSS:
                return gamepad.a;
            case B:
            case CIRCLE:
                return gamepad.b;
            case X:
            case SQUARE:
                return gamepad.x;
            case Y:
            case TRIANGLE:
                return gamepad.y;
            case RIGHT_TRIGGER:
                return gamepad.right_trigger > 0.1;
            case LEFT_TRIGGER:
                return gamepad.left_trigger > 0.1;
            case DPAD_LEFT:
                return gamepad.dpad_left;
            case DPAD_UP:
                return gamepad.dpad_up;
            case DPAD_DOWN:
                return gamepad.dpad_down;
            case DPAD_RIGHT:
                return gamepad.dpad_right;
            case RIGHT_BUMPER:
                return gamepad.right_bumper;
            case LEFT_BUMPER:
                return gamepad.left_bumper;
            case LEFT_STICK:
                return Math.hypot(gamepad.left_stick_x, gamepad.left_stick_y) > 0.1;
            case RIGHT_STICK:
                return Math.hypot(gamepad.right_stick_x, gamepad.right_stick_y) > 0.1;
            case TOUCHPAD:
                return gamepad.touchpad;
            case LEFT_STICK_BUTTON:
                    return gamepad.left_stick_button;
            case RIGHT_STICK_BUTTON:
                return gamepad.right_stick_button;
            case PLAYSTATION:
                return gamepad.ps;
            default:
                return false;
        }
    }
}
