package com.lendlord.manga;


import com.dexafree.materialList.card.provider.TextCardProvider;

/**
 * Created by lendlord on 09.10.15.
 */
public class MangaLibCardProvider extends TextCardProvider<MangaLibCardProvider> {
    @Override
    public int getLayout() {
        return R.layout.manga_card;
    }
}
