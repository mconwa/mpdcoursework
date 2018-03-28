package mpdproject.gcu.me.org.assignmenttest1;

/**
 * Created by mconwa201 on 3/27/2018.
 */

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class parser extends MainActivity {

    public static DataItem[] parseFeed(String content) {

        try {

            boolean inItemTag = false;
            String currentTagName = "";
            DataItem currentItem = null;
            List<DataItem> itemList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if (currentTagName.equals("item")) {
                            inItemTag = true;
                            currentItem = new DataItem();
                            itemList.add(currentItem);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            inItemTag = false;
                        }
                        currentTagName = "";
                        break;
                    case XmlPullParser.TEXT:
                        String text = parser.getText();
                        if (inItemTag && currentItem != null) {
                            try {
                                switch (currentTagName) {
                                    case "title":
                                        currentItem.setTitle(text);
                                        break;
                                    case "description":
                                        currentItem.setDescription(text);
                                        break;
                                    case "link":
                                        currentItem.setLink(text);
                                        break;
                                    case "georss:point":
                                        currentItem.setCoordinates(text);
                                        break;
                                    case "author":
                                        currentItem.setAuthor(text);
                                        break;
                                    case "comments":
                                        currentItem.setComments(text);
                                        break;
                                    case "pubDate":
                                        currentItem.setPubDate(text);
                                        break;
                                    default:
                                        break;
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }

                eventType = parser.next();

            } // end while loop

            DataItem[] dataItems = new DataItem[itemList.size()];
            return itemList.toArray(dataItems);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
