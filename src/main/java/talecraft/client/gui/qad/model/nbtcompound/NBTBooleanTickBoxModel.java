package talecraft.client.gui.qad.model.nbtcompound;

import net.minecraft.nbt.NBTTagCompound;
import talecraft.client.gui.qad.QADTickBox.TickBoxModel;

public class NBTBooleanTickBoxModel implements TickBoxModel {
    boolean state;
    final String tagKey;
    final NBTTagCompound tagParent;

    public NBTBooleanTickBoxModel(String key, NBTTagCompound parent) {
        this.tagKey = key;
        this.tagParent = parent;
        this.state = tagParent.getBoolean(tagKey);
    }

    @Override
    public boolean getState() {
        return state;
    }

    @Override
    public void setState(boolean newState) {
        if (state != newState) {
            state = newState;
            tagParent.setBoolean(tagKey, state);
        }
    }

    @Override
    public void toggleState() {
        setState(!getState());
    }

}
