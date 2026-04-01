package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobEntityMixin {
	@Inject(
		method = "removeAfterChangingDimensions",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;removeAfterChangingDimensions()V", shift = At.Shift.AFTER),
		cancellable = true
	)
	private void panda$toggleDimensionEquipmentClear(CallbackInfo ci) {
		if (PandaAntiDupeConfig.getDupeStatus("EntityDimensionDupe")) {
			return;
		}
		ci.cancel();
	}
}
