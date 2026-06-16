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
                    //10
                    float value = BookTool.getConfigValue(CIModifyBookEvent.AllBookConfig.bone_head);
                    //0.1f
                    value /= 100f;
                    //0.9f
                    float neoValue = 1 - value;
                    event.setDuration((int) (event.getDuration() * neoValue));
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
                if (BookTool.isHasBookItem(player,InitItems.God_blood.asItem())) {
                    BookTool.addAttribute(multimap, List.of(

                            new BookTool.CIAttribute(Attributes.MOVEMENT_SPEED, "god_blood_speed",
                                    BookTool.getConfigValue(CIModifyBookEvent.AllBookConfig.god_blood_speed),
                                    BookTool.CIOperation.baseValue),

                            new BookTool.CIAttribute(Attributes.ATTACK_SPEED, "god_blood_attackspeed",
                                    BookTool.getConfigValue(CIModifyBookEvent.AllBookConfig.god_blood_attackspeed),
                                    BookTool.CIOperation.baseValue)


                    ));
                }
                if (BookTool.isHasBookItem(player,InitItems.Stronger_Stone.asItem())) {
                    BookTool.addAttribute(multimap, List.of(

                            new BookTool.CIAttribute(Attributes.ATTACK_DAMAGE, "stronger_stone_damage",
                                    BookTool.getConfigValue(CIModifyBookEvent.AllBookConfig.stronger_stone_damage),
                                    BookTool.CIOperation.baseValue)

                    ));
                }
                if (BookTool.isHasBookItem(player,InitItems.Ring_.asItem())) {
                    BookTool.addAttribute(multimap, List.of(

                            new BookTool.CIAttribute(AttReg.fortune, "ring_fortune",
                                    BookTool.getConfigValue(CIModifyBookEvent.AllBookConfig.ring_fortune),
                                    BookTool.CIOperation.addValue)

                    ));
                }
                AttributeMap modifiers = player.getAttributes();
                modifiers.addTransientAttributeModifiers(multimap);
            }
        }
    }
}
