package me.sebastian420.PandaAntiDupe.mixin;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class TridentChargeMixin {
    @Shadow
    private PacketListener packetListener;

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        if (packetListener instanceof ServerPlayNetworkHandler) {

            ServerPlayerEntity serverPlayerEntity = ((ServerPlayNetworkHandler) packetListener).getPlayer();

            if (packet instanceof ClickSlotC2SPacket) {
                if(serverPlayerEntity.getItemUseTime() != 0 || serverPlayerEntity.getItemUseTimeLeft() != 0)
                    ci.cancel();
            }
        }
    }
}