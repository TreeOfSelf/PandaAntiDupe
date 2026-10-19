package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {
	@Shadow
	public ServerPlayer player;

	// Trident dupe: no inventory clicks while an item is in use. Checked on the server thread, and the client gets its
	// inventory back, since it already moved the items on its side
	@Inject(method = "handleContainerClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/server/level/ServerLevel;)V", shift = At.Shift.AFTER), cancellable = true)
	private void blockClickWhileUsingItem(ServerboundContainerClickPacket packet, CallbackInfo ci) {
		if (PandaAntiDupeConfig.getDupeStatus("TridentDupe") && (player.getTicksUsingItem() != 0 || player.getUseItemRemainingTicks() != 0)) {
			player.containerMenu.sendAllDataToRemote();
			ci.cancel();
		}
	}
}
