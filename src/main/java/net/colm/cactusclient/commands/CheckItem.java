package net.colm.cactusclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.colm.cactusclient.HudData;
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

                                    if (source.getEntity() instanceof ServerPlayer player) {
                                        String fullItemName = itemName.contains(":") ? itemName : "minecraft:" + itemName;
                                        ResourceLocation id = ResourceLocation.tryParse(fullItemName.toLowerCase());

                                        if (id == null) {
                                            source.sendFailure(Component.literal("Invalid item name: " + itemName));
                                            return 0;
                                        }

                                        Item item = BuiltInRegistries.ITEM.get(id);
                                        if (item == Items.AIR) {
                                            source.sendFailure(Component.literal("Unknown item: " + itemName));
                                            return 0;
                                        }

                                        HudData.addItem(item); // track item
                                        int count = inventoryCheckerClass.getItemCount(player, item);
                                        source.sendSuccess(() ->
                                                        Component.literal("Tracking " + fullItemName + ". You currently have " + count),
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