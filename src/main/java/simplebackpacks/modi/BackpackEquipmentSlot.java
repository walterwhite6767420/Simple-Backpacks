
package simplebackpacks.modi;

import com.trevlar.slotkit.api.group.SlotGroupCategory;
import com.trevlar.slotkit.api.slot.ParityGroup;
import com.trevlar.slotkit.api.slot.SlotSpec;
import com.trevlar.slotkit.api.storage.StorageAttachment;
import com.trevlar.slotkit.api.storage.StorageAttachment.PlayerStorageAttachment;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public final class BackpackEquipmentSlot {

    public static final PlayerStorageAttachment<NonNullList<ItemStack>>
            EQUIPPED_BACKPACK =
            StorageAttachment.playerAttached(
                    SimpleBackpacks.MOD_ID,
                    "equipped_backpack",
                    1
            );

    public static final ParityGroup GROUP = ParityGroup.register(
            SimpleBackpacks.MOD_ID + ":backpack_equipment",
            SlotSpec.at(
                            "backpack",
                            new SlotGroupCategory(
                                    SimpleBackpacks.MOD_ID,
                                    "backpack_equipment"
                            )
                    )
                    .count(1)
                    .emptyIcon(SimpleBackpacks.id("container/slot/empty_backpack"))
                    .storage(player -> EQUIPPED_BACKPACK.bind(player))
                    .accepts(stack ->
                            stack.getItem() instanceof BackpackItem
                    )
                    .shiftClickIn(true)
    );

    private BackpackEquipmentSlot() {
    }

    public static void initialize() {
        SimpleBackpacks.LOGGER.info(
                "Registered the backpack equipment slot"
        );
    }
}
