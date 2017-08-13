package com.antoniorosario.shelfhelpv2.models;

import org.parceler.Parcel;

@Parcel
public class Book {
    //TODO remove author and publisher related params + normalize db properly
    private String title;
    private String thumbnailUrl;
    private String subtitle;
    private String description;
    private String publishedDate;
    private String authorName;
    private String publisher;

    // Empty constructor needed by the Parceler library
    public Book() {
    }

    //TODO research using a builder pattern
    public Book(String title, String authorName, String thumbnailUrl, String subtitle,
                String publisher, String description, String publishedDate) {
        this.title = title;
        this.authorName = authorName;
        this.thumbnailUrl = thumbnailUrl;
        this.subtitle = subtitle;
        this.publisher = publisher;
        this.description = description;
        this.publishedDate = publishedDate;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedDate() {
        return publishedDate;
    }
}