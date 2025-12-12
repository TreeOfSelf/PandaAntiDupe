package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ShulkerBoxBlock.class)
public class ShulkerBoxBlockMixin {
    @Inject(
            method = "getDroppedStacks",
            at = @At("TAIL"),
            remap = false
    )
    private void clearAfterGetDroppedStack(BlockState state, LootWorldContext.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        if (!PandaAntiDupeConfig.getDupeStatus("ShulkerBoxDupe")) return;

        BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof ShulkerBoxBlockEntity shulkerBoxBlockEntity) {
            shulkerBoxBlockEntity.clear();
        }
    }
}