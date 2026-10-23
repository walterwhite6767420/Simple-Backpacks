
package simplebackpacks.modi;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Prediction;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public final class BackpackContainer extends SimpleContainer {

    private final ItemStack fallbackBackpack;
    private final Player owner;
    private final boolean equipped;
    private boolean ready = false;

    public BackpackContainer(
            ItemStack backpack,
            int size,
            Player owner,
            boolean equipped
    ) {
        super(size);

        this.fallbackBackpack = backpack;
        this.owner = owner;
        this.equipped = equipped;

        NonNullList<ItemStack> loaded = NonNullList.withSize(
                size,
                ItemStack.EMPTY
        );

        ItemStack source = getBackpackStack();

        if (!source.isEmpty()) {
            ItemContainerContents contents = source.getOrDefault(
                    DataComponents.CONTAINER,
                    ItemContainerContents.EMPTY
            );

            contents.copyInto(loaded);
        }

        boolean removedBackpack = false;
        for (int i = 0; i < size; i++) {
            ItemStack stack = loaded.get(i);
            if (stack.getItem() instanceof BackpackItem) {
                loaded.set(i, ItemStack.EMPTY);
                removedBackpack = true;

                if (!owner.level().isClientSide()
                        && !owner.getInventory().add(stack)) {
                    owner.drop(stack, false, Prediction.SERVER_ONLY);
                }
            }

            super.setItem(i, loaded.get(i));
        }

        ready = true;
        if (removedBackpack && !owner.level().isClientSide()) {
            saveContents();
        }
    }

    public ItemStack getBackpackStack() {
        if (equipped && owner != null) {
            return BackpackEquipmentSlot.EQUIPPED_BACKPACK
                    .bind(owner)
                    .getStack(0);
        }

        return fallbackBackpack;
    }

    @Override
    public void setChanged() {
        super.setChanged();

        if (ready) {
            saveContents();
        }
    }

    public void saveContents() {
        if (!ready
                || owner == null
                || owner.level().isClientSide()) {
            return;
        }

        ItemStack target = fallbackBackpack;

        if (equipped) {
            var storage = BackpackEquipmentSlot.EQUIPPED_BACKPACK
                    .bind(owner);
            target = storage.getStack(0);

            if (target.isEmpty()
                    || !(target.getItem() instanceof BackpackItem)) {
                return;
            }

            target.set(
                    DataComponents.CONTAINER,
                    ItemContainerContents.fromItems(getItems())
            );
            storage.setStack(0, target);
        } else {
            if (target.isEmpty()
                    || !(target.getItem() instanceof BackpackItem)) {
                return;
            }

            target.set(
                    DataComponents.CONTAINER,
                    ItemContainerContents.fromItems(getItems())
            );
        }

        owner.getInventory().setChanged();
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return !(stack.getItem() instanceof BackpackItem);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (canPlaceItem(slot, stack)) {
            super.setItem(slot, stack);
        }
    }

    @Override
    public ItemStack addItem(ItemStack stack) {
        return canPlaceItem(0, stack)
                ? super.addItem(stack)
                : stack;
    }

    @Override
    public boolean canAddItem(ItemStack stack) {
        return canPlaceItem(0, stack) && super.canAddItem(stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
