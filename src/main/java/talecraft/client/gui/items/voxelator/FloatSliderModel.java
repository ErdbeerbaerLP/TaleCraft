package talecraft.client.gui.items.voxelator;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import talecraft.client.gui.qad.QADSlider.SliderModel;
import talecraft.voxelator.params.FloatBrushParameter;

public class FloatSliderModel implements SliderModel<Float> {

    private final float min;
    private final float max;
    private final String name;
    private float current;
    private float value;
    private final NBTTagCompound tag;

    public FloatSliderModel(NBTTagCompound compound, FloatBrushParameter param) {
        tag = compound;
        min = param.getMinimum();
        max = param.getMaximum();
        name = param.getName();
        setValue(param.getDefault());
        if (compound.hasKey(name)) current = compound.getFloat(name);
    }

    @Override
    public Float getValue() {
        return current;
    }

    @Override
    public void setValue(Float value) {
        current = value;
        tag.setFloat(name, value);
    }

    @Override
    public String getValueAsText() {
        return name + ": " + current;
    }

    @Override
    public float getSliderValue() {
        return value;
    }

    @Override
    public void setSliderValue(float sliderValue) {
        this.value = sliderValue;
        setValue(Float.valueOf(ItemStack.DECIMALFORMAT.format(sliderValue * (max - min) + min)));
    }

}
