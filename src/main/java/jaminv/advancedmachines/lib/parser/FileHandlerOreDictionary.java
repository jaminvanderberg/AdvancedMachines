package jaminv.advancedmachines.lib.parser;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import jaminv.advancedmachines.lib.util.logger.Logger;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.oredict.OreDictionary;

public class FileHandlerOreDictionary implements FileHandler {

	@Override
	public boolean parseData(Logger logger, String filename, JsonObject json) throws DataParserException {
		logger = logger.getLogger("ore_dictionary");
		
		int i = 0, c = 0;
		for (Map.Entry<String,JsonElement> entry : json.entrySet()) {
			String name = entry.getKey();
			JsonArray list = JsonUtils.getJsonArray(entry.getValue(), entry.getKey());
			
			int j = 0;
			for (JsonElement ore : list) {
				try {
					if (parseOre(JsonUtils.getJsonObject(ore, entry.getKey() + "[" + j + "]"))) { c++; }
				} catch(DataParserException | JsonSyntaxException e) {
					logger.error(e.getMessage());
				}
				i++; j++;
			}
		}
		
		ParseUtils.logComplete(logger, c, i, "%d ore dictionary entries created successfully.", "%d ore dictionary entries not created.");
			
		return true;
	}
	
	protected boolean parseOre(JsonObject ore) throws DataParserException {
		ItemStack item = ParseUtils.parseItemStack(ore.get("item"), "item");
		if (item == null) { return false; }
		String orename = JsonUtils.getString(ore, "ore");
		
		OreDictionary.registerOre(orename, item);

		return true;
	}
}
