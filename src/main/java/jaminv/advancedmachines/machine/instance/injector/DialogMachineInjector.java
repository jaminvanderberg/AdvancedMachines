package jaminv.advancedmachines.machine.instance.injector;

import jaminv.advancedmachines.lib.container.ContainerMachine;
import jaminv.advancedmachines.lib.container.layout.IJeiLayoutManager;
import jaminv.advancedmachines.lib.container.layout.ItemLayoutGrid.HotbarLayout;
import jaminv.advancedmachines.lib.container.layout.ItemLayoutGrid.InventoryLayout;
import jaminv.advancedmachines.lib.container.layout.JeiLayoutManager;
import jaminv.advancedmachines.lib.container.layout.impl.BucketLayout;
import jaminv.advancedmachines.lib.container.layout.impl.OutputLayout;
import jaminv.advancedmachines.lib.dialog.control.enums.IOState;
import jaminv.advancedmachines.lib.jei.JeiDialog;
import jaminv.advancedmachines.lib.jei.element.JeiEnergyBar;
import jaminv.advancedmachines.lib.jei.element.JeiProgressIndicator;
import jaminv.advancedmachines.lib.machine.IRedstoneControlled;
import jaminv.advancedmachines.lib.util.coord.CoordRect;
import jaminv.advancedmachines.machine.dialog.DialogBucketToggle;
import jaminv.advancedmachines.machine.dialog.DialogEnergyBar;
import jaminv.advancedmachines.machine.dialog.DialogFluid;
import jaminv.advancedmachines.machine.dialog.DialogMultiblockQuantity;
import jaminv.advancedmachines.machine.dialog.DialogProcessBar;
import jaminv.advancedmachines.machine.dialog.DialogTooltipMultiblock;
import jaminv.advancedmachines.machine.dialog.RedstoneToggleButton;
import jaminv.advancedmachines.util.Color;
import jaminv.advancedmachines.util.recipe.injector.InjectorManager;
import net.minecraft.inventory.Container;

public class DialogMachineInjector extends JeiDialog {
	
	public static final JeiLayoutManager layout = new JeiLayoutManager()
		.setItemInputLayout(InjectorManager.getRecipeManager(), 107, 21)
		.addFluidInputLayout(53, 21, 16, 48)
		.setItemOutputLayout(new OutputLayout(107, 53))
		.setItemAdditionalLayout(new BucketLayout(152, 59))
		.setInventoryLayout(new InventoryLayout(8, 84))
		.setHotbarLayout(new HotbarLayout(8, 142));
	
	@Override public IJeiLayoutManager getLayout() { return layout; }
	
	public DialogMachineInjector(Container container) {
		super(container, "textures/gui/injector.png", 24, 0, 176, 195);
		
		this.addText(8, 8, 160, "dialog.injector.title", Color.DIALOG_TEXT);
		this.addText(8, 73, "dialog.common.inventory", Color.DIALOG_TEXT);
		
		addJeiElement(new JeiEnergyBar(9, 20, 14, 50, 200, 0));
		addJeiElement(new JeiProgressIndicator(76, 22, 24, 17, 200, 50));
	}
	
	public DialogMachineInjector(ContainerMachine container, TileMachineInjector te) {
		this(container);
		
		this.addElement(new DialogProcessBar(te.getController(), 76, 22, 24, 17, 200, 50));
		this.addElement(new DialogEnergyBar(te.getEnergy(), 9, 20, 14, 50, 200, 0));
		this.addElement(new RedstoneToggleButton((IRedstoneControlled)te));
		
		this.addElement((new DialogBucketToggle(141, 62, 7, 9, te))
			.addTexture(IOState.INPUT, 200, 81)
			.addTexture(IOState.OUTPUT, 207, 81));
		
		this.addElement(new DialogFluid(53, 21, 16, 48, te.getInputTanks().getTank(0)));
		
		this.addTooltip(new DialogTooltipMultiblock(158, 7, 11, 11, te));
		
		this.addText(new DialogMultiblockQuantity(te.getController(), 76, 14, 26, 26, Color.DIALOG_TEXT));
		this.addText(new DialogMultiblockQuantity(te.getController(), 75, 15, 26, 26, Color.WHITE));
	}	
	
	@Override
	public CoordRect getJeiTexture() {
		return new CoordRect(31, 18, 120, 54);
	}
	
	@Override
	public CoordRect getJeiTarget() {
		return new CoordRect(153, 38, 14, 14);
	}
}
