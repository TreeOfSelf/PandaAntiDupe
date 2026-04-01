package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin {
	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;handlePortal()V",
			shift = At.Shift.AFTER
		),
		cancellable = true
	)
	private void afterPortal(CallbackInfo ci) {
		if (!PandaAntiDupeConfig.getDupeStatus("GravityBlockDupe")) {
			return;
		}
		if (((Entity) (Object) this).isRemoved()) {
			ci.cancel();
		}
	}
}
