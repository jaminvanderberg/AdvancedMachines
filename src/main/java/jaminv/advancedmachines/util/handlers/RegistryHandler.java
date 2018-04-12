package jaminv.advancedmachines.util.handlers;

import jaminv.advancedmachines.init.BlockInit;
import jaminv.advancedmachines.init.ItemInit;
import jaminv.advancedmachines.util.interfaces.IHasModel;
import jaminv.advancedmachines.util.interfaces.IHasTileEntity;
import jaminv.advancedmachines.util.message.MessageRegistry;
import jaminv.advancedmachines.util.models.ModelRegistry;
import jaminv.advancedmachines.world.gen.WorldGenCustomOres;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
		
		for (Block block : BlockInit.BLOCKS) {
			if (block instanceof IHasTileEntity) {
				GameRegistry.registerTileEntity(((IHasTileEntity)block).getTileEntityClass(), block.getUnlocalizedName());
			}
		}
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for (Item item : ItemInit.ITEMS) {
			if (item instanceof IHasModel) {
				((IHasModel)item).registerModels();
			}
		}
		
		for (Block block : BlockInit.BLOCKS) {
			if (block instanceof IHasModel) {
				((IHasModel)block).registerModels();
			}
		}
	}
	
	public static void otherRegistries() {
		MessageRegistry.register();

		GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
		
		ModelRegistry.build();		
	}
}
