package talecraft.client.render.metaworld;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderPainting;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceFluidMode;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.registry.IRegistry;

public class CustomPaintingRender implements IMetadataRender{

	private final RenderPainting RENDER;
	private final Minecraft mc;
	
	public CustomPaintingRender() {
		mc = Minecraft.getInstance();
		RENDER = new RenderPainting(mc.getRenderManager());
	}
	private PaintingType getArt(String key) {
		return IRegistry.field_212620_i.get(new ResourceLocation("minecraft:"+key));
	}
	@Override
	public void render(Item item, ItemStack stack, Tessellator tessellator, BufferBuilder buffer, double partialTick, BlockPos playerPos, EntityPlayerSP player, WorldClient world) {
		PaintingType painting;
		try{
			painting = getArt(stack.getTag().getString("art"));
		}catch (Exception e) {
			painting = PaintingType.BOMB;
		}
		RayTraceResult result = player.rayTrace(5, (float)partialTick, RayTraceFluidMode.NEVER);
		if(result.type == Type.BLOCK){
			BlockPos pos = result.getBlockPos();
			EnumFacing facing = result.sideHit;
			if(!facing.getAxis().isHorizontal())return;
			EntityPainting ent = new EntityPainting(world, pos.offset(facing.getOpposite(), 1), facing);
			ent.art = painting;
			GlStateManager.enableBlend();
			GL11.glColor4f(1f, 1f, 1f, 0.5f);
			double xMove = 0;
			if(facing.getXOffset() == 0)xMove = (painting.getHeight() != 32 ?  painting.getHeight() >= 48 ? -painting.getHeight()/16/8 : 0.5 : 0);
			double zMove = 0;
			if(facing.getZOffset() == 0)zMove = (painting.getHeight() != 32 ?  painting.getHeight() >= 48 ? -painting.getHeight()/16/8 : 0.5 : 0);
			if(painting.getHeight() == 16){
				if(facing.getOpposite() == EnumFacing.EAST)zMove -= 1;
				if(facing.getOpposite() == EnumFacing.NORTH)xMove -= 1;
			}
			double yMove = (painting.getWidth() == 16  || painting.getWidth() == 16*3 ? 0 : 0.5);
			RENDER.doRender(ent, ent.posX + facing.getXOffset()*2 + xMove, ent.posY + yMove, ent.posZ + facing.getZOffset()*2 + zMove, ent.rotationYaw, (float)partialTick);
		}
	}

}
