package jaminv.advancedmachines.util.dialog.container;

import jaminv.advancedmachines.util.interfaces.IHasGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public interface IContainerUpdate {
	public int getFieldCount();
	public int getField(int id);
	public void setField(int id, int value);
	public boolean canInteractWith(EntityPlayer playerIn);
}
