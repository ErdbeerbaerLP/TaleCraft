package talecraft.client.gui.vcui;

import talecraft.util.Vec2i;

import java.util.ArrayList;
import java.util.function.Consumer;

public class VCUIComponent {
    /**
     * Position of component in parent.
     **/
    public final Vec2i position;

    /**
     * The size of the component on screen.
     **/
    public final Vec2i size;

    /**
     * This children of this component.
     **/
    public final ArrayList<VCUIComponent> children;

    /**
     * A consumer representing a layoutmanager.
     **/
    public Consumer<VCUIComponent> layoutManager;

    /**
     * Creates a new VCUIComponent.
     **/
    public VCUIComponent() {
        children = new ArrayList<>();
        position = new Vec2i(0, 0);
        size = new Vec2i(1, 1);
    }

    public void keyTyped(char typedChar, int typedCode) {
    }

    public void update() {
    }

    public void draw() {
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
    }

    public void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
    }

}
