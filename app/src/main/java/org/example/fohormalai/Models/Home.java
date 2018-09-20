package org.example.fohormalai.Models;

import org.example.fohormalai.R;

import java.util.ArrayList;
import java.util.List;

public class Home {
    private String name;
    private int image;

    public Home() {
    }

    public Home(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public static List<Home>getHomeList(){
        List<Home> obj = new ArrayList<Home>();
        obj.add(new Home("My Schedule", R.mipmap.calendar_64));
        obj.add(new Home("Notification", R.mipmap.bell_64));
        obj.add(new Home("Notices", R.mipmap.notice_64));
        obj.add(new Home("About us", R.mipmap.about_64));
        obj.add(new Home("Search", R.mipmap.search_64));
        obj.add(new Home("Better Recycler", R.mipmap.recycle_64));
        return obj;

    }

}
