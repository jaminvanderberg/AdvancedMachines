package jaminv.advancedmachines.client;

import java.lang.reflect.InvocationTargetException;
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

public class ModelBase implements IModel {
	
	private Class<? extends BakedModelBase> modelClass;
	
	public ModelBase(Class<? extends BakedModelBase> modelClass) {
		this.modelClass = modelClass;
	}
	
	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		try {
			return modelClass.getConstructor(IModelState.class, VertexFormat.class, Function.class).newInstance(state, format, bakedTextureGetter);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@Override
	public Collection<ResourceLocation> getDependencies() {
		return Collections.emptySet();
	}
	
	@Override
	public Collection<ResourceLocation> getTextures() {
		return Collections.emptyList();
	}

	@Override
	public IModelState getDefaultState() {
		return TRSRTransformation.identity();
	}

	@Override
	public IModel smoothLighting(boolean value) {
		// TODO Auto-generated method stub
		return IModel.super.smoothLighting(value);
	}
	
	
}
