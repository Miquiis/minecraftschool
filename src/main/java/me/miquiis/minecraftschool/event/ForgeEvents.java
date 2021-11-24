package me.miquiis.minecraftschool.event;

import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.commands.DebugCommand;
import me.miquiis.minecraftschool.commands.HelloWorldCommand;
import me.miquiis.minecraftschool.commands.RecordCommand;
import me.miquiis.minecraftschool.entity.custom.BabyPlayerEntity;
import me.miquiis.minecraftschool.managers.RecordManager;
import me.miquiis.minecraftschool.models.PlayScript;
import me.miquiis.minecraftschool.models.RecordScript;
import me.miquiis.minecraftschool.network.MinecraftSchoolNetwork;
import me.miquiis.minecraftschool.network.message.RotateHeadMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = MinecraftSchool.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event)
    {
        new HelloWorldCommand(event.getDispatcher());
        new RecordCommand(event.getDispatcher());
        new DebugCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerDropItem(ItemTossEvent event)
    {
        if (!event.getPlayer().world.isRemote)
        {
            final MinecraftSchool instance = MinecraftSchool.getInstance();
            final RecordManager recordManager = instance.getRecordManager();
            if (!recordManager.isRecording((event.getPlayer().getUniqueID()))) return;
            recordManager.getRecordScript(event.getPlayer().getUniqueID()).getLastTick().addRecordTickEvent(new RecordScript.RecordTick.ItemRecordTickEvent(
                    event.getEntityItem().getItem().getItem().getRegistryName().toString(),
                    event.getEntityItem().getItem().getCount(),
                    event.getEntityItem().getItem().getOrCreateTag().toString()
            ));
        }
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
        if (event.getEntity().getEntityWorld().isRemote()) return;
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
        livingEntity.setRotationYawHead(tick.yaw);
        livingEntity.fallDistance = tick.falldistance;

        if (tick.isSwingInProgress && tick.swingProgress == 0f)
        {
            livingEntity.swing(Hand.MAIN_HAND, false);
        }

        if (tick.hasEvents())
        {
            tick.events.forEach(recordTickEvent -> {
                recordTickEvent.execute(livingEntity);
            });
        }
    }

}
