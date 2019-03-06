package talecraft.invoke;

import java.util.List;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IInvokeSource {

//	public Scriptable getInvokeScriptScope();

//	public ICommandSender getInvokeAsCommandSender();

	public BlockPos getInvokePosition();

	public World getInvokeWorld();

	public void getInvokes(List<IInvoke> invokes);

	public void getInvokeColor(float[] color);

}
