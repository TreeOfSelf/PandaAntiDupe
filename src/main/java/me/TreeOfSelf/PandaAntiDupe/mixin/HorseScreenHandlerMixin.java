package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.screen.MountScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MountScreenHandler.class)
public class HorseScreenHandlerMixin {
    @Shadow @Final protected LivingEntity mount;

    @Inject(
            method = "quickMove",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onlyTransferIfEntityAlive(PlayerEntity p, int i, CallbackInfoReturnable<ItemStack> cir) {
        if (!PandaAntiDupeConfig.getDupeStatus("HorseQuickMoveDupe")) return;

        if ((p.isDead() || p.isRemoved() || mount.isDead() || mount.isRemoved()))
            cir.setReturnValue(ItemStack.EMPTY);
    }
}