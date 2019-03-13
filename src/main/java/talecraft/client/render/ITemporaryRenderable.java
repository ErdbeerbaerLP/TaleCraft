package talecraft.client.render;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ITemporaryRenderable extends IRenderable {

	boolean canRemove();

}
