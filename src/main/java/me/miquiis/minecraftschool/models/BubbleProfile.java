package me.miquiis.minecraftschool.models;

public class BubbleProfile {

    public int borderColor;
    public int insideColor;
    public float stayTime;
    public float fadeTime;

    public BubbleProfile(int borderColor, int insideColor, float stayTime, float fadeTime)
    {
        this.borderColor = borderColor;
        this.insideColor = insideColor;
        this.stayTime = stayTime;
        this.fadeTime = fadeTime;
    }

}
