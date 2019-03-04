package talecraft.client.gui.replaced_guis;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.gui.GuiModList;
import talecraft.TaleCraft;
/**
 * This is the custom main menu for talecraft<br>
 * After it is complete it will also allow you to create/load saves (semilar to AdventureCraft)
 * @author ErdbeerbaerLP
 */
public class CustomMainMenu extends GuiScreen {
	//GuiMainMenu
	private static final ResourceLocation TALECRAFT_TITLE_TEXTURES = new ResourceLocation("talecraft:textures/talecraft-titlelogo.png");
	private GuiButtonExt modButton;
	private GuiButtonExt createMapButton;
	private GuiButtonExt loadMapButton;
	private GuiButtonExt downloadMapButton;
	private GuiButtonExt exitGameButton;
	private GuiButtonExt settingsButton;
	private static final ResourceLocation[] TITLE_PANORAMA_PATHS = new ResourceLocation[] {new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
	/** Timer used to rotate the panorama, increases every tick. */

	/** Texture allocated for the current viewport of the main menu's panorama background. */
	private DynamicTexture viewportTexture;
	private final RenderSkybox panorama = new RenderSkybox(new RenderSkyboxCube(new ResourceLocation("textures/gui/title/background/panorama")));

	public CustomMainMenu() {

		this.viewportTexture = new DynamicTexture(256, 256, false);
		TaleCraft.lastVisitedWorld = null;
	}

	//GuiMainMenu
	@Override
	public void initGui() {
		TaleCraft.setPresence("In Main Menu", "talecraft");
		File savesDir = new File(this.mc.gameDir, "saves");
		final String[] savesList = savesDir.list();
		for(String s : savesList){
			if(s.startsWith(".TC")) continue;
			boolean type = !s.contains("@SAV");
//			mc.displayGuiScreen(new CrashQuestion());
			return;
		}
		int j = this.height / 4 + 48;
		this.addButton(createMapButton = new GuiButtonExt(1, this.width / 2 - 100, j, "Create new Map") {
			@Override
			public void onClick(double mouseX, double mouseY) {
//				mc.displayGuiScreen(new MapSelector(CustomMainMenu.this));
			}
		});
		this.addButton(loadMapButton = new GuiButtonExt(2, this.width / 2 - 100, j + 24* 1, "Play Map") {
			@Override
			public void onClick(double mouseX, double mouseY) {
//				mc.displayGuiScreen(new SaveSelector(CustomMainMenu.this));
			}
		});
		this.addButton(downloadMapButton = new GuiButtonExt(3, this.width / 2, j + 24* 2,100,20, "Download Maps") {
			@Override
			public void onClick(double mouseX, double mouseY) {
				// TODO Auto-generated method stub
//				mc.displayGuiScreen(new GuiMapList(CustomMainMenu.this));
			}
		});
		this.addButton(exitGameButton = new GuiButtonExt(4, this.width / 2, j + 24 * 3,100,20, I18n.format("menu.quit")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				// TODO Auto-generated method stub
				mc.shutdown();
			}
		});
		this.addButton(settingsButton = new GuiButtonExt(5, this.width / 2 - 100, j + 24 * 3,100,20, I18n.format("menu.options")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				// TODO Auto-generated method stub
				mc.displayGuiScreen(new GuiOptions(CustomMainMenu.this, mc.gameSettings));
			}
		});
		this.addButton(modButton = new GuiButtonExt(6, this.width / 2 - 100, j + 24 * 2, 100,20,I18n.format("fml.menu.mods")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				// TODO Auto-generated method stub
				mc.displayGuiScreen(new GuiModList(CustomMainMenu.this));
			}
		});
		//		downloadMapButton.enabled = false;
	}
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		this.panorama.render(partialTicks);
		int i = 274;
		int j = this.width / 2 - 120;
		int k = 30;
		this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/title/background/panorama_overlay.png"));
		drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, 16, 128, this.width, this.height, 16.0F, 128.0F);
		this.mc.getTextureManager().bindTexture(TALECRAFT_TITLE_TEXTURES);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawModalRectWithCustomSizedTexture(j + 17, 30,0.0F, 0.0F, 0, 0, 212, 48);
		super.render(mouseX, mouseY, partialTicks);
	}

/*

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		// TODO Auto-generated method stub
		super.actionPerformed(button);
		switch(button.id) {
		case 1:
			
			break;
		case 2:
			
			break;
		case 3:
			//			Test.test();
			Minecraft.getInstance().displayGuiScreen(new GuiMapList(this));
			break;
		case 4:
			this.mc.shutdown();
			break;
		case 5:
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
			break;
		case 6:
			this.mc.displayGuiScreen(new net.minecraftforge.fml.client.GuiModList(this));
			break;
		}
	}
*/

}
