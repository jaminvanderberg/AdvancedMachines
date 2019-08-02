package jaminv.advancedmachines.objects.blocks.machine.expansion.redstone;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import jaminv.advancedmachines.client.BakedModelBase;
import jaminv.advancedmachines.client.RawTextures;
import jaminv.advancedmachines.client.quads.IModelQuad;
import jaminv.advancedmachines.client.quads.ModelQuadBlock;
import jaminv.advancedmachines.client.quads.ModelQuadLayeredBlock;
import jaminv.advancedmachines.client.textureset.TextureSets;
import jaminv.advancedmachines.init.property.Properties;
import jaminv.advancedmachines.objects.blocks.machine.multiblock.model.LayeredTextureMultiblockBase;
import jaminv.advancedmachines.util.helper.BlockHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;

public class BakedModelRedstone extends BakedModelBase {
	
	protected static class LayeredTextureRedstone extends LayeredTextureMultiblockBase {

		public LayeredTextureRedstone(IBlockState state) {	super(state, "expansion"); }

		@Override
		protected TextureAtlasSprite getBaseTexture(String variant) {
			return RawTextures.get("redstone", getState().getValue(Properties.ACTIVE) ? "active" : "inactive", variant, "base");
		}
	}	

	public BakedModelRedstone(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		super(state, format, bakedTextureGetter);
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return getTexture("expansion.basic.all");
	}

	@Override
	public List<IModelQuad> render(VertexFormat format, IBlockState state, EnumFacing side, long rand) {
		return Collections.singletonList(new ModelQuadLayeredBlock(format,
			BlockHelper.getExtendedFacing(state),
			new LayeredTextureMultiblockBase(state, "expansion"),
			new LayeredTextureRedstone(state))
		);
	}

}
