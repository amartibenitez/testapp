package com.simplerssreader;

/**
 * A representation of an rss item from the list.
 * 
 * @author Veaceslav Grec
 * 
 */
public class RssItem {

	private final String title;
	private final String link;
    private final String image_url;
    private final String description;
	public RssItem(String title, String link, String image_url, String description) {
		this.title = title;
		this.link = link;
        this.image_url = image_url;
        this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

    public String getImage_url()
    {
        return image_url;
    }

    public String getDescription()
    {
        return description;
    }
}
