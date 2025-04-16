package net.colm.cactusclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.colm.cactusclient.inventorychecker.inventoryCheckerClass;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class CheckItem {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("checkitem")
                        .then(Commands.argument("item", StringArgumentType.word())
                                .executes(context -> {
                                    String itemName = StringArgumentType.getString(context, "item");
                                    CommandSourceStack source = context.getSource();

                                    if (source.getEntity() instanceof ServerPlayer player) {
                                        // Try to load the item from the registry
                                        ResourceLocation id = ResourceLocation.tryParse(itemName.toLowerCase());
                                        Item item = BuiltInRegistries.ITEM.get(id);

                                        if (item == Items.AIR) {
                                            source.sendFailure(Component.literal("Unknown item: " + itemName));
                                            return 0;
                                        }

                                        int count = inventoryCheckerClass.getItemCount(player, item);

                                        source.sendSuccess(() ->
                                                        Component.literal("You have " + count + " of " + itemName + " in your inventory!"),
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
