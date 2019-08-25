package jaminv.advancedmachines.machine.expansion.speed;

import jaminv.advancedmachines.machine.MachineHelper;
import jaminv.advancedmachines.machine.expansion.ModelBakeryExpansionType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class ModelBakeryMachineSpeed extends ModelBakeryExpansionType {
	@Override
	public TextureAtlasSprite getParticleTexture(String variant) {
		return MachineHelper.getParticleTexture("speed", variant);
	}

	@Override
	protected String getBaseTexture() { return "speed"; }
}
