package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractMountInventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractMountInventoryMenu.class)
public abstract class AbstractMountInventoryMenuMixin {
	@Shadow
	@Final
	protected LivingEntity mount;

	@Inject(method = "quickMoveStack", at = @At("HEAD"), cancellable = true)
	private void onlyTransferIfEntityAlive(Player player, int slotIndex, CallbackInfoReturnable<ItemStack> cir) {
		if (!PandaAntiDupeConfig.getDupeStatus("HorseQuickMoveDupe")) {
			return;
		}
		if (!player.isAlive() || player.isRemoved() || !mount.isAlive() || mount.isRemoved()) {
			cir.setReturnValue(ItemStack.EMPTY);
		}
	}
}
