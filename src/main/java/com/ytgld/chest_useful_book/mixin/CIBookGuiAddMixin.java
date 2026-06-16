package com.ytgld.chest_useful_book.mixin;

import com.ytgld.chest_item.renderer.book.CIBookScreen;
import com.ytgld.chest_useful_book.event.CIModifyBookEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CIBookScreen.CIBookGuiAdd.class)
public class CIBookGuiAddMixin {
    @Mutable
    @Shadow
    @Final
    private List<Component> text;

    @Inject(
            method = {"<init>(Lnet/minecraft/world/item/Item;Lnet/minecraft/world/phys/Vec2;Lnet/minecraft/network/chat/Component;Ljava/util/List;IILcom/ytgld/chest_item/renderer/book/CIBookScreen$ThePage;I)V"},
            at = {@At("RETURN")}
    )
    private void canBeCollidedWith(Item item, Vec2 vecPos, Component mainText, List<Component> text, int colorMain, int colorText, CIBookScreen.ThePage thePage, int lightColor, CallbackInfo ci) {
       this.text  =  CIModifyBookEvent.doModifyText(item,text);
    }
}
