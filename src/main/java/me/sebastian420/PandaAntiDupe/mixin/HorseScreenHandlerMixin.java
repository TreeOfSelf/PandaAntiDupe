package me.sebastian420.PandaAntiDupe.mixin;

import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.HorseScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HorseScreenHandler.class)
public class HorseScreenHandlerMixin {

    @Shadow
    @Final
    private AbstractHorseEntity entity;

    @Inject(
            method = "quickMove",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onlyTransferIfEntityAlive(PlayerEntity p, int i, CallbackInfoReturnable<ItemStack> cir) {
        if ((p.isDead() || p.isRemoved() || entity.isDead() || entity.isRemoved()))
            cir.setReturnValue(ItemStack.EMPTY);
    }
}