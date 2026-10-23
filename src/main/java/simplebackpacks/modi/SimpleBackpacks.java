
package simplebackpacks.modi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SimpleBackpacks implements ModInitializer {

	public static final String MOD_ID = "simple-backpacks";

	public static final Logger LOGGER =
			LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModMenuTypes.initialize();
		ModItems.initialize();
		BackpackEquipmentSlot.initialize();

		PayloadTypeRegistry.serverboundPlay().register(
				OpenEquippedBackpackPayload.TYPE,
				OpenEquippedBackpackPayload.CODEC
		);

		ServerPlayNetworking.registerGlobalReceiver(
				OpenEquippedBackpackPayload.TYPE,
				(payload, context) -> {
					var player = context.player();

					ItemStack backpack =
							BackpackEquipmentSlot.EQUIPPED_BACKPACK
									.bind(player)
									.getStack(0);

					if (!backpack.isEmpty()
							&& backpack.getItem()
							instanceof BackpackItem backpackItem) {
						backpackItem.openBackpack(
								player,
								backpack,
								true
						);
					}
				}
		);

		LOGGER.info("Simple Backpacks initialized!");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
