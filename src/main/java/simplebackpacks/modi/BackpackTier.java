
package simplebackpacks.modi;

import net.minecraft.world.item.Item;

public enum BackpackTier {
    BASIC(ModItems.BASIC_BACKPACK, 9),
    IRON(ModItems.IRON_BACKPACK, 18),
    GOLD(ModItems.GOLD_BACKPACK, 27),
    DIAMOND(ModItems.DIAMOND_BACKPACK, 36),
    NETHERITE(ModItems.NETHERITE_BACKPACK, 54);

    private final Item item;
    private final int slots;

    BackpackTier(Item item, int slots) {
        this.item = item;
        this.slots = slots;
    }

    public Item item() {
        return item;
    }

    public int slots() {
        return slots;
    }

    public static BackpackTier fromItem(Item item) {
        for (BackpackTier tier : values()) {
            if (tier.item == item) {
                return tier;
            }
        }

        return null;
    }
}
