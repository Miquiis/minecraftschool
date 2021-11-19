package me.miquiis.minecraftschool.entity;

import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.entity.custom.BabyPlayerEntity;
import me.miquiis.minecraftschool.entity.custom.FakePlayerEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, MinecraftSchool.MOD_ID);

    public static final RegistryObject<EntityType<FakePlayerEntity>> FAKE_PLAYER =
            ENTITY_TYPES.register("fake_player",
                    () -> EntityType.Builder.create(FakePlayerEntity::new, EntityClassification.CREATURE)
                    .size(0.6f, 2f)
                    .build(new ResourceLocation(MinecraftSchool.MOD_ID, "fake_player").toString())
            );

    public static final RegistryObject<EntityType<BabyPlayerEntity>> BABY_PLAYER =
            ENTITY_TYPES.register("baby_player",
                    () -> EntityType.Builder.create(BabyPlayerEntity::new, EntityClassification.CREATURE)
                            .size(0.6f, 2f)
                            .build(new ResourceLocation(MinecraftSchool.MOD_ID, "baby_player").toString())
            );

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }

}
