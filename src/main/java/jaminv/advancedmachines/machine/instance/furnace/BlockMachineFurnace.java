package jaminv.advancedmachines.machine.instance.furnace;

import jaminv.advancedmachines.machine.BlockMachineMultiblock;
import jaminv.advancedmachines.machine.expansion.expansion.ModelBakeryMachineExpansion;
import jaminv.advancedmachines.objects.material.MaterialExpansion;
import jaminv.advancedmachines.util.enums.EnumGui;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMachineFurnace extends BlockMachineMultiblock {

	public BlockMachineFurnace(String name) {
		super(name);
	}
	
	protected int getGuiId() { return EnumGui.FURNACE.getId(); }
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachineFurnace();
	}
	
	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TileEntityMachineFurnace.class;
	}	
}	