package talecraft.invoke;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;

public class BlockTriggerInvoke implements IInvoke {
	private static final int[] ZEROBOUNDS = new int[6];
	public static final BlockTriggerInvoke ZEROINSTANCE = new BlockTriggerInvoke();
	public static final String TYPE = "BlockTriggerInvoke";

	int[] bounds;
	EnumTriggerState triggerState;

	public BlockTriggerInvoke() {
		bounds = ZEROBOUNDS;
		triggerState = EnumTriggerState.ON;
	}

	public int[] getBounds() {
		return bounds;
	}

	public EnumTriggerState getOnOff() {
		return triggerState;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void getColor(float[] color_out) {
		color_out[0] = 1.0f;
		color_out[1] = 0.5f;
		color_out[2] = 0.0f;
	}

	@Override
	public void write(NBTTagCompound compound) {
		compound.setIntArray("bounds", bounds);
		compound.setInt("state", triggerState.getIntValue());
	}

	@Override
	public void read(NBTTagCompound compound) {
		triggerState = EnumTriggerState.get(compound.hasKey("state") ? compound.getInt("state") : 1);
		bounds = compound.getIntArray("bounds");

		if(bounds == null)
			bounds = new int[6];
		else if(bounds.length != 6)
			bounds = Arrays.copyOf(bounds, 6);
	}

}
