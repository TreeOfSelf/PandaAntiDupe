package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin {
    @Inject(
            method = "method_10524",
            at = @At("TAIL"),
            remap = false
    )
    private static void clearAfterGetDroppedStack(ShulkerBoxBlockEntity shulkerBoxBlockEntity,
                                                     Consumer lootConsumer, CallbackInfo ci) {
        if (!PandaAntiDupeConfig.getDupeStatus("ShulkerBoxDupe")) return;

        shulkerBoxBlockEntity.clear();
    }
}