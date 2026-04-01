package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.world.entity.animal.bee.Bee;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeehiveBlockEntity.class)
public abstract class BeehiveBlockEntityMixin {
	@Inject(method = "addOccupant", at = @At("HEAD"), cancellable = true)
	private void addOccupantIfHiveChunkLoaded(Bee bee, CallbackInfo ci) {
		if (!PandaAntiDupeConfig.getDupeStatus("BeeDupe")) {
			return;
		}
		if (!bee.level().hasChunkAt(((BlockEntity) (Object) this).getBlockPos())) {
			ci.cancel();
		}
	}
}
