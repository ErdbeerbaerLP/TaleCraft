package talecraft.client.gui.blocks;

import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.math.BlockPos;
import talecraft.blocks.tileentity.CollisionTriggerBlockTileEntity;
import talecraft.client.gui.GuiColors;
import talecraft.client.gui.TCGuiScreen;
import talecraft.client.gui.invoke.BlockInvokeHolder;
import talecraft.client.gui.invoke.InvokePanelBuilder;

public class GuiCollisionTriggerBlock extends TCGuiScreen {
	CollisionTriggerBlockTileEntity tileEntity;
	final BlockPos pos;
	public GuiCollisionTriggerBlock(CollisionTriggerBlockTileEntity tileEntity) {
		pos = tileEntity.getPos();
		this.tileEntity = tileEntity;
	}
	@Override
	public void initGui() {
		
		InvokePanelBuilder.build(this, 2, 16, tileEntity.getCollisionStartInvoke(), new BlockInvokeHolder(pos, "collisionStartTrigger"), InvokePanelBuilder.INVOKE_TYPE_EDIT_ALLOWALL);
		InvokePanelBuilder.build(this, 2, 16+2+20, tileEntity.getCollisionStopInvoke(), new BlockInvokeHolder(pos, "collisionStopTrigger"), InvokePanelBuilder.INVOKE_TYPE_EDIT_ALLOWALL);

		// TODO: Add entityFilter input+apply

	}
@Override
public void render(int mouseX, int mouseY, float partialTicks) {
	// TODO Auto-generated method stub
	super.render(mouseX, mouseY, partialTicks);
	drawString(this.fontRenderer, "Collision Trigger @ " + pos.getX() + " " + pos.getY() + " " + pos.getZ(), 2, 2, GuiColors.WHITE);
}


}
