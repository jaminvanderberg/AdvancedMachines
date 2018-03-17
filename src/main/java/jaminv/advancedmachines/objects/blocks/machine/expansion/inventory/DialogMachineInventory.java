package jaminv.advancedmachines.objects.blocks.machine.expansion.inventory;

import jaminv.advancedmachines.objects.blocks.inventory.DialogInventory;
import jaminv.advancedmachines.util.dialog.control.DialogToggleButton;
import jaminv.advancedmachines.util.dialog.control.DialogToggleButton.IEnumIterable;
import jaminv.advancedmachines.util.dialog.struct.DialogTooltip;
import net.minecraft.client.resources.I18n;

public class DialogMachineInventory extends DialogInventory {
	
	public static enum IOState implements IEnumIterable<IOState> {
		INPUT(true, "dialog.common.input"), OUTPUT(false, "dialog.common.output");
		private static IOState[] vals = values();
		
		private final boolean input;
		private final String name;
		private IOState(boolean input, String name) {
			this.input = input;
			this.name = name;
		}
		
		public boolean getState() { return input; }
		public String getName() { return name; }
		
		@Override
		public IOState next() {
			return vals[(this.ordinal() + 1) % vals.length];
		}
	}
	
	public static class IOToggleButton extends DialogToggleButton<IOState> {
		protected final TileEntityMachineInventory te;
		public IOToggleButton(TileEntityMachineInventory te) {
			super(8, 8, 9, 9, IOState.INPUT);
			this.te = te;
			this.addTexture(IOState.INPUT, 200, 0);
			this.addTexture(IOState.OUTPUT, 200, 9);
		}
		
		@Override
		protected void onStateChanged(IOState newstate) {
			te.setInputState(newstate.getState());
		}
	}
	
	public static class DialogTooltipInput extends DialogTooltip {
		protected final IOToggleButton button;
		public DialogTooltipInput(IOToggleButton button) {
			super(8, 8, 9, 9, "");
			this.button = button;
		}
		
		@Override
		public String getText() {
			return I18n.format(button.getState().getName());
		}
	}
	
	public DialogMachineInventory(TileEntityMachineInventory te) {
		super("textures/gui/machine_inventory.png", 24, 0, 176, 177);
		
		this.addLayout(new InventoryLayout(8, 30));
		
		IOToggleButton button = new IOToggleButton(te);
		this.addElement(button);
		this.addTooltip(new DialogTooltipInput(button));
		
		this.setInventoryLayout(new InventoryLayout(8, 95));
		this.setHotbarLayout(new HotbarLayout(8, 153));
	}
}