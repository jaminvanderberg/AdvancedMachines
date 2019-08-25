package jaminv.advancedmachines.machine.expansion.redstone;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import jaminv.advancedmachines.client.RawTextures;
import jaminv.advancedmachines.client.textureset.TextureSets;
import jaminv.advancedmachines.init.property.Properties;
import jaminv.advancedmachines.lib.render.BakedModelImpl;
import jaminv.advancedmachines.lib.render.ModelBakery;
import jaminv.advancedmachines.lib.render.quad.QuadBuilder;
import jaminv.advancedmachines.lib.render.quad.QuadBuilderBlock;
import jaminv.advancedmachines.lib.render.quad.QuadBuilderLayeredBlock;
import jaminv.advancedmachines.machine.MachineHelper;
import jaminv.advancedmachines.machine.multiblock.model.LayeredTextureMultiblockBase;
import jaminv.advancedmachines.util.helper.BlockHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

public class ModelBakeryMachineRedstone implements ModelBakery {
	
	protected static class LayeredTextureRedstone extends LayeredTextureMultiblockBase {
		public LayeredTextureRedstone(IBlockState state) {	super(state, "expansion"); }

		@Override
		protected TextureAtlasSprite getBaseTexture(String variant) {
			return RawTextures.get("redstone", getState().getValue(Properties.ACTIVE) ? "active" : "inactive", variant, "base");
		}
	}	

	@Override
	public TextureAtlasSprite getParticleTexture(String variant) {
		return MachineHelper.getParticleTexture("expansion", variant);
	}

	@Override
	public List<BakedQuad> bakeModel(IBlockState state) {
		return (new QuadBuilderLayeredBlock(
			BlockHelper.getExtendedFacing(state),
			new LayeredTextureMultiblockBase(state, "expansion"),
			new LayeredTextureRedstone(state))
		).build();
	}

}
