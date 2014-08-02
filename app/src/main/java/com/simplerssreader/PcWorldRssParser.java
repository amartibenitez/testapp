package com.simplerssreader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class PcWorldRssParser {

	// We don't use namespaces
	private final String ns = null;

	public List<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(inputStream, null);
			parser.nextTag();
			return readFeed(parser);
		} finally {
			inputStream.close();
		}
	}

	private List<RssItem> readFeed_fix(XmlPullParser parser) throws XmlPullParserException, IOException {
		//parser.require(XmlPullParser.START_TAG, null, "rss");
		String title = null;
		String link = null;
        String imageUrl = null;
        String description = null;
		List<RssItem> items = new ArrayList<RssItem>();

        //new code
        int eventType = parser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT) {
            switch(eventType) {
                // at start of document: START_DOCUMENT
                case XmlPullParser.START_DOCUMENT:
                    //study = new Study();
                    break;

                // at start of a tag: START_TAG
                case XmlPullParser.START_TAG:
                    // get tag name
                    String tagName = parser.getName();
                    // if <study>, get attribute: 'id'
                    if(tagName.equalsIgnoreCase("media:thumbnail")) {
                        imageUrl = parser.getAttributeValue(null, "url").trim();
                    }
                    // if <content>
                    else if(tagName.equalsIgnoreCase("title")) {
                        title = parser.nextText();
                    }
                    // if <topic>
                    else if(tagName.equalsIgnoreCase("media:content")) {
                        link = parser.getAttributeValue(null, "url").trim();
                        //link = parser.nextText();
                    }
                    // if <author>
                    else if(tagName.equalsIgnoreCase("description")) {
                        description = parser.nextText();
                    }
                    if (title != null && link != null && imageUrl != null && description != null) {
                        RssItem item = new RssItem(title, link, imageUrl, description );
                        items.add(item);
                        title = null;
                        link = null;
                        imageUrl = null;
                        description = null;
                    }
                    break;
            }

            // jump to next event
            eventType = parser.next();
        }



        //end new code




/*


		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			if (name.equals("title")) {
				title = parser.nextText();
            } else if (name.equals("link")) {
                link = parser.nextText();
            }

            else if (name.equals("media:thumbnail")) {
                imageUrl = parser.getAttributeValue(null, "url");
            }
            else if (name.equals("description")) {
                description = parser.nextText();
            }
			if (title != null && link != null) {
				RssItem item = new RssItem(title, link, imageUrl, description );
				items.add(item);
				title = null;
				link = null;
                imageUrl = null;
                description = null;
			}
		}*/
		return items;
	}
    private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "rss");
        String title = null;
        String link = null;
        String imageUrl = null;
        String description = null;
        List<RssItem> items = new ArrayList<RssItem>();
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("media:content")) {
                link = readLink(parser);
            }

            else if (name.equals("media:thumbnail")) {
                imageUrl = readImageUrl(parser);
            }
            else if (name.equals("description")) {
                description = readDescription(parser);
            }
            if (title != null && link != null && imageUrl != null && description != null) {
                RssItem item = new RssItem(title, link, imageUrl, description );
                items.add(item);
                title = null;
                link = null;
                imageUrl = null;
                description = null;
            }
        }
        return items;
    }
	private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
		//parser.require(XmlPullParser.START_TAG, ns, "link");
		String link = readTextAttr(parser);
		//parser.require(XmlPullParser.END_TAG, ns, "link");
		return link;
	}

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }
    private String readImageUrl(XmlPullParser parser) throws XmlPullParserException, IOException {
        //parser.require(XmlPullParser.START_TAG, ns, "media:thumbnail");
        String imageUrl = readTextAttr(parser);
        //parser.require(XmlPullParser.END_TAG, ns, "media:thumbnail");

        //String imageUrl  = parser.getAttributeValue(null, "url");
        //String imageUrl = readTextAttr(parser);

        return imageUrl;
    }
    private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }

    // For the tags title and link, extract their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

     // For the tags imageUrl, extract their text values.
    private String readTextAttr(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        //result = parser.getAttributeValue("", "url");
        result = parser.getAttributeValue(null, "url");
        //parser.nextTag();
        if (parser.next() == XmlPullParser.TEXT) {
            //result = parser.getText();;
            //result = parser.getAttributeValue(null, "url");
            //result = "text hard";
            //parser.nextTag();
        }
        return result;
    }












}
