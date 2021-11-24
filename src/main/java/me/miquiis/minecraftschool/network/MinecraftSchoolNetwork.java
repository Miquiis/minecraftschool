package me.miquiis.minecraftschool.network;

import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.network.message.RotateHeadMessage;
import me.miquiis.minecraftschool.network.message.SendBubbleMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class MinecraftSchoolNetwork {

    public static final String NETWORK_VERSION = "0.1.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MinecraftSchool.MOD_ID, "network"), () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION)
    );

    public static void init() {
        CHANNEL.registerMessage(0, RotateHeadMessage.class, RotateHeadMessage::encode, RotateHeadMessage::decode, RotateHeadMessage::handle);
        CHANNEL.registerMessage(1, SendBubbleMessage.class, SendBubbleMessage::encode, SendBubbleMessage::decode, SendBubbleMessage::handle);
    }

}
