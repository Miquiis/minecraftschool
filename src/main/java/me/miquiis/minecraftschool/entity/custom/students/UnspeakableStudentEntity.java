package me.miquiis.minecraftschool.entity.custom.students;

import me.miquiis.minecraftschool.entity.custom.StudentEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class UnspeakableStudentEntity extends StudentEntity {

    public UnspeakableStudentEntity(EntityType<? extends StudentEntity> type, World worldIn) {
        super(type, worldIn, "Unspeakable");
    }
}
