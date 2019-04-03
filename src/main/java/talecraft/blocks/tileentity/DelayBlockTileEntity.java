package talecraft.blocks.tileentity;

import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import talecraft.TaleCraftRegistered;
import talecraft.invoke.BlockTriggerInvoke;
import talecraft.invoke.EnumTriggerState;
import talecraft.invoke.IInvoke;
import talecraft.invoke.Invoke;

public class DelayBlockTileEntity extends TCTileEntity {
	IInvoke triggerInvoke;
	int delay;
	private boolean triggered = false;

	public DelayBlockTileEntity() {
		super(TaleCraftRegistered.TE_DELAY);
		triggerInvoke = BlockTriggerInvoke.ZEROINSTANCE;
		delay = 20;
		
	}

	@Override
	public void getInvokes(List<IInvoke> invokes) {
		invokes.add(triggerInvoke);
	}

	@Override
	public void getInvokeColor(float[] color) {
		color[0] = 1.00f;
		color[1] = 0.75f;
		color[2] = 1.00f;
	}


	@Override
	public void readFromNBT_do(NBTTagCompound comp) {
		triggerInvoke = IInvoke.Serializer.read(comp.getCompound("triggerInvoke"));
		delay = comp.getInt("delay");
	}

	@Override
	public NBTTagCompound writeToNBT_do(NBTTagCompound comp) {
		comp.setTag("triggerInvoke", IInvoke.Serializer.write(triggerInvoke));
		comp.setInt("delay", delay);
		return comp;
	}

	public void trigger(EnumTriggerState triggerState) {
		if(triggerState.getBooleanValue()) {
			this.triggered = true;
		}
	}

	public void invokeFromUpdateTick() {
		Invoke.invoke(triggerInvoke, this, null, EnumTriggerState.IGNORE);
	}

	@Override
	public void commandReceived(String command, NBTTagCompound data) {
		if(command.equals("set")) {
			delay = data.getInt("delay");
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 0); //TODO Confirm
			return;
		}

		super.commandReceived(command, data);
	}

	public IInvoke getInvoke() {
		return triggerInvoke;
	}

	public int getDelayValue() {
		return delay;
	}
	int ticks = 0;
	@Override
	public void tick() {
		System.out.println("T? "+triggered+" TICKS: "+ticks+" DELAY: "+getDelayValue());
		if(triggered) {
			ticks++;
			System.out.println("Triggered for "+ticks+ " ticks");
			if(ticks > getDelayValue()) {
				triggered = false;
				ticks = 0;
				if (world.getTileEntity(pos) instanceof DelayBlockTileEntity) {
					System.out.println("Triggering next...");
					invokeFromUpdateTick();
				}
			}else return;
		}
	}
}
