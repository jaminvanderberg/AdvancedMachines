package jaminv.advancedmachines.machine.instance.press;

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

public class PressManager {
	protected static MachineRecipeManager<MachineRecipe> manager = new MachineRecipeManager<>();
	
	public static RecipeManager getRecipeManager() { return manager; }
	public static List<MachineRecipe> getRecipeList() { return manager.getRecipeList(); }

	public static void init() {
		FileHandler handler = new FileHandlerRecipe("press", ModConfig.general.defaultPressEnergyCost, (recipe) -> {
			if (ModConfig.doExclude("press", recipe.getRecipeId())) {	return;	}
			manager.addRecipe(recipe);
		}).setLimit(RecipeSection.INPUT, IngredientType.ITEM, 3).setLimit(RecipeSection.CATALYST, IngredientType.ITEM, 2)
			.setLimit(RecipeSection.OUTPUT, IngredientType.ITEM, 1);
		
		DataParser.parseJarFolder(ModReference.MODID, "data/recipes/press", handler);
		DataParser.parseConfigFolder(ModReference.MODID, "data/press", handler);
	}
}
