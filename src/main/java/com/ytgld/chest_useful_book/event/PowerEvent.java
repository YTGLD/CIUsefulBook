package com.ytgld.chest_useful_book.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.InitItems;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.List;

public class PowerEvent {

    @SubscribeEvent
    public void theEatEvent(LivingEntityUseItemEvent.Start event){
        if (event.getEntity() instanceof Player player) {
            UseAnim useAnim = event.getItem().getUseAnimation();;
            if (BookTool.isHasBookItem(player, InitItems.Bone_Head.asItem())) {
                if (useAnim == UseAnim.EAT) {
                    event.setDuration((int) (event.getDuration() * 0.9f));
                }
            }
        }
    }

    @SubscribeEvent
    public void theEatEvent(PlayerTickEvent.Pre event){
        if (event.getEntity() instanceof Player player) {
            if (player.tickCount % 20 == 0) {
                Multimap<Holder<Attribute>, AttributeModifier> multimap = HashMultimap.create();
                if (BookTool.isHasBookItem(player,InitItems.Heart_.asItem())) {
                    BookTool.addAttribute(multimap, List.of(

                            new BookTool.CIAttribute(AttReg.heal, "heart",
                                    BookTool.getConfigValue(CIModifyBookEvent.AllBookConfig.heart),
                                    BookTool.CIOperation.baseValue),

                            new BookTool.CIAttribute(Attributes.MAX_HEALTH, "heart2",
                                    BookTool.getConfigValue(CIModifyBookEvent.AllBookConfig.heart2),
                                    BookTool.CIOperation.addValue)


                    ));
                }

                AttributeMap modifiers = player.getAttributes();
                modifiers.addTransientAttributeModifiers(multimap);
            }
        }
    }
}
