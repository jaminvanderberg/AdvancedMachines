package jaminv.advancedmachines.util.models;

import java.io.FileWriter;
import java.io.IOException;

import jaminv.advancedmachines.util.material.MaterialBase;
import jaminv.advancedmachines.util.material.MaterialBase.MaterialType;
import net.minecraft.util.EnumFacing;

public class BlockstateInventory extends BlockstateMaker {

	protected MaterialType type;
	protected String input, output;
	
	public class InventoryProperties extends Properties {
		MaterialBase variant;
		EnumFacing facing;
		boolean input;
		public InventoryProperties(Properties prop, MaterialBase variant, EnumFacing facing, boolean input) {
			super(prop);
			this.variant = variant;
			this.facing = facing;
			this.input = input;
		}
	}
	
	public BlockstateInventory(String name, String folder, String input, String output, MaterialType type) {
		super(name, folder);
		this.type = type;
		this.input = input;
		this.output = output;
	}
	
	@Override
	protected String makeProperties(Properties prop, boolean first) throws IOException {
		String ret = "";
		
		for (MaterialBase variant : MaterialBase.values(type)) {
			for (EnumFacing facing : EnumFacing.VALUES) {
				for (int input = 0; input <= 1; input++) {
					InventoryProperties iprop = new InventoryProperties(prop, variant, facing, input == 1);
					ret += makeEntry(iprop, first);
					first = false;
				}
			}
		}
		return ret;
	}
	
	@Override
	protected String getVariantString(Properties prop) {
		InventoryProperties iprop = (InventoryProperties)prop;
		
		return super.getVariantString(prop) +
			",facing=" + iprop.facing.toString() +
			",input=" + (iprop.input ? "true" : "false") +
			",variant=" + iprop.variant.getName();
	}

	@Override
	protected String getTextureFolder(String facing, Properties prop) {
		InventoryProperties iprop = (InventoryProperties)prop;
		if (iprop.facing.toString() == facing) {
			if (iprop.input) {
				return input + iprop.variant.getName() + "/";
			} else {
				return output + iprop.variant.getName() + "/";
			}
		} else {
			return folder + iprop.variant.getName() + "/";
		}
	}

}
