
package simplebackpacks.modi;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemContainerContents;

public final class ModItems {

    public static final Item BASIC_BACKPACK = register(
            "basic_backpack", 9, ModMenuTypes.BASIC
    );

    public static final Item IRON_BACKPACK = register(
            "iron_backpack", 18, ModMenuTypes.IRON
    );

    public static final Item GOLD_BACKPACK = register(
            "gold_backpack", 27, ModMenuTypes.GOLD
    );

    public static final Item DIAMOND_BACKPACK = register(
            "diamond_backpack", 36, ModMenuTypes.DIAMOND
    );

    public static final Item NETHERITE_BACKPACK = register(
            "netherite_backpack", 54, ModMenuTypes.NETHERITE
    );

    private ModItems() {
    }

    private static Item register(
            String name,
            int slots,
            net.minecraft.world.inventory.MenuType<BackpackMenu> menuType
    ) {
        ResourceKey<Item> key = ResourceKey.create(
                Registries.ITEM,
                SimpleBackpacks.id(name)
        );

        Item.Properties properties = new Item.Properties()
                .setId(key)
                .stacksTo(1)
                .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY);

        Item item = new BackpackItem(properties, slots, menuType);

        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                item
        );
    }

    public static void initialize() {
        CreativeModeTabEvents.modifyOutputEvent(
                CreativeModeTabs.INGREDIENTS
        ).register(entries -> {
            entries.accept(BASIC_BACKPACK);
            entries.accept(IRON_BACKPACK);
            entries.accept(GOLD_BACKPACK);
            entries.accept(DIAMOND_BACKPACK);
            entries.accept(NETHERITE_BACKPACK);
        });

        SimpleBackpacks.LOGGER.info(
                "Registered Simple Backpacks items and Creative tab entries"
        );
    }
}
