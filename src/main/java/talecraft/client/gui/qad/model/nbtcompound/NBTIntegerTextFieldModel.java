package talecraft.client.gui.qad.model.nbtcompound;

import net.minecraft.nbt.NBTTagCompound;
import talecraft.client.gui.qad.QADTextField.TextFieldModel;

public final class NBTIntegerTextFieldModel implements TextFieldModel {
    String text;
    final String tagKey;
    final NBTTagCompound tagParent;
    boolean valid;

    public NBTIntegerTextFieldModel(String tagKey, NBTTagCompound tagParent) {
        this.tagKey = tagKey;
        this.tagParent = tagParent;
        this.text = Integer.toString(tagParent.getInteger(tagKey));
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
            int value = Integer.parseInt(text);
            tagParent.setInteger(tagKey, value);
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