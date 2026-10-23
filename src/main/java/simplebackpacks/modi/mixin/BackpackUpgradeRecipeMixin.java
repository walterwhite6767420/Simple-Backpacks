package simplebackpacks.modi.mixin;

import simplebackpacks.modi.BackpackItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShapedRecipe.class)
public abstract class BackpackUpgradeRecipeMixin {
    @Inject(
            method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;)Lnet/minecraft/world/item/ItemStack;",
            at = @At("RETURN"),
            cancellable = true
    )
    private void copyBackpackContents(
            CraftingInput input,
            CallbackInfoReturnable<ItemStack> callback
    ) {
        ItemStack result = callback.getReturnValue();
        if (result.isEmpty() || !(result.getItem() instanceof BackpackItem)) {
            return;
        }

        for (ItemStack ingredient : input.items()) {
            if (ingredient.getItem() instanceof BackpackItem) {
                result.set(
                        DataComponents.CONTAINER,
                        ingredient.getOrDefault(
                                DataComponents.CONTAINER,
                                ItemContainerContents.EMPTY
                        )
                );
                callback.setReturnValue(result);
                return;
            }
        }
    }
}
