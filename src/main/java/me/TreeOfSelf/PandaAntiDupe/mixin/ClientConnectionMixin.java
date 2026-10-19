package me.TreeOfSelf.PandaAntiDupe.mixin;

import io.netty.channel.ChannelHandlerContext;
import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundEditBookPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public abstract class ClientConnectionMixin {
	@Shadow
	private volatile PacketListener packetListener;

	@Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), cancellable = true)
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci) {
		if (!(packetListener instanceof ServerGamePacketListenerImpl handler)) {
			return;
		}
		ServerPlayer player = handler.player;
		if (PandaAntiDupeConfig.getDupeStatus("BookDupe") && packet instanceof ServerboundEditBookPacket bookPacket) {
			// Same slots vanilla accepts; a negative slot threw on the netty thread
			int slot = bookPacket.slot();
			if (!Inventory.isHotbarSlot(slot) && slot != 40) {
				ci.cancel();
				return;
			}
			ItemStack bookStack = player.getInventory().getItem(slot);
			if (bookPacket.title().isPresent() && bookPacket.title().get().length() > 32) {
				ci.cancel();
				return;
			}
			if (bookStack.getItem() != Items.WRITABLE_BOOK) {
				ci.cancel();
				return;
			}
		}
	}
}
