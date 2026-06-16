package com.ytgld.chest_useful_book.event;

import com.ytgld.chest_item.config.ConfigPluginFinder;
import com.ytgld.chest_item.config.RegisterItemConfig;
import com.ytgld.chest_item.items.InitItems;
import com.ytgld.chest_item.items.ItemBase;
import com.ytgld.chest_useful_book.CIUsefulBook;
import com.ytgld.chest_useful_book.config.BookConfigPlugin;
import com.ytgld.chest_useful_book.config.BookConfigPluginFinder;
import com.ytgld.chest_useful_book.config.BookRegisterItemConfig;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
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
        components.addAll(BookTool.addText(itemTarget,InitItems.Bone_Head.asItem(), "bone_head"));
        components.addAll(BookTool.addText(itemTarget,InitItems.Heart_.asItem(), "heart","heart2"));
        return components;
    }
    @BookConfigPlugin
    public static class AllBookConfig implements BookRegisterItemConfig {

        public void config(ModConfigSpec.Builder builder) {
            bone_head = BookTool.setModConfigSpec(builder,
                    "bone_head",10,0,Integer.MAX_VALUE);
            heart = BookTool.setModConfigSpec(builder,
                    "heart",4,0,Integer.MAX_VALUE);
            heart2 = BookTool.setModConfigSpec(builder,
                    "heart2",2,0,Integer.MAX_VALUE);

        }
        public List<CIBookString> theLanguageProvider() {
            return List.of(

                    new CIBookString("bone_head", "无厌之骸骨", CIUsefulBookText.eat),
                    new CIBookString("heart", "仿生心脏", CIUsefulBookText.heal),
                    new CIBookString("heart2", "仿生心脏2", CIUsefulBookText.maxHealth)

            );
        }



        public static ModConfigSpec.DoubleValue bone_head;
        public static ModConfigSpec.DoubleValue heart;
        public static ModConfigSpec.DoubleValue heart2;
     }
    public static class CIUsefulBookText extends LanguageProvider{
       public static String eat = "进食速度";
       public static String heal = "治疗";
       public static String maxHealth = "最大生命值";

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
            addCI("bone_head",BookTool.addNumberString(true,true,
                    10, eat));

            addCI("heart",BookTool.addNumberString(true,true,
                    4, heal));

            addCI("heart2",BookTool.addNumberString(true,true,
                    2, maxHealth));
        }
        public void addCI(String string ,String path){
            this.add("chest_useful_book.modify.book." + string, path);
        }
    }



}
