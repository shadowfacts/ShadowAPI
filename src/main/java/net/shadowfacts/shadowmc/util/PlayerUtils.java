package net.shadowfacts.shadowmc.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

/**
 * @author shadowfacts
 */
public class PlayerUtils {

	public static void addChatMsg(EntityPlayer player, String msg) {
		player.sendMessage(new TextComponentString(msg));
	}

	public static void addChatMsg(EntityPlayer player, String msg, Object... params) {
		addChatMsg(player, String.format(msg, params));
	}

}
