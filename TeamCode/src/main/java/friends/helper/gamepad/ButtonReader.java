package friends.helper.gamepad;

import java.util.function.BooleanSupplier;

public class ButtonReader {
    private boolean last;
    private boolean current;
    private final BooleanSupplier state;

    public ButtonReader(GamepadEx gamepad, GamepadButton button) {
        state = () -> gamepad.get(button);
        current = state.getAsBoolean();
        last = current;
    }

    public void read() {
        last = current;
        current = state.getAsBoolean();
    }

    public boolean down() { return state.getAsBoolean(); }
    public boolean up() { return !state.getAsBoolean(); }

    public boolean justPressed() { return (!last && current); }
    public boolean justReleased() { return (!current && last); }
}

