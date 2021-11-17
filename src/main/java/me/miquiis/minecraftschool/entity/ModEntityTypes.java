package me.miquiis.minecraftschool.entity;

import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.entity.custom.FakePlayerEntity;
import me.miquiis.minecraftschool.entity.custom.StudentEntity;
import me.miquiis.minecraftschool.entity.custom.students.MrBeastStudentEntity;
import me.miquiis.minecraftschool.entity.custom.students.UnspeakableStudentEntity;
import me.miquiis.minecraftschool.entity.render.BabyFakePlayerRenderer;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModEntityTypes {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, MinecraftSchool.MOD_ID);

    public static List<RegistryObject<EntityType<StudentEntity>>> REGISTERED_STUDENTS;

    public static final RegistryObject<EntityType<FakePlayerEntity>> FAKE_PLAYER =
            ENTITY_TYPES.register("fake_player",
                    () -> EntityType.Builder.create(FakePlayerEntity::new, EntityClassification.CREATURE)
                    .size(0.6f, 2f)
                    .build(new ResourceLocation(MinecraftSchool.MOD_ID, "fake_player").toString())
            );

    public static final RegistryObject<EntityType<FakePlayerEntity>> BABY_FAKE_PLAYER =
            ENTITY_TYPES.register("baby_fake_player",
                    () -> EntityType.Builder.create(FakePlayerEntity::new, EntityClassification.CREATURE)
                            .size(0.6f / BabyFakePlayerRenderer.SIZE, 2f / BabyFakePlayerRenderer.SIZE)
                            .build(new ResourceLocation(MinecraftSchool.MOD_ID, "fake_player").toString())
            );

    public static final RegistryObject<EntityType<StudentEntity>> MRBEAST_STUDENT =
            ENTITY_TYPES.register("mrbeast_student",
                    () -> EntityType.Builder.<StudentEntity>create(MrBeastStudentEntity::new, EntityClassification.CREATURE)
                            .size(0.6f / BabyFakePlayerRenderer.SIZE, 2f / BabyFakePlayerRenderer.SIZE)
                            .build(new ResourceLocation(MinecraftSchool.MOD_ID, "mrbeast_student").toString())
            );

    public static final RegistryObject<EntityType<StudentEntity>> UNSPEAKABLE_STUDENT =
            ENTITY_TYPES.register("unspeakable_student",
                    () -> EntityType.Builder.<StudentEntity>create(UnspeakableStudentEntity::new, EntityClassification.CREATURE)
                            .size(0.6f / BabyFakePlayerRenderer.SIZE, 2f / BabyFakePlayerRenderer.SIZE)
                            .build(new ResourceLocation(MinecraftSchool.MOD_ID, "unspeakable_student").toString())
            );

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
        REGISTERED_STUDENTS = new ArrayList<>();

        REGISTERED_STUDENTS.add(MRBEAST_STUDENT);
        REGISTERED_STUDENTS.add(UNSPEAKABLE_STUDENT);
    }

}
