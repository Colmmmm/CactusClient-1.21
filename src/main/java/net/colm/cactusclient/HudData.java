package net.colm.cactusclient;

import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashMap;

public class HudData {
    // Tracked items with their latest count
    public static final Map<Item, Integer> trackedItems = new HashMap<>();

    public static Map<Item, Integer> getTrackedItems() {
        return trackedItems;
    }

    public static void addItem(Item item) {
        trackedItems.put(item, 0); // default count to 0
    }

    public static void updateItemCount(Item item, int count) {
        if (trackedItems.containsKey(item)) {
            trackedItems.put(item, count);
        }
    }

    public static void clear() {
        trackedItems.clear();
    }
}