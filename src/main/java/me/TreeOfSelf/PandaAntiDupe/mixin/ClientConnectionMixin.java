package me.TreeOfSelf.PandaAntiDupe.mixin;

import io.netty.channel.ChannelHandlerContext;
import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
public class ClientConnectionMixin {
    @Shadow
    private PacketListener packetListener;


    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"), cancellable = true)
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
        if (!PandaAntiDupeConfig.getDupeStatus("BookDupe")) return;

        if (packetListener instanceof ServerPlayNetworkHandler) {
            ServerPlayerEntity serverPlayerEntity = ((ServerPlayNetworkHandler) packetListener).getPlayer();
            if (packet instanceof BookUpdateC2SPacket bookPacket) {
                ItemStack bookStack = serverPlayerEntity.getInventory().getStack(bookPacket.slot());
                if (bookPacket.title().isPresent() && bookPacket.title().get().length() > 32) {
                    ci.cancel();
                }
                if (bookStack.getItem() != Items.WRITABLE_BOOK) {
                    ci.cancel();
                }
            }
        }
    }
}

