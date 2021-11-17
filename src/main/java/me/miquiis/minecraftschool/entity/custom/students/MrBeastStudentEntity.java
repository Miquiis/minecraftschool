package me.miquiis.minecraftschool.entity.custom.students;

import me.miquiis.minecraftschool.entity.custom.FakePlayerEntity;
import me.miquiis.minecraftschool.entity.custom.StudentEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class MrBeastStudentEntity extends StudentEntity {

    public MrBeastStudentEntity(EntityType<? extends StudentEntity> type, World worldIn) {
        super(type, worldIn, "MrBeast");
    }
}
