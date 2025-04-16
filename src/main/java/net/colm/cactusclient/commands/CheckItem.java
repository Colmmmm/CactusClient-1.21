package net.colm.cactusclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.colm.cactusclient.inventorychecker.inventoryCheckerClass;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class CheckItem {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("checkitem")
                        .then(Commands.argument("item", StringArgumentType.word())
                                .executes(context -> {
                                    String itemName = StringArgumentType.getString(context, "item");
                                    CommandSourceStack source = context.getSource();

                                    // Debug: print the item name the player typed
                                    System.out.println("Item Name Typed: " + itemName);

                                    if (source.getEntity() instanceof ServerPlayer player) {
                                        // Create a final variable to hold the full item name
                                        final String fullItemName = itemName.contains(":") ? itemName : "minecraft:" + itemName;

                                        // Try to load the item from the registry
                                        ResourceLocation id = ResourceLocation.tryParse(fullItemName.toLowerCase());

                                        // Debug: print the ResourceLocation that was created
                                        System.out.println("Parsed ResourceLocation: " + id);

                                        if (id == null) {
                                            source.sendFailure(Component.literal("Invalid item name: " + itemName));
                                            return 0;
                                        }

                                        Item item = BuiltInRegistries.ITEM.get(id);

                                        if (item == Items.AIR) {
                                            source.sendFailure(Component.literal("Unknown item: " + itemName));
                                            return 0;
                                        }

                                        int count = inventoryCheckerClass.getItemCount(player, item);

                                        source.sendSuccess(() ->
                                                        Component.literal("You have " + count + " of " + fullItemName + " in your inventory!"),
                                                false
                                        );
                                        return 1;
                                    }

                                    return 0;
                                })
                        )
        );
    }
}
