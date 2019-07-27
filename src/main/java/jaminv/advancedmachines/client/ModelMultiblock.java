package jaminv.advancedmachines.client;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

import com.google.common.collect.ImmutableBiMap.Builder;
import com.google.common.collect.ImmutableSet;

import jaminv.advancedmachines.Main;
import jaminv.advancedmachines.util.Reference;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

public class ModelMultiblock implements IModel {
	
	protected String basetexture, facetexture;
	public ModelMultiblock(String basetexture, String facetexture) {
		this.basetexture = basetexture;
		this.facetexture = facetexture;
	}
	
	public ModelMultiblock(String basetexture) {
		this.basetexture = basetexture;
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		return new BakedModelMultiblock(state, format, bakedTextureGetter, basetexture, facetexture);
	}
	
	@Override
	public Collection<ResourceLocation> getDependencies() {
		return Collections.emptySet();
	}
	
	@Override
	public Collection<ResourceLocation> getTextures() {
		Main.logger.info("Model textures requested.");
		return MultiblockTextures.textures;
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}
	
}

