package me.miquiis.minecraftschool.models;

import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public class RecordScript {

    public static class RecordTick {

        public double posx;
        public double posy;
        public double posz;
        public float pitch;
        public float yaw;

        public RecordTick(double posx, double posy, double posz, float pitch, float yaw)
        {
            this.posx = posx;
            this.posy = posy;
            this.posz = posz;
            this.yaw = yaw;
        }

        public RecordTick(LivingEntity livingEntity)
        {
            this.posx = livingEntity.getPosX();
            this.posy = livingEntity.getPosY();
            this.posz = livingEntity.getPosZ();
            this.pitch = livingEntity.rotationPitch;
            this.yaw = livingEntity.rotationYawHead;
        }

    }

    public String name;
    public List<RecordTick> ticks;

    public RecordScript(String name)
    {
        this.name = name;
        this.ticks = new ArrayList<>();
    }

    public void addTick(RecordTick tick)
    {
        this.ticks.add(tick);
    }

    public int getMaxTicks()
    {
        return ticks.size();
    }

    public RecordTick getFirstTick()
    {
        if (getMaxTicks() == 0) return null;
        return this.ticks.get(0);
    }

}
