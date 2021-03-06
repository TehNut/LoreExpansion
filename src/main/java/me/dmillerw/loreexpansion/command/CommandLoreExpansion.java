package me.dmillerw.loreexpansion.command;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.server.command.CommandTreeBase;

public class CommandLoreExpansion extends CommandTreeBase {

    public CommandLoreExpansion() {
        addSubcommand(new CommandClearLore());
        addSubcommand(new CommandReloadLore());
        addSubcommand(new CommandDebug());
        addSubcommand(new CommandGiveLore());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getName() {
        return "lore";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/lore [clear|reload]";
    }
}
