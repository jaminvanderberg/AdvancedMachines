package jaminv.advancedmachines.lib.machine;

public interface IMachineTE extends ICanProcess, IRedstoneControlled {
	public boolean isClient();
	
	/** Allows the machine to process more than one item at a time. 
	 * Called every time a new process is started. */
	public default int getProcessingMultiplier() { return 1; }

	/** Allows the machine to process more quickly. 
	 * Called every time a new process is started. */
	public default int getSpeedMultiplier() { return 1; }
	
	/** Allows the machine to output more secondary items.
	 * Called every time a new process is started. */
	public default int getProductivityMultiplier() { return 1; }
	
	public void onControllerUpdate();
}
