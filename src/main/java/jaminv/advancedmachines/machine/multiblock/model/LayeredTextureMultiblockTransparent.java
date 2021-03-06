package jaminv.advancedmachines.machine.multiblock.model;

import java.util.Collections;
import java.util.List;

import jaminv.advancedmachines.lib.render.quad.Texture;
import jaminv.advancedmachines.machine.multiblock.MultiblockBorderType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

public class LayeredTextureMultiblockTransparent extends LayeredTextureMultiblock {

	public LayeredTextureMultiblockTransparent(IBlockState state, MultiblockTextureBase base) {
		super(state, base);
	}

	@Override
	public List<Texture> getTextures(EnumFacing side) {
		if (borders.get(side) == MultiblockBorderType.SOLID) {
			return super.getTextures(side);
		} else {
			return Collections.emptyList();
		}
	}
}
