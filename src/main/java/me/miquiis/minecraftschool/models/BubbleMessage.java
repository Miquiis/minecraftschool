package me.miquiis.minecraftschool.models;

import me.miquiis.minecraftschool.managers.BubbleManager;

import java.util.UUID;

public class BubbleMessage {

    public UUID messageSender;
    public int messageSenderId;
    public String message;
    public Long messageTime;

    public BubbleMessage(UUID messageSender, int messageSenderId, String message, Long messageTime) {
        this.messageSender = messageSender;
        this.messageSenderId = messageSenderId;
        this.message = message;
        this.messageTime = messageTime;
    }

    public boolean hasFaded(float stayTime)
    {
        Long currentTime = System.currentTimeMillis();
        float secondsPassed = ((currentTime - messageTime) / 1000f) - stayTime;
        return secondsPassed >= BubbleManager.FADE_OUT_TIME;
    }

    public float getFadedPercentage(float stayTime)
    {
        Long currentTime = System.currentTimeMillis();
        float secondsPassed = ((currentTime - messageTime) / 1000f) - stayTime;
        if (secondsPassed <= 0) return 0f;
        return secondsPassed / BubbleManager.FADE_OUT_TIME;
    }

}
