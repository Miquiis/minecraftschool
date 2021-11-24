package me.miquiis.minecraftschool.managers;

import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.models.BubbleMessage;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class BubbleManager {

    public static final Color BORDER_COLOR = new Color(253,1,252);
    public static final Color INSIDE_COLOR = new Color(255,183,255);
    public static final float FADE_OUT_TIME = 2f;
    public static final float STAY_OUT_TIME = 2f;

    private MinecraftSchool mod;

    private Set<BubbleMessage> messages;

    @OnlyIn(Dist.CLIENT)
    private Set<BubbleMessage> clientMessages;

    public BubbleManager(MinecraftSchool mod)
    {
        this.mod = mod;
        this.messages = new HashSet<>();
        this.clientMessages = new HashSet<>();
    }

    @OnlyIn(Dist.CLIENT)
    public void syncMessage(BubbleMessage bubbleMessage)
    {
        clientMessages.add(bubbleMessage);
    }

    @OnlyIn(Dist.CLIENT)
    public void desyncMessage(BubbleMessage bubbleMessage)
    {
        clientMessages.remove(bubbleMessage);
    }

    public Set<BubbleMessage> getClientMessages() {
        return clientMessages;
    }

    public Set<BubbleMessage> getMessages() {
        return messages;
    }
}
