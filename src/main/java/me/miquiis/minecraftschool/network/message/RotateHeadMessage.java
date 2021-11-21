package me.miquiis.minecraftschool.network.message;

import me.miquiis.minecraftschool.MinecraftSchool;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RotateHeadMessage {

    public int id;
    public float yaw, pitch;

    public RotateHeadMessage() {

    }

    public RotateHeadMessage(int id, float yaw, float pitch)
    {
        this.id = id;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public static void encode(RotateHeadMessage message, PacketBuffer buffer)
    {
        buffer.writeInt(message.id);
        buffer.writeFloat(message.yaw);
        buffer.writeFloat(message.pitch);
    }

    public static RotateHeadMessage decode(PacketBuffer buffer)
    {
        return new RotateHeadMessage(buffer.readInt(), buffer.readFloat(), buffer.readFloat());
    }

    public static void handle(RotateHeadMessage message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> RotateHeadMessage.handlePacket(message, contextSupplier));
        });
        context.setPacketHandled(true);
    }

    // In ClientPacketHandlerClass
    public static void handlePacket(RotateHeadMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        // Do stuff
        final Entity entity = Minecraft.getInstance().world.getEntityByID(message.id);
        entity.setRotationYawHead(message.yaw);
        //entity.setHeadRotation(message.yaw, (int) message.pitch);
        entity.rotationPitch = message.pitch;
    }

}
