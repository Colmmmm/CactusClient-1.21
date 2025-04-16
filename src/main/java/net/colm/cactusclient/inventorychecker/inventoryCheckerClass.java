package net.colm.cactusclient.inventorychecker;

import net.colm.cactusclient.CactusClient;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class inventoryCheckerClass {
    public static int getItemCount(Player player, Item itemToCheck) {
        if (player == null || itemToCheck == null)
            return 0;

        int count = 0;
        Inventory inv = player.getInventory(); // gets the player's inventory

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == itemToCheck) {
                count += stack.getCount();
            }
        }

        return count;
    }

}
