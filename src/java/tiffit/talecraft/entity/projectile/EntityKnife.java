package tiffit.talecraft.entity.projectile;

import de.longor.talecraft.TaleCraftItems;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import tiffit.talecraft.client.render.RenderKnife;
import tiffit.talecraft.util.BombExplosion;

public class EntityKnife extends EntityArrow{
	
    public EntityKnife(World world){
        super(world);
    	setDamage(7D);
    }

    public EntityKnife(World world, EntityLivingBase thrower){
        super(world, thrower);
    	setDamage(7D);
    }

    public EntityKnife(World world, double x, double y, double z){
        super(world, x, y, z);
    	setDamage(7D);
    }

    @Override
    protected void entityInit() {
    	super.entityInit();
    }
    
    @Override
    public boolean getIsCritical() {
    	return false;
    }
    
    @Override
    public double getDamage() {
    	return 7D;
    }
    
    @Override
    protected ItemStack getArrowStack() {
    	return new ItemStack(TaleCraftItems.knife);
    }
    
    public void setHeadingFromThrower(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy){
        float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
        float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        this.setThrowableHeading((double)f, (double)f1, (double)f2, velocity, inaccuracy);
        this.motionX += entityThrower.motionX;
        this.motionZ += entityThrower.motionZ;

        if (!entityThrower.onGround)
        {
            this.motionY += entityThrower.motionY;
        }
    }
	
	public static class EntityKnifeRenderFactory implements IRenderFactory{
		@Override
		public Render createRenderFor(RenderManager manager) {
			Minecraft mc = Minecraft.getMinecraft();
			return new RenderKnife(manager);
		}
	}


}