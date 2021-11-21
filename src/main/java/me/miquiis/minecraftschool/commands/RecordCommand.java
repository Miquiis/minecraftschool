package me.miquiis.minecraftschool.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.entity.ModEntityTypes;
import me.miquiis.minecraftschool.managers.RecordManager;
import me.miquiis.minecraftschool.models.RecordScript;
import me.miquiis.minecraftschool.utils.ColorUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySummonArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.entity.*;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class RecordCommand {
    public RecordCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("record").then(Commands.literal("start").then(Commands.argument("name", StringArgumentType.string()).executes(context -> {
            String input = StringArgumentType.getString(context, "name");
            context.getSource().sendFeedback(new StringTextComponent("Started recording for " + input), true);
            MinecraftSchool.getInstance().getRecordManager().startRecording(context.getSource().asPlayer().getUniqueID(), input);
            return 1;
        }))).then(Commands.literal("stop").executes(context -> {
            context.getSource().sendFeedback(new StringTextComponent("Recording stopped."), true);
            MinecraftSchool.getInstance().getRecordManager().stopRecording(context.getSource().asPlayer().getUniqueID());
            return 1;
        })).then(Commands.literal("play").then(Commands.argument("name", StringArgumentType.string()).then(Commands.argument("entity", EntitySummonArgument.entitySummon()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes(context -> {
            ResourceLocation entity = EntitySummonArgument.getEntityId(context, "entity");
            return MinecraftSchool.getInstance().getRecordManager().startPlaying(context.getSource().asPlayer(), StringArgumentType.getString(context, "name"), EntityType.byKey(entity.toString()).orElse(null));
        }))))
        );
    }

}
