package net.jeff.disconnecter9000;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class Disconnecter9k implements ModInitializer {
	private static KeyBinding disconnectKeyBinding;

	@Override
	public void onInitialize() {
		// Initialize the keybinding
		disconnectKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.disconnecter9000.disconnect", // The translation key of the keybinding's name
				InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard keys
				GLFW.GLFW_KEY_DELETE, // The keycode of the key, GLFW.GLFW_KEY_DELETE for the delete key
				"key.disconnecter9000.disconnect" // The translation key of the keybinding's category
		));

		// Register the client tick event to check for key presses
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (disconnectKeyBinding.wasPressed()) {
				// Check if the client is connected to a server
				if (client.getNetworkHandler() != null) {
					// Disconnect from the server
					client.getNetworkHandler().getConnection().disconnect(Text.of("Disconnected by user"));
				}
			}
		});
	}


}