package dev.phonis.schemupload.commands;

import dev.phonis.schemupload.schematica.SchemUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class UploadCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return ".upload";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return ".upload";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        SchemUtils.uploadSchem();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

}
