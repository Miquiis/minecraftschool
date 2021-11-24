package me.miquiis.minecraftschool.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.entity.custom.BabyPlayerEntity;
import me.miquiis.minecraftschool.models.BubbleMessage;
import me.miquiis.minecraftschool.network.MinecraftSchoolNetwork;
import me.miquiis.minecraftschool.network.message.SendBubbleMessage;
import me.miquiis.minecraftschool.utils.ColorUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

public class DebugCommand {
    public DebugCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("debug").executes(command -> debug(command.getSource())));
    }

    private int debug(CommandSource source) throws CommandSyntaxException {

        System.out.println(findBabyPlayer(source.asPlayer()));

        final BabyPlayerEntity foundEntity = findBabyPlayer(source.asPlayer());
        if (foundEntity == null) return -1;

        MinecraftSchoolNetwork.CHANNEL.send(PacketDistributor.NEAR.with(
                () -> new PacketDistributor.TargetPoint(
                        foundEntity.getPosX(),
                        foundEntity.getPosY(),
                        foundEntity.getPosZ(),
                        50.0,
                        foundEntity.getEntityWorld().getDimensionKey()
                )
        ), new SendBubbleMessage(new BubbleMessage(
                foundEntity.getUniqueID(),
                foundEntity.getEntityId(),
                "Hello, world!",
                System.currentTimeMillis()
        )));
        return 1;
    }

    private BabyPlayerEntity findBabyPlayer(ServerPlayerEntity serverPlayerEntity)
    {
        return (BabyPlayerEntity) serverPlayerEntity.getServerWorld().getEntitiesWithinAABBExcludingEntity(serverPlayerEntity.getEntity(), serverPlayerEntity.getBoundingBox().grow(15, 15 ,15)).stream().filter(entity -> entity instanceof BabyPlayerEntity).findFirst().orElse(null);
    }

}
