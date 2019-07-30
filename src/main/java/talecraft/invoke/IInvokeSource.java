package talecraft.invoke;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.mozilla.javascript.Scriptable;

import java.util.List;

public interface IInvokeSource {

    Scriptable getInvokeScriptScope();

    ICommandSender getInvokeAsCommandSender();

    BlockPos getInvokePosition();

    World getInvokeWorld();

    void getInvokes(List<IInvoke> invokes);

    void getInvokeColor(float[] color);

}
