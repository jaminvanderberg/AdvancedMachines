package jaminv.advancedmachines.util.models;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.annotation.Nullable;

import jaminv.advancedmachines.util.material.MaterialBase;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.EnumFacing;

public class BlockstateMaker {
	private static final String tab1 = "        ";
	private static final String tab2 = "            ";
	private static final String tab3 = "                ";
	
	static public class Side {
		protected boolean top = false, bottom = false, left = false, right = false;
		protected boolean lock = false;
		public Side setTop(boolean set) { if (!lock) { top = set; } return this; }
		public Side setBottom(boolean set) { if (!lock) { bottom = set; } return this; }
		public Side setLeft(boolean set) { if (!lock) { left = set; } return this;}
		public Side setRight(boolean set) { if (!lock) { right = set; } return this; }
		
		public Side setAll(boolean set) {
			top = bottom = left = right = set;
			return this;
		}
		public Side reset() {
			return setAll(false);
		}
		public Side setAll() {
			return setAll(true);
		}
		
		public String toString() {
			if (!top && !bottom && !left && !right) { return "none"; }
			if (top && bottom && left && right) { return "all"; }
			
			boolean first = true;
			String ret = "";
			
			if (top) { ret += "top"; first = false; }
			if (bottom) {
				if (!first) { ret += "_"; }
				ret += "bottom"; first = false;
			}
			if (left) {
				if (!first) { ret += "_"; }
				ret += "left"; first = false;
			}
			if (right) {
				if (!first) { ret += "_"; }
				ret += "right";
			}
			return ret;
		}
	}
	static public class Block {
		public Side bottom = new Side();
		public Side east = new Side();
		public Side north = new Side();
		public Side south = new Side();
		public Side top = new Side();
		public Side west = new Side();
	}
	static public class Properties {
		public boolean bottom, east, north, south, top, west;
		public Properties(boolean bottom, boolean east, boolean north, boolean south, boolean top, boolean west) {
			this.bottom = bottom; this.east = east; this.north = north;
			this.south = south; this.top = top; this.west = west;
		}
		public Properties(Properties copy) {
			bottom = copy.bottom; east = copy.east; north = copy.north;
			south = copy.south; top = copy.top; west = copy.west;
		}
	}
	
	protected String name;
	protected String folder;
	
	public BlockstateMaker(String name, String folder) {
		this.name = name;
		this.folder = folder;
	}

	public void make() throws IOException {
		String start = "{\r\n" + 
			"    \"_autogenerated\": \"This file is automatically generated by BlockstateMaker.java. Do not modify directly.\",\r\n" +
			"    \"forge_marker\": 1,\r\n" + 
			"    \"defaults\": {\r\n" + 
			"        \"model\": \"advmach:" + name + "\"\r\n" + 
			"    },\r\n" + 
			"    \"variants\": {\r\n";
			
		String end = "    }\r\n}";
		
		File file = new File("..\\src\\main\\resources\\assets\\advmach\\blockstates\\" + name + ".json");
		file.createNewFile();
		FileWriter writer = new FileWriter(file, false);
		
		writer.write(start);
		
		boolean first = true;
		for (int bottom = 0; bottom <= 1; bottom++) {
			for (int east = 0; east <= 1; east++) {
				for (int north = 0; north <= 1; north++) {
					for (int south = 0; south <= 1; south++) {
						for (int top = 0; top <= 1; top++) {
							for (int west = 0; west <= 1; west++) {
								writer.write(makeProperties(new Properties(bottom == 1, east == 1, north == 1, south == 1, top == 1, west == 1), first));
								first = false;
							}
						}
					}
				}
			}
		}
		
		writer.write(end);
		writer.close();
	}
	
	protected String makeProperties(Properties prop, boolean first) throws IOException {
		return makeEntry(prop, first);
	}
	
	protected String makeEntry(Properties prop, boolean first) throws IOException {
		String ret = "";
		ret += tab1 + (first ? "" : ",") + "\"";
		ret += getVariantString(prop);
		ret += "\": {\r\n";
		
		Block block = makeBlock(prop);
		
		ret += tab2 + "\"model\": \"advmach:" + name + "\",\r\n";
		ret += tab2 + "\"textures\": {\r\n";
		ret += tab3 + "\"down\": \"advmach:blocks/" + getTextureFolder("down", prop) + block.bottom.toString() + "\",\r\n";
		ret += tab3 + "\"east\": \"advmach:blocks/" + getTextureFolder("east", prop) + block.east.toString() + "\",\r\n";
		ret += tab3 + "\"north\": \"advmach:blocks/" + getTextureFolder("north", prop) + block.north.toString() + "\",\r\n";
		ret += tab3 + "\"south\": \"advmach:blocks/" + getTextureFolder("south", prop) + block.south.toString() + "\",\r\n";
		ret += tab3 + "\"up\": \"advmach:blocks/" + getTextureFolder("up", prop) + block.top.toString() + "\",\r\n";
		ret += tab3 + "\"west\": \"advmach:blocks/" + getTextureFolder("west", prop) + block.west.toString() + "\",\r\n";
		ret += tab3 + "\"particle\": \"advmach:blocks/" + getTextureFolder("particle", prop) + "all\"\r\n";
		ret += tab2 + "}\r\n";

		ret += tab1 + "}\r\n";
		return ret;
	}
	
	/**
	 * Get the Variant description string for JSON entry
	 * 
	 * This method is intended to be overridden.  Remember to keep the properties in alphabetical order.
	 * The default properties for tall start with "border".  You can call this base class method before
	 * or after your derived method, depending on alphabetical order (usually before).
	 * @param bottom
	 * @param east
	 * @param north
	 * @param south
	 * @param top
	 * @param west
	 * @return Variant string, example "border_bottom=false,border_east=true..."
	 */
	protected String getVariantString(Properties prop) {
		return "border_bottom=" + (prop.bottom ? "true" : "false") + "," +
			"border_east=" + (prop.east ? "true" : "false") + "," +
			"border_north=" + (prop.north ? "true" : "false") + "," +
			"border_south=" + (prop.south ? "true" : "false") + "," +
			"border_top=" + (prop.top ? "true" : "false") + "," +
			"border_west=" + (prop.west ? "true" : "false") + "";		
	}
	
	protected Block makeBlock(Properties prop) {
		Block bl = new Block();
		if (prop.top) {
			bl.north.setTop(true); bl.south.setTop(true);
			bl.west.setTop(true); bl.east.setTop(true);
		} 
		if (prop.bottom) {
			bl.north.setBottom(true); bl.south.setBottom(true);
			bl.west.setBottom(true); bl.east.setBottom(true);
		}
		if (prop.north) {
			bl.east.setRight(true); bl.west.setLeft(true);
			bl.top.setTop(true); bl.bottom.setBottom(true);
		}
		if (prop.south) {
			bl.east.setLeft(true); bl.west.setRight(true);
			bl.top.setBottom(true); bl.bottom.setTop(true);
		}
		if (prop.east) {
			bl.north.setLeft(true); bl.south.setRight(true);
			bl.top.setRight(true); bl.bottom.setRight(true);
		}
		if (prop.west) {
			bl.north.setRight(true); bl.south.setLeft(true);
			bl.top.setLeft(true); bl.bottom.setLeft(true);
		}
		return bl;
	}
	
	protected String getTextureFolder(String facing, Properties prop) {
		return folder;
	}
}