package com.example.user.recyclerviewwithjson;

public class ListItem {
    private String head;
    private String desc;
    private String image;

    public ListItem(String head, String desc,String image) {
        this.head = head;
        this.desc = desc;
        this.image = image;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }
}
