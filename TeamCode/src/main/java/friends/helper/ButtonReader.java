package friends.helper;

import java.util.function.BooleanSupplier;
import friends.helper.GamepadButton;

public class ButtonReader {
    private boolean last;
    private boolean current;
    private BooleanSupplier state;

    public ButtonReader(GamepadEx gamepad, GamepadButton button) {
        state = () -> gamepad.get(button);
        current = state.getAsBoolean();
        last = current;
    }

    public void read() {
        last = current;
        current = state.getAsBoolean();
    }

    public boolean down() {
        return state.getAsBoolean();
    }

    public boolean justPressed() {
        return (!last && current);
    }
}

