package ship.control.panel.model;

public class Ship {
    private static boolean on;

    public Ship() {
        on = false;
    }

    public static boolean isOn() {
        return on;
    }

    public static void setOn(boolean on) {
        Ship.on = on;
    }
}
