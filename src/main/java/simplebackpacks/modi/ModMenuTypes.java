
package simplebackpacks.modi;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.entity.player.Inventory;

public final class ModMenuTypes {

    public static final MenuType<BackpackMenu> BASIC =
            register("basic_backpack", ModMenuTypes::createBasic);

    public static final MenuType<BackpackMenu> IRON =
            register("iron_backpack", ModMenuTypes::createIron);

    public static final MenuType<BackpackMenu> GOLD =
            register("gold_backpack", ModMenuTypes::createGold);

    public static final MenuType<BackpackMenu> DIAMOND =
            register("diamond_backpack", ModMenuTypes::createDiamond);

    public static final MenuType<BackpackMenu> NETHERITE =
            register("netherite_backpack", ModMenuTypes::createNetherite);

    private ModMenuTypes() {
    }

    private static MenuType<BackpackMenu> register(
            String name,
            MenuType.MenuSupplier<BackpackMenu> supplier
    ) {
        return Registry.register(
                BuiltInRegistries.MENU,
                SimpleBackpacks.id(name),
                new MenuType<>(supplier, FeatureFlagSet.of())
        );
    }

    private static BackpackMenu createBasic(int id, Inventory inventory) {
        return new BackpackMenu(BASIC, 1, id, inventory);
    }

    private static BackpackMenu createIron(int id, Inventory inventory) {
        return new BackpackMenu(IRON, 2, id, inventory);
    }

    private static BackpackMenu createGold(int id, Inventory inventory) {
        return new BackpackMenu(GOLD, 3, id, inventory);
    }

    private static BackpackMenu createDiamond(int id, Inventory inventory) {
        return new BackpackMenu(DIAMOND, 4, id, inventory);
    }

    private static BackpackMenu createNetherite(int id, Inventory inventory) {
        return new BackpackMenu(NETHERITE, 6, id, inventory);
    }

    public static void initialize() {
        SimpleBackpacks.LOGGER.info("Registering backpack menus");
    }
}
