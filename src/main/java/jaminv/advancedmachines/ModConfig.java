package jaminv.advancedmachines;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import jaminv.advancedmachines.lib.recipe.RecipeOutput;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = ModReference.MODID, name = "advancedmachines/advancedmachines")
@Config.LangKey("advmach.config.title")
public final class ModConfig {
	
	protected static Map<String, HashSet<String>> exclude = null;
	
	public static boolean doExclude(String type, String recipe_id) {
		if (exclude == null) { loadExcludes(); }
		HashSet typeset = exclude.get(type);
		if (typeset == null) { return false; }
		return typeset.contains(recipe_id);
	}
	
	public static void loadExcludes() {
		exclude = new HashMap<>();
		for (String entry : recipe.excludeRecipes) {
			String[] data = entry.split(":", 2);
			if (data.length != 2) { continue; }
			HashSet typeset = exclude.get(data[0]);
			if (typeset == null) {
				typeset = new HashSet<String>();
				typeset.add(data[1]);
				exclude.put(data[0], typeset);
			} else {
				typeset.add(data[1]);
			}
		}		
	}
	
	@Config.Comment("General Configuration")
	@Config.RequiresMcRestart
	public static General general = new General();
	public static class General {
		@Config.Comment("Machines only run full updates every X ticks")
		@RangeInt(min = 1, max = 100)
		public int tickUpdate = 5;
		
		@Config.Comment("Default machine energy capacity for lowest tier")
		@RangeInt(min = 0, max = 1000000)
		public int defaultMachineEnergyCapacity = 50000;
		
		@Config.Comment("Default machine energy transfer rate for lowest tier")
		@RangeInt(min = 0, max = 50000)
		public int defaultMachineEnergyTransfer = 250;
		
		@Config.Comment("Default machine fluid capacity for lowest tier")
		@RangeInt(min = 0, max = 128000)
		public int defaultMachineFluidCapacity = 16000;
		
		@Config.Comment("Default machine fluid transfer rate for lowest tier")
		@RangeInt(min = 0, max = 50000)
		public int defaultMachineFluidTransfer = 1000;
		
		@Config.Comment("Processing time for machines with no upgrages")
		@RangeInt(min = 1, max=6000)
		public int processTimeBasic = 100;
		
		@Config.Comment("Default energy cost for standard furnace recipes")
		@RangeInt(min = 0, max = 1000000)
		public int defaultFurnaceEnergyCost = 2000;
		
		@Config.Comment("Default energy cost for standard grinder recipes")
		@RangeInt(min = 0, max = 1000000)
		public int defaultGrinderEnergyCost = 4000;
		
		@Config.Comment("Default energy cost for standard purifier recipes")
		@RangeInt(min = 0, max = 1000000)
		public int defaultPurifierEnergyCost = 30000;
		
		@Config.Comment("Default energy cost for standard alloy furnace recipes")
		@RangeInt(min = 0, max = 1000000)
		public int defaultAlloyEnergyCost = 4000;
		
		@Config.Comment("Default energy cost for standard metler recipes")
		@RangeInt(min = 0, max = 1000000)
		public int defaultMelterEnergyCost = 4000;
		
		@Config.Comment("Default energy cost for standard fluid stabilizer recipes")
		@RangeInt(min = 0, max = 1000000)
		public int defaultStabilizerEnergyCost = 4000;
		
		@Config.Comment("Default energy cost for standard fluid injector recipes")
		@RangeInt(min = 0, max = 1000000)
		public int defaultInjectorEnergyCost = 10000;
		
		@Config.Comment("Default energy cost for circuit press recipes")
		@RangeInt(min = 0, max = 1000000)
		public int defaultPressEnergyCost = 50000;
	}
	
	@Config.Comment({ "Crafting Configuration", "Allows disabling of some common crafting recipes that may conflict with other mods." })
	@Config.RequiresMcRestart
	public static Crafting crafting = new Crafting();
	public static class Crafting {
		@Config.Comment("Include crafting recipe for 'Iron Gear'.")
		public boolean craftIronGear = true;
		
		@Config.Comment("Include crafting recipe for 'Titanium Gear'.")
		public boolean craftTitaniumGear = true;
		
		@Config.Comment("Include crafting recipes for titanium blocks.")
		public boolean craftTitaniumBlock = true;
		
		@Config.Comment("Include crafting recipe for copper blocks.")
		public boolean craftCopperBlock = true;
		
		@Config.Comment("Include crafting recipe for silver blocks.")
		public boolean craftSilverBlock = true;
		
		@Config.Comment("Include crafting recipes for titanium tools.")
		public boolean craftTitaniumTools = true;
		
		@Config.Comment("Include crafting recipes for titanium armor.")
		public boolean craftTitaniumArmor = true;
		
		@Config.Comment("Include crafting recipes for copper tools.")
		public boolean craftCopperTools = true;
		
		@Config.Comment("Include crafting recipes for copper armor.")
		public boolean craftCopperArmor = true;
		
		@Config.Comment("Include crafting recipes for silver tools.")
		public boolean craftSilverTools = true;
		
		@Config.Comment("Include crafting recipes for silver armor.")
		public boolean craftSilverArmor = true;		
	}
	
	// FIXME: Not picking Thermal Foundation tar and rosin for recipe outputs
	@Config.Comment({ "Recipe Configuration" })
	public static Recipe recipe = new Recipe();
	public static class Recipe {
		// TODO: ModTweaker support
		
		@Config.Comment("Enable to show recipe ids in JEI. This can be used to find ids for disabling recipes.")
		public boolean showRecipeIds = false;
		
		@Config.Comment({ 
			"Scan the ore dictionary for all ore/dust/gem/ingot items and create appropriate grinder recipes for them.",
			"This helps provide the widest cross-mod capabilities even for unknown mods."
		})
		public boolean scanGrinderOre = true;
		
		@Config.Comment({
			"Exclude specific recipes by recipe id. Format is [type]:[recipe_id]",
			"Recipe IDs can be found by setting 'showRecipeIds' to true and uing JEI.",
			"Types are: furnace, grinder, alloy, purifier, press, melter, stabilizer, injector."
		})
		public String[] excludeRecipes = {};
		
		@Config.Comment({
			"Order of preference for ore dictionary outputs. Can be used for consistant machine outputs across mods."
		})
		public String[] oreDictionaryPreference = { "minecraft", "thermalfoundation", "appliedenergistics2", "advancedmachines" };
		
		@Config.Comment("Enable Applied Energistics 2 recipes for the Circuit Press")
		public boolean allowPressAE2 = true; 
		
		/*
		for (String item : excludeGrinderRecipes) {
			doExcludeGrinder.put(item, true);
		}*/			
	}
	
	@Config.Comment("Tool Configuration")
	@Config.RequiresMcRestart
	public static Tool tool = new Tool();
	public static class Tool {

	}
	
	@Config.Comment({ "World Gen Configution", "",
		"Disabling ore generation for a material may prevent certain aspects of the mod from working",
		"unless another mod provides ore generation for an ore dictionary equivalent to that material."		
	})
	public static WorldGen worldgen = new WorldGen();
	public static class WorldGen {
		@Config.Comment("Set to false to prevent Titanium Ore generation in the world")
		public boolean doGenerateTitanium = true;
				
		@Config.Comment("Maximum number of Titanium Ore in a vein")
		@RangeInt(min = 0, max = 64)
		public int titaniumVeinSize = 4;
		
		@Config.Comment("Bottom Y-level for Titanium Ore generation")
		@RangeInt(min = 0, max = 256)
		public int titaniumMinHeight = 0;
		
		@Config.Comment("Top Y-level for Titanium Ore generation")
		public int titaniumMaxHeight = 24;
		
		@Config.Comment("Number of times per chunk that it will attempt to generate Titanium Ore")
		public int titaniumChance = 2; 
	}
	
	@Mod.EventBusSubscriber(modid = ModReference.MODID)
	private static class EventHandler {
		
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(ModReference.MODID)) {
				ConfigManager.sync(ModReference.MODID, Config.Type.INSTANCE);
				
				RecipeOutput.setOreDictionaryPreference(ModConfig.recipe.oreDictionaryPreference);
			}
		}
	}
	
/*	
	private static void initDependentConfigs() {
		if (!doInclude("material_titanium")) {
			doGenerateTitanium = false;
		}
		if (!doInclude("material_copper")) {
			doGenerateCopper = false;
		}
	}
	

	
	public static boolean doGenerate(String material) {
		switch (material) {
		case "titanium": return doGenerateTitanium;
		case "copper": return doGenerateCopper;
		}
		return false;
	}

	public static boolean doAddTools(String material) {
		switch (material) {
		case "titanium": return doAddTitaniumTools;
		case "copper": return doAddCopperTools;
		}
		return false;
	}
	
	public static boolean doAddArmor(String material) {
		switch (material) {
		case "titanium": return doAddTitaniumArmor;
		case "copper": return doAddCopperArmor;
		}
		return false;
	} */
}
