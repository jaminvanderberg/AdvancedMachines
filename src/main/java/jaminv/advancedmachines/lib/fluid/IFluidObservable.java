package jaminv.advancedmachines.lib.fluid;

import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IFluidObservable {

	public static interface IObserver {
		public void onTankContentsChanged();
	}
	
	public void addObserver(IObserver observer);
}