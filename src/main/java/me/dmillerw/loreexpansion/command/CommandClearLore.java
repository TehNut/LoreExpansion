package me.dmillerw.loreexpansion.command;

import me.dmillerw.loreexpansion.LoreExpansion;
import me.dmillerw.loreexpansion.core.saving.LoreSaveData;
import me.dmillerw.loreexpansion.network.MessageSyncLore;
import me.dmillerw.loreexpansion.util.LoreUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CommandClearLore extends CommandBase {

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/lore clear [player]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        World world = server.getEntityWorld();
        if (world.isRemote)
            return;

        EntityPlayer player = getCommandSenderAsPlayer(sender);
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("~"))
                args[0] = sender.getName();
            player = getPlayer(server, sender, args[0]);
        }

        LoreSaveData loreSaveData = LoreUtil.getData(world);
        loreSaveData.clearPlayer(player);
        LoreExpansion.NETWORK_WRAPPER.sendTo(new MessageSyncLore((EntityPlayerMP) player), (EntityPlayerMP) player);
        player.sendMessage(new TextComponentString(I18n.translateToLocalFormatted("chat.loreexpansion.lore.cleared", player.getName())));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
    }
}
