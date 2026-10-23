
package simplebackpacks.modi;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public final class BackpackMenu extends AbstractContainerMenu {

    private final BackpackContainer container;
    private final int rows;

    public BackpackMenu(
            MenuType<BackpackMenu> type,
            int rows,
            int containerId,
            Inventory inventory
    ) {
        this(
                type,
                rows,
                containerId,
                inventory,
                new BackpackContainer(
                        ItemStack.EMPTY,
                        rows * 9,
                        inventory.player,
                        false
                )
        );
    }

    public BackpackMenu(
            MenuType<BackpackMenu> type,
            int rows,
            int containerId,
            Inventory inventory,
            BackpackContainer container
    ) {
        super(type, containerId);

        this.rows = rows;
        this.container = container;

        checkContainerSize(container, rows * 9);
        container.startOpen(inventory.player);

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < 9; column++) {
                int index = column + row * 9;

                addSlot(new Slot(
                        container,
                        index,
                        8 + column * 18,
                        18 + row * 18
                ));
            }
        }

        addStandardInventorySlots(
                inventory,
                8,
                31 + rows * 18
        );
    }

    public int rows() {
        return rows;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        if (slotIndex < 0 || slotIndex >= slots.size()) {
            return ItemStack.EMPTY;
        }

        Slot slot = slots.get(slotIndex);

        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        ItemStack original = stack.copy();
        int containerSlots = rows * 9;

        if (slotIndex < containerSlots) {
            if (!moveItemStackTo(
                    stack,
                    containerSlots,
                    slots.size(),
                    true
            )) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(
                    stack,
                    0,
                    containerSlots,
                    false
            )) {
                return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        container.saveContents();
        return original;
    }

    @Override
    public boolean stillValid(Player player) {
        if (player == null || player.level() == null) {
            return false;
        }

        ItemStack backpack = container.getBackpackStack();
        return !backpack.isEmpty()
                && backpack.getItem() instanceof BackpackItem;
    }

    @Override
    public void removed(Player player) {
        container.saveContents();
        super.removed(player);
        container.stopOpen(player);
    }
}
