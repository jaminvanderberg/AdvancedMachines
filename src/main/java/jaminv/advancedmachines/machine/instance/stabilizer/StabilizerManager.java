package jaminv.advancedmachines.machine.instance.stabilizer;

import java.util.List;

import jaminv.advancedmachines.ModConfig;
import jaminv.advancedmachines.ModReference;
import jaminv.advancedmachines.lib.parser.DataParser;
import jaminv.advancedmachines.lib.parser.FileHandler;
import jaminv.advancedmachines.lib.parser.FileHandlerRecipe;
import jaminv.advancedmachines.lib.parser.FileHandlerRecipe.IngredientType;
import jaminv.advancedmachines.lib.parser.FileHandlerRecipe.RecipeSection;
import jaminv.advancedmachines.lib.recipe.MachineRecipe;
import jaminv.advancedmachines.lib.recipe.MachineRecipeManager;
import jaminv.advancedmachines.lib.recipe.RecipeManager;

public class StabilizerManager {
	protected static MachineRecipeManager<MachineRecipe> manager = new MachineRecipeManager<>();
	
	public static RecipeManager getRecipeManager() { return manager; }
	public static List<MachineRecipe> getRecipeList() { return manager.getRecipeList(); }

	public static void init() {
		FileHandler handler = new FileHandlerRecipe("stabilizer", ModConfig.general.defaultStabilizerEnergyCost, (recipe) -> {
			if (ModConfig.doExclude("stabilizer", recipe.getRecipeId())) {	return;	}
			manager.addRecipe(recipe);
		}).setLimit(RecipeSection.INPUT, IngredientType.FLUID, 1).setLimit(RecipeSection.OUTPUT, IngredientType.ITEM, 1);
		
		DataParser.parseJarFolder(ModReference.MODID, "data/recipes/stabilizer", handler);
		DataParser.parseConfigFolder(ModReference.MODID, "data/stabilizer", handler);
	}
}
