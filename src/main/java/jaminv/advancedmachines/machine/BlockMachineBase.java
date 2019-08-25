package jaminv.advancedmachines.machine;

import jaminv.advancedmachines.Main;
import jaminv.advancedmachines.init.property.PropertyMaterial;
import jaminv.advancedmachines.machine.expansion.MachineUpgrade;
import jaminv.advancedmachines.machine.expansion.MachineUpgradeTileEntity;
import jaminv.advancedmachines.machine.expansion.MachineUpgrade.UpgradeType;
import jaminv.advancedmachines.machine.expansion.redstone.TileEntityMachineRedstone;
import jaminv.advancedmachines.machine.multiblock.MultiblockBorders;
import jaminv.advancedmachines.objects.blocks.BlockMaterial;
import jaminv.advancedmachines.objects.blocks.DirectionalBlock;
import jaminv.advancedmachines.objects.material.MaterialBase;
import jaminv.advancedmachines.objects.material.MaterialExpansion;
import jaminv.advancedmachines.util.helper.BlockHelper;
import jaminv.advancedmachines.util.helper.ItemHelper;
import jaminv.advancedmachines.util.interfaces.IHasTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public abstract class BlockMachineBase extends BlockMaterial implements ITileEntityProvider, IHasTileEntity, MachineUpgrade {
	
    public static final PropertyDirection FACING = PropertyDirection.create("facing");
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockMachineBase(String name) {
		super(name, MaterialBase.MaterialType.EXPANSION, Material.IRON, 3.5f);
	}
	
	protected int getGuiId() { return -1; }
	
	@Override
	protected PropertyMaterial getVariant() {
		return BlockMaterial.EXPANSION_VARIANT;
	}	
		
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		return BlockHelper.openGui(worldIn, pos, playerIn, getGuiId());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		
		BlockHelper.setDirectional(worldIn, pos, placer, false);
		BlockHelper.setMeta(worldIn, pos, stack);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		VARIANT = this.getVariant();		
		return new BlockStateContainer(this, new IProperty[] { VARIANT, FACING, ACTIVE });
	}	
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntity tileentity = BlockHelper.getTileEntity(worldIn, pos);

        EnumFacing facing = EnumFacing.NORTH;
       	boolean active = false;

        if (tileentity instanceof TileEntityMachine) {
        	TileEntityMachine te = (TileEntityMachine)tileentity;
        	facing = te.getFacing();
        	active = te.isProcessing();
        }
        
        return state.withProperty(FACING, facing).withProperty(ACTIVE, active);
	}
}