package jaminv.advancedmachines.objects.blocks.machine.purifier;

import jaminv.advancedmachines.objects.blocks.machine.dialog.DialogEnergyBar;
import jaminv.advancedmachines.objects.blocks.machine.dialog.DialogProcessBar;
import jaminv.advancedmachines.objects.blocks.machine.dialog.DialogTooltipEnergy;
import jaminv.advancedmachines.objects.blocks.machine.multiblock.DialogMachineMultiblock;
import jaminv.advancedmachines.util.dialog.DialogBase;
import jaminv.advancedmachines.util.dialog.struct.DialogArea;
import jaminv.advancedmachines.util.dialog.struct.DialogTexture;

public class DialogMachinePurifier extends DialogMachineMultiblock {
	
	public DialogMachinePurifier() {
		super("textures/gui/purifier.png", 24, 0, 176, 195);
		
		this.addLayout(53, 23);			// Input
		this.addLayout(107, 23);		// Output
		this.addLayout(35, 55, 1, 6); 	// Secondary
		
		this.setInventoryLayout(new InventoryLayout(8, 84));
		this.setHotbarLayout(new HotbarLayout(8, 142));

	}
	
	public DialogMachinePurifier(TileEntityMachinePurifier te) {
		this();
		this.addElement(new DialogProcessBar(te, 74, 23, 24, 17, 200, 50));
		this.addElement(new DialogEnergyBar(te, 9, 20, 14, 50, 200, 0));
		
		this.addTooltip(new DialogTooltipEnergy(9, 20, 14, 50, te));
		this.addTooltip(new TooltipMultiblock(158, 7, 11, 11, te));
	}	
	
	@Override
	public DialogArea getJeiTexture() {
		return new DialogArea(31, 18, 162, 54);
	}
	
	@Override
	public DialogArea getJeiTarget() {
		return new DialogArea(74, 23, 24, 17);
	}
}
