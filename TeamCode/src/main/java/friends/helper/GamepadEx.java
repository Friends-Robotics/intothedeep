package friends.helper;

import android.util.Pair;

import com.qualcomm.robotcore.hardware.Gamepad;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class GamepadEx {
    private final Map<GamepadButton, BiConsumer<Gamepad, ButtonReader>> bindings =
            new HashMap<>();
    private final Map<GamepadButton, BiConsumer<Gamepad, ButtonReader>> bindingsAlt =
            new HashMap<>();
    private final Map<GamepadButton, ButtonReader> buttonReaders = new HashMap<>();

    private Gamepad gamepad;

    public GamepadEx(Gamepad gp) {
        gamepad = gp;
    }

    public void bind(GamepadButton btn, BiConsumer<Gamepad, ButtonReader> callback) {
        if(bindings.containsKey(btn)) return;
        if(!buttonReaders.containsKey(btn)) {
            ButtonReader reader = new ButtonReader(this, btn);
            buttonReaders.put(btn, reader);
        }
        bindings.put(btn, callback);
    }

    public void bindAlt(GamepadButton btn, BiConsumer<Gamepad, ButtonReader> callback) {
        if(bindingsAlt.containsKey(btn)) return;
        if(!buttonReaders.containsKey(btn)) {
            ButtonReader reader = new ButtonReader(this, btn);
            buttonReaders.put(btn, reader);
        }
        bindingsAlt.put(btn, callback);
    }

    public void bind(
            GamepadButton btn,
            BiConsumer<Gamepad, ButtonReader> downCallback,
            BiConsumer<Gamepad, ButtonReader> upCallback) {
        bind(btn, downCallback);
        bindAlt(btn, upCallback);
    }


    public void update(Gamepad gp) {
        gamepad = gp;

        // Update all button readers
        for(Map.Entry<GamepadButton, ButtonReader> entry : buttonReaders.entrySet()) {
            ButtonReader reader = entry.getValue();
            reader.read();
        }

        for (Map.Entry<GamepadButton, BiConsumer<Gamepad, ButtonReader>> entry :
                bindings.entrySet()) {
            if (get(entry.getKey())) {
                ButtonReader reader = buttonReaders.get(entry.getKey());
                entry.getValue().accept(gamepad, reader);
            }
        }

        for (Map.Entry<GamepadButton, BiConsumer<Gamepad, ButtonReader>> entry :
                bindingsAlt.entrySet()) {
            if (!get(entry.getKey())) {
                ButtonReader reader = buttonReaders.get(entry.getKey());
                entry.getValue().accept(gamepad, reader);
            }
        }
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
                return gamepad.left_stick_x > 0.1 && gamepad.left_stick_y > 0.1;
            case RIGHT_STICK:
                return gamepad.right_stick_x > 0.1 && gamepad.right_stick_y > 0.1;
            case TOUCHPAD:
                return gamepad.touchpad;
            default:
                return false;
        }
    }
}
