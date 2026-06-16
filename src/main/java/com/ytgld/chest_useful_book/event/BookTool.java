package com.ytgld.chest_useful_book.event;

import com.google.common.collect.Multimap;
import com.ytgld.chest_item.items.AttReg;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_useful_book.CIUsefulBook;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BookTool {
    public static ModConfigSpec.DoubleValue  setModConfigSpec(ModConfigSpec.Builder builder,
                                                    String pathName, double valueDef, double valueMin, double valueMax){
        return builder.translation("chest_item.config."+ pathName)
                .defineInRange(pathName,valueDef,valueMin,valueMax);
    }

    public static String addNumberString(boolean increase,boolean percentage,float value , String path){
        if (increase) {
            String add = "+";
            if (percentage) {
                return add + String.valueOf(value) + "%" + path;
            }else {
                return add + String.valueOf(value) + path;
            }
        }else {
            String down = "-";
            if (percentage) {
                return down + String.valueOf(value) + "%" + path;
            }else {
                return down + String.valueOf(value) + path;
            }
        }
    }
    public static boolean isTarget(Item itemTarget,
                                   Item item){
        return itemTarget == item;
    }

    public static List<Component> addText(Item item, Item target, String...  name){
        List<Component> modifyText = new ArrayList<>();
        if (!isTarget(item,target)){
            return modifyText;
        }
        if (target instanceof ItemBase itemBase) {
            addText(modifyText,itemBase.color(itemBase.getDefaultInstance()));
        }else {
            addText(modifyText,0xffffffff);
        }
        return modifyText;
    }

    public static void addText(List<Component> modifyText,int color, String...  name){
        modifyText.add(Component.literal(""));
        modifyText.add(Component.translatable("chest_useful_book.modify.book").withStyle(Style.EMPTY
                .withColor(color)));
        for (String string : name){
            String path = "chest_useful_book.modify.book." + string;
            modifyText.add(Component.translatable(path).withStyle(Style.EMPTY
                    .withColor(color)));
        }
    }
    public static float getConfigValue(ModConfigSpec.DoubleValue doubleValue){
        return (float)doubleValue.getAsDouble();
    }
    public static void addAttribute(Multimap<Holder<Attribute>, AttributeModifier> modifiers,
                                    List<CIAttribute> ciAttribute) {
        for (CIAttribute theCIAttribute : ciAttribute) {
            float value = theCIAttribute.value();
            if (theCIAttribute.ciOperation.isp) {
                value /= 100f;
            }
            modifiers.put(theCIAttribute.attribute(),
                    new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(CIUsefulBook.MODID, theCIAttribute.nameSpace()),
                            value,
                            theCIAttribute.ciOperation.operation));
        }
    }

    public static record CIAttribute(Holder<Attribute> attribute, String nameSpace, float value,
                              CIOperation ciOperation){}

    public static enum CIOperation{
        addValue(AttributeModifier.Operation.ADD_VALUE,false),
        baseValue(AttributeModifier.Operation.ADD_MULTIPLIED_BASE,true);


        private final AttributeModifier.Operation operation;
        private final boolean isp;
        CIOperation(AttributeModifier.Operation operation, boolean isp){
            this.operation = operation;
            this.isp = isp;
        }
    }

    public static boolean isHasBookItem(Player player, Item item){
        Set<String> strings = player.getData(AttReg.itemRecord);
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(item);
        String itemName = resourceLocation.getNamespace() + ":" + resourceLocation.getPath();
        return strings.contains(itemName);
    }
}
