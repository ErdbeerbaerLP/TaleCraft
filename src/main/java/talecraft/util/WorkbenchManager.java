package talecraft.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public class WorkbenchManager extends ArrayList<IRecipe> {

	private static final long serialVersionUID = -7624110812743587186L;

	public NBTTagCompound toNBT(){
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInt("size", size());
		for(int i = 0; i < size(); i++){
			tag.setTag("recipe_" + i, recipeToNBT(get(i)));
		}
		return tag;
	}
	
	public static WorkbenchManager fromNBT(NBTTagCompound tag){
		WorkbenchManager wrk = new WorkbenchManager();
		for(int i = 0; i < tag.getInt("size"); i++){
			wrk.add(recipeFromNBT(tag.getCompound("recipe_" + i)));
		}
		return wrk;
	}
	
	public static NBTTagCompound recipeToNBT(IRecipe recipe){
		if(recipe instanceof ShapedRecipe){
			return shapedToNBT((ShapedRecipe) recipe);
		}else{
			return new NBTTagCompound();
		}
	}
	
	public static IRecipe recipeFromNBT(NBTTagCompound tag){
		if(tag.getString("type").equals("shaped")){
			return shapedFromNBT(tag);
		}else{
			return null;
		}
	}
	
	private static NBTTagCompound shapedToNBT(ShapedRecipe shaped){
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("type", "shaped");
		tag.setInt("width", shaped.getRecipeWidth());
		tag.setInt("height", shaped.getRecipeHeight());
		for(int i = 0; i < 9; i++){
			ItemStack[] stacks = shaped.getIngredients().get(i).getMatchingStacks();
			NBTTagCompound stackTag = new NBTTagCompound();
			for(ItemStack stack : stacks) {
				if(!stack.isEmpty()){
					stack.write(stackTag);
				}
			}
			tag.setTag("item_" + i, stackTag);
		}
		tag.setTag("output", shaped.getRecipeOutput().write(new NBTTagCompound()));
		return tag;
	}
	
	private static ShapedRecipe shapedFromNBT(NBTTagCompound tag){
		int width = tag.getInt("width");
		int height = tag.getInt("height");
		NonNullList<Ingredient> ingredients = NonNullList.create();
		for(int i = 0; i < 9; i++){
			NBTTagCompound stackTag = tag.getCompound("item_" + i);
			if(stackTag.isEmpty()){
				ingredients.add(Ingredient.fromStacks(ItemStack.EMPTY));
				continue;
			}else{
				try{
					ingredients.add(Ingredient.fromStacks(ItemStack.class.getConstructor(NBTTagCompound.class).newInstance(stackTag)));
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		ItemStack output = null;
		try {
			output = ItemStack.class.getConstructor(NBTTagCompound.class).newInstance(tag.getCompound("output"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ShapedRecipe(null, "TC", width, height, ingredients, output);
	}
	
	public static WorkbenchManager getInstance(){
//		return WorkbenchBlock.recipes;
		return null;
	}
	
}
