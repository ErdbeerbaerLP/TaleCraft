package talecraft.client.gui.qad.model.nbtcompound;

import net.minecraft.nbt.NBTTagCompound;
import talecraft.client.gui.qad.QADTextField.TextFieldModel;

public final class NBTFloatTextFieldModel implements TextFieldModel {
    String text;
    final String tagKey;
    final NBTTagCompound tagParent;
    boolean valid;

    public NBTFloatTextFieldModel(String tagKey, NBTTagCompound tagParent) {
        this.tagKey = tagKey;
        this.tagParent = tagParent;
        this.text = Float.toString(tagParent.getFloat(tagKey));
        this.valid = true;
    }

    @Override
    public int getTextLength() {
        return text.length();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String newText) {
        text = newText;
        try {
            float value = Float.parseFloat(text);
            tagParent.setFloat(tagKey, value);
            valid = true;
        } catch (NumberFormatException e) {
            valid = false; // :(
        }
    }

    @Override
    public char getCharAt(int i) {
        return text.charAt(i);
    }

    @Override
    public int getTextColor() {
        return valid ? 0xFFFFFFFF : 0xFFFF7070;
    }

    @Override
    public void setTextColor(int color) {
        // nope
    }
}