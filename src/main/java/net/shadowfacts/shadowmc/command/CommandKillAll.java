package net.shadowfacts.shadowmc.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandKillAll implements SubCommand {

	public static CommandKillAll instance = new CommandKillAll();
	
	
	@Override
	public String getCommandName() {
	    return "killall";
	}

	@Override
	public void handleCommand(ICommandSender sender, String[] args) throws CommandException {
		sender.addChatMessage(new ChatComponentText("This command is not implemented yet."));
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
		return null;
	}

	@Override
	public void handleHelpRequest(ICommandSender sender, String[] args) {
		sender.addChatMessage(new ChatComponentText("Kills all hostile mobs within a 32 block radius."));
	}

}
