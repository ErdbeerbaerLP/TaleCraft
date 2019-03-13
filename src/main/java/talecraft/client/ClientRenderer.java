package talecraft.client;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import talecraft.TaleCraft;
import talecraft.client.environment.Environments;
import talecraft.client.render.IRenderable;
import talecraft.client.render.ITemporaryRenderable;
import talecraft.client.render.RenderModeHelper;
import talecraft.client.render.renderables.SelectionBoxRenderer;
import talecraft.client.render.renderers.CustomSkyRenderer;
import talecraft.client.render.renderers.ItemMetaWorldRenderer;

@EventBusSubscriber
public class ClientRenderer implements IResourceManagerReloadListener{
	private final ClientProxy proxy;
	private final Minecraft mc;

	private VisualMode visualizationMode;
	private float partialTicks;
	
	public static enum VisualMode{
		Default("default"),
		Lighting("lighting"),
		Nightvision("nightvision"),
		Wireframe("wireframe");
		String name;
		
		VisualMode(String n) {
			name = n;
		}
		
		public String getName() {
			return name;
		}
		
		public VisualMode next(){
			int current = ordinal();
			current++;
			if(current >= values().length) current = 0;
			Minecraft.getInstance().player.getActivePotionEffects().clear();
			return values()[current];
		}
	}

	private final ConcurrentLinkedDeque<ITemporaryRenderable> temporaryRenderers;
	private final ConcurrentLinkedDeque<IRenderable> staticRenderers;

	public ClientRenderer(ClientProxy clientProxy) {
		proxy = clientProxy;
		mc = Minecraft.getInstance();

		visualizationMode = VisualMode.Default;
		partialTicks = 1f;

		temporaryRenderers = new ConcurrentLinkedDeque<>();
		staticRenderers = new ConcurrentLinkedDeque<>();
	}

	public void preInit() {
		// TODO Auto-generated method stub

	}

	public void addStaticRenderer(SelectionBoxRenderer selectionBoxRenderer) {
		staticRenderers.offer(selectionBoxRenderer);
	}

	/****/
	public void addTemporaryRenderer(ITemporaryRenderable renderable) {
		temporaryRenderers.offer(renderable);
	}

	public void clearTemporaryRenderers() {
		temporaryRenderers.clear();
	}

	public VisualMode getVisualizationMode() {
		return visualizationMode;
	}

	/****/
	public void setVisualizationMode(VisualMode mode) {
		visualizationMode = mode;
	}

	/****/
	public int getTemporablesCount() {
		return temporaryRenderers.size();
	}

	/****/
	public int getStaticCount() {
		return staticRenderers.size();
	}

	/****/
	public float getLastPartialTicks() {
		return partialTicks;
	}




	// some empty space here





	public void on_render_world_post(RenderWorldLastEvent event) {
		RenderModeHelper.DISABLE();
		partialTicks = event.getPartialTicks();

		// Iterate trough all ITemporaryRenderables and remove the ones that can be removed.
		Iterator<ITemporaryRenderable> iterator = temporaryRenderers.iterator();
		while(iterator.hasNext()) {
			ITemporaryRenderable itr = iterator.next();
			if(itr.canRemove()) {
				iterator.remove();
			}
		}

		// If the world and the player exist, call the worldPostRender-method.
		if(mc.world != null && mc.player != null) {
			GlStateManager.pushMatrix();

			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder vertexbuffer = tessellator.getBuffer();

			on_render_world_post_sub(partialTicks, tessellator, vertexbuffer);

			GlStateManager.popMatrix();
		}

		// Enable textures again, since the GUI-prerender doesn't enable it again by itself.
		GlStateManager.enableTexture2D();
	}

	private void on_render_world_post_sub(double partialTicks, Tessellator tessellator, BufferBuilder vertexbuffer) {

		// Translate into World-Space
		double px = mc.player.prevPosX + (mc.player.posX - mc.player.prevPosX) * partialTicks;
		double py = mc.player.prevPosY + (mc.player.posY - mc.player.prevPosY) * partialTicks;
		double pz = mc.player.prevPosZ + (mc.player.posZ - mc.player.prevPosZ) * partialTicks;
		GL11.glTranslated(-px, -py, -pz);

		GlStateManager.disableCull();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.color4f(1, 1, 1, 1);
		RenderHelper.enableStandardItemLighting();

		// Render all the renderables
		for(IRenderable renderable : staticRenderers) {
			renderable.render(mc, tessellator, vertexbuffer, partialTicks);
		}

		GlStateManager.disableCull();
		GlStateManager.enableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.color4f(1, 1, 1, 1);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableStandardItemLighting();

		// Render all the temporary renderables
		for(ITemporaryRenderable renderable : temporaryRenderers) {
			renderable.render(mc, tessellator, vertexbuffer, partialTicks);
		}

		GlStateManager.disableCull();
		GlStateManager.enableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.color4f(1, 1, 1, 1);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableStandardItemLighting();

		// Render Item Meta Renderables
		if(mc.player != null && mc.player.getHeldItemMainhand() != null) {
			ItemStack stack = mc.player.getHeldItemMainhand();
			Item item = stack.getItem();

			ItemMetaWorldRenderer.tessellator = tessellator;
			ItemMetaWorldRenderer.vertexbuffer = vertexbuffer;
			ItemMetaWorldRenderer.partialTicks = partialTicks;
			ItemMetaWorldRenderer.partialTicksF = (float) partialTicks;
			ItemMetaWorldRenderer.clientProxy = proxy;
			ItemMetaWorldRenderer.world = mc.world;
			ItemMetaWorldRenderer.player = mc.player;
			ItemMetaWorldRenderer.playerPosition = new BlockPos(px, py, pz);
			ItemMetaWorldRenderer.render(item, stack);
		}

		if(mc.player != null && mc.player.getHeldItemOffhand() != null) {
			ItemStack stack = mc.player.getHeldItemOffhand();
			Item item = stack.getItem();

			ItemMetaWorldRenderer.tessellator = tessellator;
			ItemMetaWorldRenderer.vertexbuffer = vertexbuffer;
			ItemMetaWorldRenderer.partialTicks = partialTicks;
			ItemMetaWorldRenderer.partialTicksF = (float) partialTicks;
			ItemMetaWorldRenderer.clientProxy = proxy;
			ItemMetaWorldRenderer.world = mc.world;
			ItemMetaWorldRenderer.player = mc.player;
			ItemMetaWorldRenderer.playerPosition = new BlockPos(px, py, pz);
			ItemMetaWorldRenderer.render(item, stack);
		}

		GlStateManager.enableCull();;
		GlStateManager.enableLighting();
		GlStateManager.enableTexture2D();
		GlStateManager.color4f(1, 1, 1, 1);
		RenderHelper.enableStandardItemLighting();
	}

	public void on_world_unload() {
		temporaryRenderers.clear();
		visualizationMode = VisualMode.Default;
	}
	
	public void on_render_world_terrain_pre(RenderTickEvent revt) {
		if(mc.world != null) {
			// Which VisualMode should we use?
			VisualMode visMode = visualizationMode;
			
			// Prevent non-creative players from using the visualization modes.
			if(!TaleCraft.isBuildMode()) {
				visMode = VisualMode.Default;
			}
			
			// this takes care of the CUSTOM SKY RENDERING
			if(mc.world.getDimension() != null) {
				boolean debugSkyActive = visMode != VisualMode.Default;
				
				if(debugSkyActive) {
					CustomSkyRenderer.instance.setDebugSky(true);
					mc.world.getDimension().setSkyRenderer(CustomSkyRenderer.instance);
				} else if(Environments.isNonDefault()) {
					CustomSkyRenderer.instance.setDebugSky(false);
					mc.world.getDimension().setSkyRenderer(CustomSkyRenderer.instance);
				} else {
					CustomSkyRenderer.instance.setDebugSky(false);
					mc.world.getDimension().setSkyRenderer(null);
				}
			}
			
			// handle currently active VisualMode
			if(mc.player != null) {
				RenderModeHelper.ENABLE(visMode);
			}
		}
	}

	public void on_render_world_terrain_post(RenderTickEvent revt) {
		if(mc.ingameGUI != null && mc.world != null) {
			if(proxy.getInfoBar().canDisplayInfoBar(mc)) {
				proxy.getInfoBar().display(mc, mc.world);
				// XXX: Move this to its own IF
				proxy.getInvokeTracker().display(mc, proxy);
			}
		}
	}

	public static ClientFadeEffect fadeEffect = null;
	
	public void on_render_world_hand_post(RenderHandEvent event) {
		if(fadeEffect != null && mc.ingameGUI != null){
			fadeEffect.render();

			// Do NOT draw the hand!
			event.setCanceled(true);
		}
		
		GlStateManager.enableTexture2D();
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		Environments.reload(resourceManager);
		
	}



}
