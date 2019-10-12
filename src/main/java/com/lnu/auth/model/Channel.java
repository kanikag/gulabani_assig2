package com.lnu.auth.model;

import java.util.List;

public class Channel {
    String title;
    String description;
    String link;
    String language;
    String creator;
    List<Item> items;

    public Channel() {
    }

    public Channel(String title, String description, String link, String language, String creator, List<Item> items) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.language = language;
        this.creator = creator;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", language='" + language + '\'' +
                ", creator='" + creator + '\'' +
                ", items=" + items +
                '}';
    }
}
