package me.miquiis.minecraftschool.network.message;

import me.miquiis.minecraftschool.MinecraftSchool;
import me.miquiis.minecraftschool.models.BubbleMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class SendBubbleMessage {

    public UUID messageSender;
    public int messageSenderId;
    public String message;
    public Long messageTime;

    public SendBubbleMessage() {
    }

    public SendBubbleMessage(BubbleMessage bubbleMessage)
    {
        this.messageSender = bubbleMessage.messageSender;
        this.messageSenderId = bubbleMessage.messageSenderId;
        this.message = bubbleMessage.message;
        this.messageTime = bubbleMessage.messageTime;
    }

    public SendBubbleMessage(UUID messageSender, int messageSenderId, String message, Long messageTime)
    {
        this.messageSender = messageSender;
        this.messageSenderId = messageSenderId;
        this.message = message;
        this.messageTime = messageTime;
    }

    public static void encode(SendBubbleMessage message, PacketBuffer buffer)
    {
        buffer.writeUniqueId(message.messageSender);
        buffer.writeInt(message.messageSenderId);
        buffer.writeString(message.message);
        buffer.writeLong(message.messageTime);
    }

    public static SendBubbleMessage decode(PacketBuffer buffer)
    {
        return new SendBubbleMessage(buffer.readUniqueId(), buffer.readInt(), buffer.readString(), buffer.readLong());
    }

    public static void handle(SendBubbleMessage message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SendBubbleMessage.handlePacket(message, contextSupplier));
        });
        context.setPacketHandled(true);
    }

    public static void handlePacket(SendBubbleMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        final BubbleMessage bubbleMessage = new BubbleMessage(message.messageSender, message.messageSenderId, message.message, message.messageTime);
        MinecraftSchool.getInstance().getBubbleManager().syncMessage(bubbleMessage);
    }

}
