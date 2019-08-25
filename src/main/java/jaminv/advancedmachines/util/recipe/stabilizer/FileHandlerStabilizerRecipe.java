package jaminv.advancedmachines.util.recipe.stabilizer;

import com.google.gson.JsonObject;

import jaminv.advancedmachines.lib.recipe.RecipeInput;
import jaminv.advancedmachines.lib.recipe.RecipeOutput;
import jaminv.advancedmachines.util.ModConfig;
import jaminv.advancedmachines.util.logger.Logger;
import jaminv.advancedmachines.util.parser.DataParserException;
import jaminv.advancedmachines.util.parser.FileHandlerRecipe;
import jaminv.advancedmachines.util.recipe.stabilizer.StabilizerManager.StabilizerRecipe;

public class FileHandlerStabilizerRecipe extends FileHandlerRecipe {

	@Override
	protected boolean parseRecipe(Logger logger, String filename, String path, JsonObject recipe) throws DataParserException {
		logger = logger.getLogger("stabilizer");		
		logger.info("Parsing recipe '" + path + "'.");
		
		RecipeInput input = new RecipeInput(parseFluidStack(recipe.get("input"), "input"));
		RecipeOutput output = parseOutput(recipe.get("output"), "output");
		int energy = getEnergy(recipe, ModConfig.general.defaultGrinderEnergyCost);
		
		if (input == null || input.isEmpty() || output == null || output.isEmpty()) { return false; }
		if (!checkConditions(recipe, "conditions", logger)) { return false; }
		
		StabilizerRecipe rec = new StabilizerRecipe(filename + "." + path, energy);
		rec.setInput(input).setOutput(output);

		StabilizerManager.getRecipeManager().addRecipe(rec);		
		
		return true; 
	}

}