package talecraft.blocks.tileentity;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import talecraft.TaleCraftRegistered;
import talecraft.invoke.IInvoke;

public class LightBlockTE extends TCTileEntity {
	int lightValue;
	boolean lightActive;

	public LightBlockTE() {
		super(TaleCraftRegistered.TE_LIGHT_BLOCK);
		this.lightValue = 15;
		this.lightActive = true;
	}

	public int getLightValue() {
		return lightActive ? lightValue : 0;
	}
	public int getSetLightValue() {
		return lightValue;
	}
	public void setLightValue(int value) {
		lightValue = value < 15 ? (value > 0 ? value : 0) : 15;
		world.notifyBlockUpdate(this.pos, world.getBlockState(pos), world.getBlockState(pos), 0); //TODO Confirm
		world.notifyLightSet(pos);
	}

	public void setLightActive(boolean flag) {
		if(lightActive != flag) {
			lightActive = flag;
			world.notifyBlockUpdate(this.pos, world.getBlockState(pos), world.getBlockState(pos), 0); //TODO Confirm
			world.notifyLightSet(pos);
		}
	}

	public void toggleLightActive() {
		lightActive = !lightActive;
		world.notifyBlockUpdate(this.pos, world.getBlockState(pos), world.getBlockState(pos), 0); //TODO Confirm
		world.notifyLightSet(pos);
	}

	@Override
	public void commandReceived(String command, NBTTagCompound data) {
		if(command.equals("toggle") || command.equals("trigger")) {
			setLightActive(!lightActive);
			return;
		}

		if(command.equals("on")) {
			setLightActive(true);
			return;
		}

		if(command.equals("off")) {
			setLightActive(false);
			return;
		}

		if(command.equals("set")) {
			setLightValue(data.getInt("lightValue"));
			return;
		}

		super.commandReceived(command, data);
	}

	@Override
	public void getInvokes(List<IInvoke> invokes) {
		// none
	}

	@Override
	public void getInvokeColor(float[] color) {
		color[0] = 1f;
		color[1] = 1f;
		color[2] = 0.75f;
	}

//	@Override
//	public String getName() {
//		return "LightBlock@"+getPos();
//	}

	@Override
	public void readFromNBT_do(NBTTagCompound comp) {
		lightValue = comp.getInt("lightValue");
		lightActive = comp.getBoolean("lightActive");

		if(world != null)
			world.checkLight(getPos());
	}

	@Override
	public NBTTagCompound writeToNBT_do(NBTTagCompound comp) {
		comp.setInt("lightValue", lightValue);
		comp.setBoolean("lightActive", lightActive);
		return comp;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 128; // reduced render distance, as light blocks may be placed in VERY large amounts.
	}

}
