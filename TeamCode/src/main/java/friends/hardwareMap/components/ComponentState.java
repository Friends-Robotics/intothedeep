package friends.hardwareMap.components;

public class ComponentState {
    public boolean State;

    public void toggleState() {
        State = !State;
    }

    public void setTrue() {
        State = true;
    }

    public void setFalse() {
        State = false;
    }

}