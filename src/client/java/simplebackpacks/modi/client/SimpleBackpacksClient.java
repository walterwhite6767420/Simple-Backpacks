
package simplebackpacks.modi.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.trevlar.menukit.api.panel.Panel;
import com.trevlar.menukit.api.panel.PanelPosition;
import com.trevlar.menukit.api.panel.PanelStyle;
import com.trevlar.menukit.api.panel.OutsideRegion;
import com.trevlar.slotkit.api.group.SlotGroupCategory;
import com.trevlar.slotkit.api.group.SlotGroupPanelAdapter;
import com.trevlar.slotkit.api.slot.SlotFlowElement;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import simplebackpacks.modi.BackpackEquipmentSlot;
import simplebackpacks.modi.ModMenuTypes;
import simplebackpacks.modi.OpenEquippedBackpackPayload;
import simplebackpacks.modi.SimpleBackpacks;

public final class SimpleBackpacksClient
		implements ClientModInitializer {

	private static final KeyMapping.Category CATEGORY =
			KeyMapping.Category.register(
					SimpleBackpacks.id("backpack")
			);

	private static final KeyMapping OPEN_BACKPACK_KEY =
			KeyMappingHelper.registerKeyMapping(
					new KeyMapping(
							"key.simple-backpacks.open_backpack",
							InputConstants.Type.KEYBOARD,
							InputConstants.KEY_B,
							CATEGORY
					)
			);

	@Override
	public void onInitializeClient() {
		MenuScreens.register(
				ModMenuTypes.BASIC,
				BackpackScreen::new
		);
		MenuScreens.register(
				ModMenuTypes.IRON,
				BackpackScreen::new
		);
		MenuScreens.register(
				ModMenuTypes.GOLD,
				BackpackScreen::new
		);
		MenuScreens.register(
				ModMenuTypes.DIAMOND,
				BackpackScreen::new
		);
		MenuScreens.register(
				ModMenuTypes.NETHERITE,
				BackpackScreen::new
		);

		Panel backpackPanel = Panel.builder(
						"simple-backpacks:backpack_equipment_panel"
				)
				.visibleWhen(() ->
						isPlayerInventoryScreen(
								Minecraft.getInstance().gui.screen()
						)
				)
				.add(
						SlotFlowElement.of(
								BackpackEquipmentSlot.GROUP
						)
				)
				.style(PanelStyle.NONE)
				.position(
						PanelPosition.region(
								OutsideRegion.TOP_ALIGN_LEFT
						).gap(0)
				)
				.build();

		new SlotGroupPanelAdapter(backpackPanel, 0)
				.on(SlotGroupCategory.PLAYER_OFFHAND);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (OPEN_BACKPACK_KEY.consumeClick()) {
				if (client.player == null || client.level == null) {
					continue;
				}

				ClientPlayNetworking.send(
						new OpenEquippedBackpackPayload()
				);
			}
		});

		SimpleBackpacks.LOGGER.info(
				"Registered backpack screens, inventory slot, and B keybind"
		);
	}

	private static boolean isPlayerInventoryScreen(
			net.minecraft.client.gui.screens.Screen screen
	) {
		if (screen instanceof InventoryScreen) {
			return true;
		}

		return screen instanceof CreativeModeInventoryScreen creativeScreen
				&& creativeScreen.getSelectedTab()
						.getType() == CreativeModeTab.Type.INVENTORY;
	}
}
