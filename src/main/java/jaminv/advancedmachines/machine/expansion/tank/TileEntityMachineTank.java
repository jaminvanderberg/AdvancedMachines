package jaminv.advancedmachines.machine.expansion.tank;

import javax.annotation.Nullable;

import jaminv.advancedmachines.Main;
import jaminv.advancedmachines.lib.container.ContainerInventory;
import jaminv.advancedmachines.lib.fluid.FluidTankAdvanced;
import jaminv.advancedmachines.lib.fluid.IFluidObservable;
import jaminv.advancedmachines.lib.inventory.IItemObservable;
import jaminv.advancedmachines.lib.inventory.ItemStackHandlerObservable;
import jaminv.advancedmachines.lib.machine.IMachineController;
import jaminv.advancedmachines.machine.dialog.DialogIOToggle;
import jaminv.advancedmachines.machine.expansion.TileEntityMachineExpansionType;
import jaminv.advancedmachines.objects.material.MaterialExpansion;
import jaminv.advancedmachines.util.ModConfig;
import jaminv.advancedmachines.util.interfaces.IDirectional;
import jaminv.advancedmachines.util.interfaces.IHasGui;
import jaminv.advancedmachines.util.network.IOStateMessage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityMachineTank extends TileEntityMachineExpansionType implements ITickable, IHasGui, IDirectional, 
		IMachineController.ISubController, IItemObservable.IObserver, IFluidObservable.IObserver, DialogIOToggle.ISwitchableIO {
	
	protected EnumFacing facing = EnumFacing.NORTH;
	protected boolean inputState = true;
	protected int priority = 0;
	protected IMachineController controller;
	
	protected ItemStackHandlerObservable inventory = new ItemStackHandlerObservable(2) {
		@Override protected int getStackLimit(int slot, ItemStack stack) {
			if (FluidUtil.getFluidHandler(stack) != null) {	return 1; }
			return super.getStackLimit(slot, stack);
		}
	};
	
	protected FluidTankAdvanced tank = new FluidTankAdvanced(ModConfig.general.defaultMachineFluidCapacity * MaterialExpansion.maxMultiplier,
		ModConfig.general.defaultMachineFluidTransfer * MaterialExpansion.maxMultiplier);
	public IFluidTank getTank() { return tank; }
	
	public TileEntityMachineTank() {
		tank.addObserver(this);
		inventory.addObserver(this);
	}

	
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }
    
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}	
	
	@Override
	public Container createContainer(IInventory playerInventory) {
		return new ContainerInventory(DialogMachineTank.layout, inventory, playerInventory);
	}
	
	@Override
	public GuiContainer createGui(IInventory playerInventory) {
		return new DialogMachineTank(createContainer(playerInventory), this);
	}	
	
	public void setFacing(EnumFacing facing) {
		this.facing = facing;
	}
	
	public EnumFacing getFacing() {
		return facing;
	}
	
	public boolean getInputState() {
		return inputState;
	}
	    
	@Override
	public void setMeta(int meta) {
		super.setMeta(meta);
		tank.setCapacity(ModConfig.general.defaultMachineFluidCapacity * this.getMultiplier());
	}
	
	public void setInputState(boolean state) {
		this.inputState = state;
		world.markBlockRangeForRenderUpdate(this.pos, this.pos);
		
		if (controller != null) { controller.wake(); }
		
		if (world.isRemote) {
			Main.NETWORK.sendToServer(new IOStateMessage(this.getPos(), state, priority));
		}
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
				
		if (world.isRemote) {
			Main.NETWORK.sendToServer(new IOStateMessage(this.getPos(), inputState, priority));
		}
		
		if (controller != null) { controller.sortSubControllers(); }
	}
	
	boolean allowInput = false;
	boolean allowOutput = false;
	
	@Override public boolean canInput() { return allowInput; }
	@Override public boolean canOutput() { return allowOutput; }
	@Override public boolean hasController() { return controller != null; }

	@Override
	public void setController(IMachineController controller) {
		this.controller = controller;
		if (controller != null) {
			// TODO: Machine Input/Output determination
			allowInput = true; //inv.canInsert();
			allowOutput = true; //inv.canExtract();
			
			if (allowInput && !allowOutput) { this.setInputState(true); }
			if (allowOutput && !allowInput) { this.setInputState(false); }
		} else {
			allowInput = false;
			allowOutput = false;
		}
	}
	
	@Override
	public int getPriority() {
		return priority;
	}
	
	boolean sleep = true;
	
	private int tick;
		
	@Override
	public void update() {
		if (sleep) { return; } // Return quickly if there's nothing to do

		tick++;
		if (tick < ModConfig.general.tickUpdate) { return; }
		tick = 0;
		
		boolean didSomething = false;
		
		for (int i = 0; i < inventory.getSlots(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack == null) { continue; }

			FluidActionResult result = null;
			if (i == 0) { 
				result = FluidUtil.tryEmptyContainer(stack, tank, 1000, null, true);
			} else if (i == 1) {
				result = FluidUtil.tryFillContainer(stack, tank, 1000, null, true);
			}
			
			if (result != null && result.success) {
				inventory.extractItem(i, stack.getCount(), false);
				inventory.insertItem(i, result.getResult(), false);
				didSomething = true;
			}
		}
		
		if (!didSomething) { sleep = true; }
	}
	
	@Override
	public boolean preProcess(IMachineController controller) {
		boolean didSomething = false;
		
		if (inputState) {
			return moveInput(controller) > 0;
		} else {
			return moveOutput(controller) > 0;
		}
	}
	
	protected int moveInput(IMachineController controller) {
		FluidStack stack = tank.drain(Integer.MAX_VALUE, false);
		int amount = controller.getFluidTank().fill(stack, false);
		
		if (amount > 0) {
			stack = tank.drain(amount, true);
			controller.getFluidTank().fill(stack, true);
			return amount;
		}
		return 0;
	}
	
	protected int moveOutput(IMachineController controller) {
		FluidStack stack = controller.getFluidTank().drain(Integer.MAX_VALUE, false);
		int amount = tank.fill(stack, false);
		
		if (amount > 0) {
			stack = controller.getFluidTank().drain(amount, true);
			tank.fill(stack, true);
			return amount;
		}			
		return 0;
	}	
	
	@Override
	public void onInventoryContentsChanged(int slot) {
		sleep = false;
		if (controller != null) { controller.wake(); }	}
	
	@Override
	public void onTankContentsChanged() {
		this.markDirty();
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
		world.markBlockRangeForRenderUpdate(pos, pos);
		
		sleep = false;
		if (controller != null) { controller.wake(); }
	}
	
	public boolean canInteractWith(EntityPlayer playerIn) {
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}	

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("tank")) {
			tank.deserializeNBT(compound.getCompoundTag("tank"));
		}
		if (compound.hasKey("facing")) {
			facing = EnumFacing.byName(compound.getString("facing"));
		}
		inputState = compound.getBoolean("inputState");
		priority = compound.getInteger("priority");
		allowInput = compound.getBoolean("allowInput");
		allowOutput = compound.getBoolean("allowOutput");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("tank", tank.serializeNBT());
		compound.setString("facing", facing.getName());
		compound.setBoolean("inputState", inputState);
		compound.setInteger("priority", priority);
		compound.setBoolean("allowInput", allowInput);
		compound.setBoolean("allowOutput", allowOutput);		
		return compound;
	}
}