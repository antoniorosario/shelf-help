package com.antoniorosario.shelfhelpv2.models;

/*
 * NOT YET IMPLEMENTED.
 * Reconsider using a publisher class due it's small size.
 * Do users care about seeing a  publisher?
 */

//TODO implement after normalizing and restructuring db properly
public class Publisher {

    private String publisherName;

    public Publisher(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}

