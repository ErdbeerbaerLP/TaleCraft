package talecraft.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

public interface IRenderable {

	void render(Minecraft mc, Tessellator tessellator, BufferBuilder vertexbuffer, double partialTicks);

}
