package com.ytgld.chest_useful_book;

import com.ytgld.chest_useful_book.config.BookConfigPluginFinder;
import com.ytgld.chest_useful_book.config.BookRegisterItemConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BookConfig {
    private static final Pair<BookConfig, ModConfigSpec> BUILDER = (new ModConfigSpec.Builder()).configure(BookConfig::new);
    public static BookConfig config;
    public static ModConfigSpec fc;
    public BookConfig(ModConfigSpec.Builder builder) {
        builder.push(CIUsefulBook.MODID);
        for(BookRegisterItemConfig registerItemConfig : BookConfigPluginFinder.getModPlugins()) {
            registerItemConfig.config(builder);
        }
        builder.pop();
    }

    static {
        config = BUILDER.getKey();
        fc = BUILDER.getRight();
    }
}
