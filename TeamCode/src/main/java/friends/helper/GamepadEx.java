package friends.helper;

import com.qualcomm.robotcore.hardware.Gamepad;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class GamepadEx {
    public enum GamepadButton {
        A,
        B,
        X,
        Y,
        RIGHT_TRIGGER,
        LEFT_TRIGGER,
        RIGHT_BUMPER,
        LEFT_BUMPER,
        DPAD_LEFT,
        DPAD_RIGHT,
        DPAD_UP,
        DPAD_DOWN,
        LEFT_STICK,
        RIGHT_STICK,
        ALWAYS
    }

    public static GamepadEx primary;
    public static GamepadEx secondary;


    private final Map<Set<GamepadButton>, BiConsumer<Gamepad, Gamepad>> bindings =
            new HashMap<>();
    private final Map<Set<GamepadButton>, BiConsumer<Gamepad, Gamepad>> bindingsAlt =
            new HashMap<>();

    private final Gamepad gamepad;
    private final Gamepad prev;

    public static void initGamepads(Gamepad g1, Gamepad g2) {
        primary = new GamepadEx(g1);
        secondary = new GamepadEx(g2);
    }

    public GamepadEx(Gamepad gp) {
        gamepad = gp;
        prev = new Gamepad();
    }

    public void bind(GamepadButton btn, BiConsumer<Gamepad, Gamepad> callback) {
        bind(Set.of(btn), callback);
    }

    public void bind(
            GamepadButton btn,
            BiConsumer<Gamepad, Gamepad> downCallback,
            BiConsumer<Gamepad, Gamepad> upCallback) {
        bind(btn, downCallback);
        bindAlt(btn, upCallback);
    }

    public void bind(Set<GamepadButton> buttons, BiConsumer<Gamepad, Gamepad> callback) {
        if (bindings.containsKey(buttons)) {
            return;
        }
        bindings.put(buttons, callback);
    }

    public void bind(BiConsumer<Gamepad, Gamepad> callback, GamepadButton... btns) {
        bind(Arrays.stream(btns).collect(Collectors.toSet()), callback);
    }

    public void bindAlt(GamepadButton btn, BiConsumer<Gamepad, Gamepad> callback) {
        bindAlt(Set.of(btn), callback);
    }

    public void bindAlt(Set<GamepadButton> buttons, BiConsumer<Gamepad, Gamepad> callback) {
        if (bindingsAlt.containsKey(buttons)) {
            return;
        }
        bindingsAlt.put(buttons, callback);
    }

    public void bindAlt(BiConsumer<Gamepad, Gamepad> callback, GamepadButton... btns) {
        bindAlt(Arrays.stream(btns).collect(Collectors.toSet()), callback);
    }

    public void updateGamepad(Gamepad gp) {
        prev.copy(gamepad);
        gamepad.copy(gp);
    }

    public void update() {
        for (Map.Entry<Set<GamepadButton>, BiConsumer<Gamepad, Gamepad>> entry :
                bindings.entrySet()) {
            if (entry.getKey().stream().allMatch(this::getButtonState)) {
                entry.getValue().accept(gamepad, prev);
            }
        }

        for (Map.Entry<Set<GamepadButton>, BiConsumer<Gamepad, Gamepad>> entry :
                bindingsAlt.entrySet()) {
            if (entry.getKey().stream().allMatch(this::getButtonState)) {
                entry.getValue().accept(gamepad, prev);
            }
        }
    }

    public void update(Gamepad newGamepad) {
        updateGamepad(newGamepad);
        update();
    }

    public boolean getButtonState(GamepadButton btn) {
        switch (btn) {
            case A:
                return gamepad.a;
            case B:
                return gamepad.b;
            case X:
                return gamepad.x;
            case Y:
                return gamepad.y;
            case RIGHT_TRIGGER:
                return gamepad.right_trigger > 0;
            case LEFT_TRIGGER:
                return gamepad.left_trigger > 0;
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
                return gamepad.left_stick_x > 0 && gamepad.left_stick_y > 0;
            case RIGHT_STICK:
                return gamepad.right_stick_x > 0 && gamepad.right_stick_y > 0;
            case ALWAYS:
                return true; // Consider if this is truly needed.
            default:
                return false; // Add a default case to handle unexpected button values.
        }
    }
}
