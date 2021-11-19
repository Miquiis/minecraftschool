package me.miquiis.minecraftschool.event;

import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.commands.HelloWorldCommand;
import me.miquiis.minecraftschool.commands.RecordCommand;
import me.miquiis.minecraftschool.managers.RecordManager;
import me.miquiis.minecraftschool.models.PlayScript;
import me.miquiis.minecraftschool.models.RecordScript;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = MinecraftSchool.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event)
    {
        new HelloWorldCommand(event.getDispatcher());
        new RecordCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.PlayerTickEvent event)
    {
        if (event.side == LogicalSide.CLIENT && event.phase == TickEvent.Phase.END)
        {
            final MinecraftSchool instance = MinecraftSchool.getInstance();
            final RecordManager recordManager = instance.getRecordManager();

            if (!recordManager.isRecording((event.player.getUniqueID()))) return;

            recordManager.getRecordScript(event.player.getUniqueID()).addTick(new RecordScript.RecordTick(event.player));
        }
    }

    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntity().getEntityWorld().isRemote) return;
        final MinecraftSchool instance = MinecraftSchool.getInstance();
        final RecordManager recordManager = instance.getRecordManager();
        final LivingEntity livingEntity = event.getEntityLiving();

        if (!recordManager.isPlaying(event.getEntity().getUniqueID())) return;

        final PlayScript playScript = recordManager.getPlayScript(event.getEntity().getUniqueID());
        final RecordScript.RecordTick tick = playScript.playTick();

        if (tick == null)
        {
            recordManager.stopPlaying(event.getEntity().getUniqueID());
            return;
        }

        livingEntity.setPositionAndRotation(tick.posx, tick.posy, tick.posz, tick.yaw, tick.pitch);
    }

}
