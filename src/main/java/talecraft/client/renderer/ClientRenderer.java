package talecraft.client.renderer;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import talecraft.blocks.tileentity.TileEntityBarrier;
import talecraft.render.tileentity.GenericTileEntityRenderer;

public class ClientRenderer {

	public static void regsiterAll() {
		// TODO Auto-generated method stub
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBarrier.class,
				new GenericTileEntityRenderer<TileEntityBarrier>("minecraft:textures/item/barrier.png"));

	}

}
