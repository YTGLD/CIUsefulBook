package com.ytgld.chest_useful_book.event;

import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_useful_book.CIUsefulBook;
import com.ytgld.chest_useful_book.config.BookConfigPlugin;
import com.ytgld.chest_useful_book.config.BookConfigPluginFinder;
import com.ytgld.chest_useful_book.config.BookRegisterItemConfig;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.List;

public class CIModifyBookEvent {

    /**
     *
     * @param itemTarget 需要对比那个物品
     * @param modifyText 需要修改的目标文本
     * @return 返回新的文本
     */
    public static List<Component> doModifyText(Item itemTarget,List<Component> modifyText){
        List<Component> components = new ArrayList<>(modifyText);

        components.addAll(BookTool.addText(itemTarget,InitItems.Bone_Head.asItem(),
                new BookTool.StringAndValue("bone_head", BookTool.getConfigValue(AllBookConfig.bone_head))));

        components.addAll(BookTool.addText(itemTarget,InitItems.Heart_.asItem(),
                new BookTool.StringAndValue("heart",BookTool.getConfigValue(AllBookConfig.heart)),
                new BookTool.StringAndValue("heart2",BookTool.getConfigValue(AllBookConfig.heart2))));

        components.addAll(BookTool.addText(itemTarget,InitItems.God_blood.asItem(),
                new BookTool.StringAndValue("god_blood_speed",BookTool.getConfigValue(AllBookConfig.god_blood_speed)),
                new BookTool.StringAndValue("god_blood_attackspeed",BookTool.getConfigValue(AllBookConfig.god_blood_attackspeed))));

        components.addAll(BookTool.addText(itemTarget,InitItems.Stronger_Stone.asItem(),
                new BookTool.StringAndValue("stronger_stone_damage",BookTool.getConfigValue(AllBookConfig.stronger_stone_damage))));

        components.addAll(BookTool.addText(itemTarget,InitItems.Ring_.asItem(),
                new BookTool.StringAndValue("ring_fortune",BookTool.getConfigValue(AllBookConfig.ring_fortune))));

        return components;
    }
    @BookConfigPlugin
    public static class AllBookConfig implements BookRegisterItemConfig {

        public void config(ModConfigSpec.Builder builder) {
            {
                builder.push("bone_head");
                bone_head = BookTool.setModConfigSpec(builder,
                        "bone_head", 10, 0, 100);
                builder.pop();
            }
            {
                builder.push("heart");
                heart = BookTool.setModConfigSpec(builder,
                        "heart", 4, 0, 100);
                heart2 = BookTool.setModConfigSpec(builder,
                        "heart2", 2, 0, 100);
                builder.pop();
            }
            {
                builder.push("god_blood");
                god_blood_speed = BookTool.setModConfigSpec(builder,
                        "god_blood_speed", 4, 0, 100);
                god_blood_attackspeed = BookTool.setModConfigSpec(builder,
                        "god_blood_attackspeed", 4, 0, 100);
                builder.pop();
            }
            {
                builder.push("stronger_stone");
                stronger_stone_damage = BookTool.setModConfigSpec(builder,
                        "stronger_stone_damage", 2.5, 0, 100);
                builder.pop();
            }
            {
                builder.push("ring");
                ring_fortune = BookTool.setModConfigSpec(builder,
                        "ring_fortune", 1, 0, 100);
                builder.pop();
            }
        }
        public List<CIBookString> theLanguageProvider() {
            return List.of(

                    new CIBookString("bone_head", "无厌之骸骨", CIUsefulBookText.eat),

                    new CIBookString("stronger_stone", "黄金战神", ""),
                    new CIBookString("stronger_stone_damage", "黄金战神", CIUsefulBookText.damage),

                    new CIBookString("ring", "矿工戒指", ""),
                    new CIBookString("ring_fortune", "矿工戒指", CIUsefulBookText.future),

                    new CIBookString("god_blood", "神速力", ""),
                    new CIBookString("god_blood_speed", "神速力", CIUsefulBookText.speed),
                    new CIBookString("god_blood_attackspeed", "神速力2", CIUsefulBookText.attackSpeed),

                    new CIBookString("heart", "仿生心脏", CIUsefulBookText.heal),
                    new CIBookString("heart2", "仿生心脏2", CIUsefulBookText.maxHealth)


            );
        }



        public static ModConfigSpec.DoubleValue bone_head;

        public static ModConfigSpec.DoubleValue heart;
        public static ModConfigSpec.DoubleValue heart2;

        public static ModConfigSpec.DoubleValue god_blood_speed;
        public static ModConfigSpec.DoubleValue god_blood_attackspeed;

        public static ModConfigSpec.DoubleValue stronger_stone_damage;

        public static ModConfigSpec.DoubleValue ring_fortune;

    }
    public static class CIUsefulBookText extends LanguageProvider{
       public static String eat = "进食速度";
       public static String heal = "治疗";
       public static String maxHealth = "最大生命值";
       public static String speed = "移动速度";
       public static String digger = "挖掘速度";
       public static String luck = "幸运值";
       public static String looting = "抢夺";
       public static String future = "时运";
       public static String attackSpeed = "攻击速度";
       public static String damage = "近战伤害";

        public CIUsefulBookText(PackOutput output) {
            super(output, CIUsefulBook.MODID, "chest_useful_book_test_lang");
        }
        @Override
        protected void addTranslations() {
            for(BookRegisterItemConfig registerItemConfig : BookConfigPluginFinder.getModPlugins()) {
                for(BookRegisterItemConfig.CIBookString lang : registerItemConfig.theLanguageProvider()) {
                    this.add("chest_useful_book.configuration." + lang.path(), lang.doIt());
                    this.add("chest_useful_book.config." + lang.path(), lang.doName());
                }
            }
            {
                addCI("bone_head", BookTool.addNumberString(true, true,
                        eat));
            }
            {
                addCI("heart", BookTool.addNumberString(true, true,
                        heal));
                addCI("heart2", BookTool.addNumberString(true, false,
                        maxHealth));
            }
            {
                addCI("god_blood_speed", BookTool.addNumberString(true, true,
                        speed));
                addCI("god_blood_attackspeed", BookTool.addNumberString(true, true,
                        attackSpeed));
            }
            {
                addCI("stronger_stone_damage", BookTool.addNumberString(true, true,
                        damage));
            }
            {
                addCI("ring_fortune", BookTool.addNumberString(true, false,
                        future));
            }
        }
        public void addCI(String string ,String path){
            this.add("chest_useful_book.modify.book." + string, path);
        }
    }



}
