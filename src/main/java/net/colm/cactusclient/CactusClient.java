package net.colm.cactusclient;

import net.colm.cactusclient.commands.CheckItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Test
public class CactusClient implements ModInitializer {
	public static final String MOD_ID = "cactusclient";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			CheckItem.register(dispatcher);
		});
	}
}