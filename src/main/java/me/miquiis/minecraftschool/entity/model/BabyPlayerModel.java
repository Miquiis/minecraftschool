package me.miquiis.minecraftschool.entity.model;

import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.entity.custom.BabyPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

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


    @Override
    public void setLivingAnimations(BabyPlayerEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head_c");

        LivingEntity entityIn = (LivingEntity) entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
