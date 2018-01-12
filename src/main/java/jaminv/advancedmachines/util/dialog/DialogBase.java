package jaminv.advancedmachines.util.dialog;

import jaminv.advancedmachines.util.Reference;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class DialogBase {
	
	public class Texture {
		protected final int xpos, ypos, width, height, u, v;
		
		public Texture(int xpos, int ypos, int width, int height, int u, int v) {
			this.xpos = xpos; this.ypos = ypos;
			this.width = width; this.height = height;
			this.u = u; this.v = v;
		}
		
		public Texture(int xpos, int ypos, int width, int height) {
			this(xpos, ypos, width, height, -1, -1);
		}
		
		public int getXPos() { return xpos; }
		public int getYPos() { return ypos; }
		public int getWidth() { return width; }
		public int getHeight() { return height; }
		public int getU() { return u; }
		public int getV() { return v; }
	}

	public class Text {
		protected final int xpos, ypos, width;
		protected final String text;
		protected final int color;
		
		public Text(int xpos, int ypos, int width, String text, int color) {
			this.xpos = xpos; this.ypos = ypos;
			this.width = width;
			this.text = text;
			this.color = color;
		}
		
		public Text(int xpos, int ypos, String text, int color) {
			this(xpos, ypos, -1, text, color);
		}
		
		public int getXPos() { return xpos; }
		public int getYPos() { return ypos; }
		public int getWidth() { return width; }
		public String getText() { return text; }
		public int getColor() { return color; }
		
		public void draw(FontRenderer render, int guiLeft, int guiTop) {
			int x = xpos;
			String loc = I18n.format(text);
			if (width != -1) {
				int strw = render.getStringWidth(loc);
				x += width / 2 - strw / 2;				
			}
			render.drawString(I18n.format(loc), x + guiLeft, ypos + guiTop, color);
		}
	}
	
	private final ResourceLocation background;
	private final Texture dialog;
	private NonNullList<Text> text = NonNullList.<Text>create(); 
	
	public DialogBase(String background, int xpos, int ypos, int width, int height) {
		this.background = new ResourceLocation(Reference.MODID, background);
		this.dialog = new Texture(xpos, ypos, width, height);
	}
	
	protected void addText(int xpos, int ypos, int width, String text, int color) {
		this.text.add(new Text(xpos, ypos, width, text, color));
	}
	
	protected void addText(int xpos, int ypos, String text, int color) {
		this.text.add(new Text(xpos, ypos, text, color));
	}
	
	public ResourceLocation getBackground() {
		return this.background;
	}

	public int getWidth() {
		return dialog.width;
	}
	
	public int getHeight() {
		return dialog.height;
	}
	
	public Texture getDialogTexture() {
		return this.dialog;
	}
	
	public void drawBackground(GuiScreen gui, int guiLeft, int guiTop) {
		gui.drawTexturedModalRect(guiLeft, guiTop, dialog.getXPos(), dialog.getYPos(), dialog.getWidth(), dialog.getHeight());
	}
	
	public void drawText(FontRenderer render, int guiLeft, int guiTop) {
		for (Text t : text) {
			t.draw(render, guiLeft, guiTop);
		}
	}
}
