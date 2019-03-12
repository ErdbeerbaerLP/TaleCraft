package talecraft.client.renderer;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import talecraft.TaleCraft;
import talecraft.blocks.tileentity.CollisionTriggerBlockTileEntity;
import talecraft.blocks.tileentity.LightBlockTE;
import talecraft.blocks.tileentity.TileEntityBarrier;
import talecraft.render.tileentity.GenericTileEntityRenderer;

public class ClientRenderer {

	public static void registerAll() {
		// TODO Auto-generated method stub
		TaleCraft.logger.info("Registering Renderers");
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBarrier.class,
				new GenericTileEntityRenderer<TileEntityBarrier>("minecraft:textures/item/barrier.png"));
		
		ClientRegistry.bindTileEntitySpecialRenderer(LightBlockTE.class,
				new GenericTileEntityRenderer<LightBlockTE>(TaleCraft.MOD_ID+":textures/blocks/util/light.png"));
		
		ClientRegistry.bindTileEntitySpecialRenderer(CollisionTriggerBlockTileEntity.class,
				new GenericTileEntityRenderer<CollisionTriggerBlockTileEntity>(TaleCraft.MOD_ID+":textures/blocks/util/trigger.png"));

	}

}
