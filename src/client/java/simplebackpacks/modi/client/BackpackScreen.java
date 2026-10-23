
package simplebackpacks.modi.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import simplebackpacks.modi.BackpackMenu;

public final class BackpackScreen
        extends AbstractContainerScreen<BackpackMenu> {

    private static final Identifier CONTAINER_TEXTURE =
            Identifier.withDefaultNamespace(
                    "textures/gui/container/generic_54.png"
            );

    public BackpackScreen(
            BackpackMenu menu,
            Inventory inventory,
            Component title
    ) {
        super(
                menu,
                inventory,
                title,
                176,
                114 + menu.rows() * 18
        );

        this.inventoryLabelX = 8;
        this.inventoryLabelY = menu.rows() * 18 + 18;
        this.titleLabelX = 8;
        this.titleLabelY = 6;
    }

    @Override
    public void extractBackground(
            GuiGraphicsExtractor graphics,
            int mouseX,
            int mouseY,
            float delta
    ) {
        super.extractBackground(graphics, mouseX, mouseY, delta);

        int backpackHeight = this.menu.rows() * 18 + 17;

        // Draw the backpack's storage area.
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                CONTAINER_TEXTURE,
                this.leftPos,
                this.topPos,
                0.0F,
                0.0F,
                this.imageWidth,
                backpackHeight,
                256,
                256
        );

        // Draw the normal player inventory and hotbar area.
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                CONTAINER_TEXTURE,
                this.leftPos,
                this.topPos + backpackHeight,
                0.0F,
                126.0F,
                this.imageWidth,
                96,
                256,
                256
        );
    }
}
