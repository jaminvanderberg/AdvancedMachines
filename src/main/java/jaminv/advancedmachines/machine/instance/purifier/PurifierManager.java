package jaminv.advancedmachines.machine.instance.purifier;

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

public class PurifierManager {
	protected static MachineRecipeManager<MachineRecipe> manager = new MachineRecipeManager<>();
	
	public static RecipeManager getRecipeManager() { return manager; }
	public static List<MachineRecipe> getRecipeList() { return manager.getRecipeList(); }

	public static void init() {
		FileHandler handler = new FileHandlerRecipe("purifier", ModConfig.general.defaultPurifierEnergyCost, (recipe) -> {
			if (ModConfig.doExclude("purifier", recipe.getRecipeId())) {	return;	}
			manager.addRecipe(recipe);
		}).setLimit(RecipeSection.INPUT, IngredientType.ITEM, 1).setLimit(RecipeSection.OUTPUT, IngredientType.ITEM, 1)
			.setLimit(RecipeSection.SECONDARY, IngredientType.ITEM, 6);
		
		DataParser.parseJarFolder(ModReference.MODID, "data/recipes/purifier", handler);
		DataParser.parseConfigFolder(ModReference.MODID, "data/purifier", handler);
	}
}
