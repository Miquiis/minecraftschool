package me.miquiis.minecraftschool.entity.model;

import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.entity.custom.BabyPlayerEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabyPlayerModel extends AnimatedGeoModel<BabyPlayerEntity> {

    @Override
    public ResourceLocation getModelLocation(BabyPlayerEntity object) {
        return new ResourceLocation(MinecraftSchool.MOD_ID, "geo/baby.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BabyPlayerEntity object) {
        return new ResourceLocation(MinecraftSchool.MOD_ID, "textures/entity/baby.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BabyPlayerEntity animatable) {
        return new ResourceLocation(MinecraftSchool.MOD_ID, "animations/baby.animation.json");
    }
}
