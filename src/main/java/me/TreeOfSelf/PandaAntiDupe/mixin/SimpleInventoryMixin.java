package me.TreeOfSelf.PandaAntiDupe.mixin;

import me.TreeOfSelf.PandaAntiDupe.PandaAntiDupeConfig;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.storage.ReadView;
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

    @Inject(method= "readDataList",at=@At("HEAD"))
    public void readNbtListWithoutDupe(ReadView.TypedListReadView<ItemStack> list, CallbackInfo ci) {
        if (!PandaAntiDupeConfig.getDupeStatus("NBTListDupe")) return;
        for(int j = 0; j < this.size(); ++j) {
            this.setStack(j, ItemStack.EMPTY);
        }
    }
}