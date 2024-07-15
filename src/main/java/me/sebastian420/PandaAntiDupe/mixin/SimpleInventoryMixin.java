package me.sebastian420.PandaAntiDupe.mixin;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleInventory.class)
public class SimpleInventoryMixin {

    @Shadow public void setStack(int slot, ItemStack stack) {}
    @Shadow public int size() {
        return 0;
    }

    @Inject(method= "readNbtList",at=@At("HEAD"))
    public void readNbtListWithoutDupe(NbtList list, RegistryWrapper.WrapperLookup registries, CallbackInfo ci) {
        for(int j = 0; j < this.size(); ++j) {
            this.setStack(j, ItemStack.EMPTY);
        }
    }
}