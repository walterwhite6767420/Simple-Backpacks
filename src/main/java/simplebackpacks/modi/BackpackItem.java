
package simplebackpacks.modi;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public final class BackpackItem extends Item {

    private final int slots;
    private final int rows;
    private final MenuType<BackpackMenu> menuType;

    public BackpackItem(
            Properties properties,
            int slots,
            MenuType<BackpackMenu> menuType
    ) {
        super(properties);
        this.slots = slots;
        this.rows = slots / 9;
        this.menuType = menuType;
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    @Override
    public InteractionResult use(
            Level level,
            Player player,
            InteractionHand hand
    ) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        openBackpack(
                player,
                player.getItemInHand(hand),
                false
        );

        return InteractionResult.SUCCESS;
    }

    public void openBackpack(Player player, ItemStack backpack) {
        openBackpack(player, backpack, false);
    }

    public void openBackpack(
            Player player,
            ItemStack backpack,
            boolean equipped
    ) {
        if (player.level().isClientSide() || backpack.isEmpty()) {
            return;
        }

        if (!(backpack.getItem() instanceof BackpackItem)) {
            return;
        }

        player.openMenu(new MenuProvider() {

            @Override
            public Component getDisplayName() {
                return backpack.getHoverName();
            }

            @Override
            public AbstractContainerMenu createMenu(
                    int containerId,
                    Inventory inventory,
                    Player menuPlayer
            ) {
                BackpackContainer container =
                        new BackpackContainer(
                                backpack,
                                slots,
                                menuPlayer,
                                equipped
                        );

                return new BackpackMenu(
                        menuType,
                        rows,
                        containerId,
                        inventory,
                        container
                );
            }
        });
    }
}
