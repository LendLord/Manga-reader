package com.lendlord.manga.MangaViewer;

import java.io.Serializable;

/**
 * Created by lendlord on 13.10.15.
 */
public class MangaChapterData implements Serializable {
    private String chapterName;
    private boolean isChecked;

    public MangaChapterData(String chapterName, boolean isChecked){
        this.chapterName = chapterName;
        this.isChecked = isChecked;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
