package jaminv.advancedmachines.init;

import jaminv.advancedmachines.util.recipe.AlloyManager;
import jaminv.advancedmachines.util.recipe.FurnaceManager;
import jaminv.advancedmachines.util.recipe.grinder.GrinderManager;
import jaminv.advancedmachines.util.recipe.melter.MelterManager;
import jaminv.advancedmachines.util.recipe.purifier.PurifierManager;
import jaminv.advancedmachines.util.recipe.stabilizer.StabilizerManager;
import scala.tools.nsc.ast.parser.Parsers.Parser;

public class RecipeInit {

	public static void init() {
		FurnaceManager.init();
		PurifierManager.init();
		GrinderManager.init();
		AlloyManager.init();
		MelterManager.init();
		StabilizerManager.init();
	}
}
