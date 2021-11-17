package me.miquiis.minecraftschool.event;

import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.entity.ModEntityTypes;
import me.miquiis.minecraftschool.entity.custom.FakePlayerEntity;
import me.miquiis.minecraftschool.entity.custom.StudentEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MinecraftSchool.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event)
    {
        event.put(ModEntityTypes.FAKE_PLAYER.get(), FakePlayerEntity.setCustomAttributes().create());
        event.put(ModEntityTypes.BABY_FAKE_PLAYER.get(), FakePlayerEntity.setCustomAttributes().create());

        ModEntityTypes.REGISTERED_STUDENTS.forEach(entityTypeRegistryObject -> {
            event.put(entityTypeRegistryObject.get(), StudentEntity.setCustomAttributes().create());
        });
    }

}
