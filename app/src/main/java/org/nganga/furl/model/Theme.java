
package org.nganga.furl.model;

import android.util.SparseIntArray;

import java.util.Locale;

public enum Theme {

    ADVENTURE(0, ThemeDrawables.ADVENTURE_DRAWABLE),
    MYSTERY(1, ThemeDrawables.MYSTERY_DRAWABLE),
    NATURE(2, ThemeDrawables.NATURE_DRAWABLE),
    ROMANCE(3, ThemeDrawables.ROMANCE_DRAWABLE);


    private final int id;
    private final SparseIntArray imageList;
    private final String themeName;


    Theme(int id, SparseIntArray imageList) {
        this.id = id; // index in strings.xml array, see themes array
        this.themeName = name().toLowerCase(Locale.US);
        this.imageList = imageList;
    }

    public int getId() {
        return id;
    }

    public SparseIntArray getImageList() {
        return imageList;
    }

    public String getDisplayName() {
        return themeName;
    }

    public String getHashtag() {
        return "#" + themeName;
    }
}

