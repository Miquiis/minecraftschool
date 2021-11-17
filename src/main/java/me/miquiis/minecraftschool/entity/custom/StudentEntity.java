package me.miquiis.minecraftschool.entity.custom;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.World;

public class StudentEntity extends FakePlayerEntity {

    public String studentName;

    public StudentEntity(EntityType<? extends FakePlayerEntity> type, World worldIn, String studentName) {
        super(type, worldIn);
        this.studentName = studentName;
    }
}
