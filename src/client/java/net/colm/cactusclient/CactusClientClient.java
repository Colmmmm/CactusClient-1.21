package net.colm.cactusclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Map;

public class CactusClientClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register((graphics, tickDelta) -> {
			onHudRender(graphics, tickDelta);
		});

		// Client-side tick listener to update item counts in real-time
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null) return;

			for (Map.Entry<Item, Integer> entry : HudData.getTrackedItems().entrySet()) {
				Item item = entry.getKey();
				int count = net.colm.cactusclient.inventorychecker.inventoryCheckerClass.getItemCount(client.player, item);
				HudData.updateItemCount(item, count);
			}
		});
	}

	private void onHudRender(GuiGraphics graphics, DeltaTracker tickDelta) {
		Minecraft client = Minecraft.getInstance();
		if (client.player == null) return;

		Font font = client.font;
		int x = 10;
		int y = 10;

		for (Map.Entry<Item, Integer> entry : HudData.getTrackedItems().entrySet()) {
			Item item = entry.getKey();
			int count = entry.getValue();
			String display = item.toString() + ": " + count;
			Component text = Component.literal(display);

			int color = (count == 0) ? 0xFF0000 : 0xFFFFFF; // Red if zero, white otherwise

			// If zero, increase font size
			if (count == 0) {
				// Scaling the font size for a "larger" effect when count is zero
				graphics.drawString(font, text, x, y, color, false);  // Use `true` to scale font (larger)
				y += 10; // Increase spacing for larger text size
			} else {
				// Regular text with normal font size and color
				graphics.drawString(font, text, x, y, color, false);
				y += 10; // Regular spacing
			}
		}
	}
}
