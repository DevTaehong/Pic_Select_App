package com.example.assignment3.placeholder;

import android.content.res.Resources;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.assignment3.ItemListFragment;


/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */

public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */


    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PlaceholderItem> ITEM_MAP = new HashMap<String, PlaceholderItem>();

    private static final int COUNT = 7;



    static {
        // Add some sample items.

        addItem(new PlaceholderItem("1", "Moon", "pic1.png"));
        addItem(new PlaceholderItem("2", "Tram", "pic2.png"));
        addItem(new PlaceholderItem("3", "Bridge", "pic3.png"));
        addItem(new PlaceholderItem("4", "City", "pic4.png"));
        addItem(new PlaceholderItem("5", "River", "pic5.png"));
        addItem(new PlaceholderItem("6", "Sunset", "pic6.png"));
        addItem(new PlaceholderItem("7", "Bridge2", "pic7.png"));
    }

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static PlaceholderItem createPlaceholderItem(int position) {

        return new PlaceholderItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem {
        public final String id;
        public final String picName;
        public final String picture;

        public PlaceholderItem(String id, String picName, String picture) {
            this.id = id;
            this.picName = picName;
            this.picture = picture;
        }

        @Override
        public String toString() {
            return picName;
        }
    }

}