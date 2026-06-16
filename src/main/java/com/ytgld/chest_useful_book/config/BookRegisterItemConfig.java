package com.ytgld.chest_useful_book.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public interface BookRegisterItemConfig {
    void config(ModConfigSpec.Builder var1);

    List<CIBookString> theLanguageProvider();

    record CIBookString(String path, String doIt, String doName) {
    }
}
