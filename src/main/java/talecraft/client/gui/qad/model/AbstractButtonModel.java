package talecraft.client.gui.qad.model;

import net.minecraft.util.ResourceLocation;
import talecraft.client.gui.qad.QADButton.ButtonModel;

@SuppressWarnings("unused")
public abstract class AbstractButtonModel implements ButtonModel {
    public String text;
    public ResourceLocation icon;

    public AbstractButtonModel(String txt, ResourceLocation icn) {
        this.text = txt;
        this.icon = icn;
    }

    public AbstractButtonModel(String txt) {
        this.text = txt;
        this.icon = null;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String newText) {
        this.text = newText;
    }

    @Override
    public ResourceLocation getIcon() {
        return icon;
    }

    @Override
    public void setIcon(ResourceLocation newIcon) {
        this.icon = newIcon;
    }

    @Override
    public abstract void onClick();
}
