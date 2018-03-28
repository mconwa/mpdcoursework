// Starter code for the Mobile Platform Development Assignment
// Session 2017/2018

package mpdproject.gcu.me.org.assignmenttest1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import mpdproject.gcu.me.org.assignmenttest1.DataItem;
import mpdproject.gcu.me.org.assignmenttest1.parser;
import mpdproject.gcu.me.org.assignmenttest1.Service;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String url1 = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String url2 = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String url3 = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String url4 = "http://floodline.sepa.org.uk/feed/";
    private TextView urlInput;
    private Button startButton;
    private String result = "";
    private String content = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlInput = (TextView) findViewById(R.id.urlInput);
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        LinkedList<WidgetClass> alist = null;
        alist = parseData(content, alist);

        // write list to Log for testing
        if (alist != null) {
            Log.e("MyTag", "List not null");
            for (Object o : alist) {
                Log.e("MyTag", o.toString());
            }
        } else {
            Log.e("MyTag", "List is null");
        }
    } // end of onCreate

    private LinkedList<WidgetClass> parseData(String widgetString, LinkedList<WidgetClass> alist)
    {
        return alist;
    }


    public void onClick(View aview) {

        startProgress();

        if (aview == startButton)
        {
            startActivity(new Intent(MainActivity.this, activity_menu.class));
        }
    } // end of onClick

    public void startProgress() {
        // run network access on a separate thread;
        new Thread(new Task(url1)).start();

    } //

    // need separate thread to access the internet resource over network
    // other neater solutions should be adopted in later iterations.
    class Task implements Runnable {
        private String url;

        public Task(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                //
                // throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                    Log.e("MyTag", inputLine);

                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "IOException");
            }
        }
    }
    // parse the XML into String
    private LinkedList<WidgetClass> parseData(String content)
    {
        WidgetClass widget = null;
        LinkedList<WidgetClass> alist = null;

        // throw try & catch exception
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(content));
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Found a start tag
                if (eventType == XmlPullParser.START_TAG) {
                    // Check which Tag we have
                    if (xpp.getName().equalsIgnoreCase("widgetcollection")) {
                        alist = new LinkedList<WidgetClass>();
                    } else if (xpp.getName().equalsIgnoreCase("widget")) {
                        Log.e("MyTag", "Item Start Tag found");
                        widget = new WidgetClass();
                    } else if (xpp.getName().equalsIgnoreCase("title")) {
                        // Now just get the associated text
                        String temp = xpp.nextText();
                        // Do something with text
                        Log.e("MyTag", "Title is " + temp);
                        widget.setTitle(temp);
                    } else
                        // check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("description")) {
                            // now just get the associated text
                            String temp = xpp.nextText();
                            // do something with text
                            Log.e("MyTag", "Description is " + temp);
                            widget.setDescription(temp);
                        } else
                            // check which Tag we have
                            if (xpp.getName().equalsIgnoreCase("georss:point")) {
                                // now just get the associated text
                                String temp = xpp.nextText();
                                // do something with text
                                Log.e("MyTag", "Coordinates is " + temp);
                                widget.setCoordinates(temp);
                            }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().equalsIgnoreCase("widget")) {
                        Log.e("MyTag", "widget is " + widget.toString());
                        alist.add(widget);
                    } else if (xpp.getName().equalsIgnoreCase("widgetcollection")) {
                        int size;
                        size = alist.size();
                        Log.e("MyTag", "collection size is " + size);
                    }
                }
                // get the next event
                eventType = xpp.next();

            } // end of while

            // return alist;
        }
        // catch exceptions
        catch (XmlPullParserException ae1) {
            Log.e("MyTag", "Parsing error" + ae1.toString());
        } catch (IOException ae1) {
            Log.e("MyTag", "IO error during parsing");
        }

        Log.e("MyTag", "End document");

        // return alist
        return alist;

    }


    // Configuration between protrait & landscape
    public void onConfigurationChanges(Configuration newConfig)
    {
        Configuration c =
                getResources().getConfiguration();
        if (c.orientation ==
                Configuration.ORIENTATION_PORTRAIT)
        {
            // portrait

        }
        else if (c.orientation ==
                Configuration.ORIENTATION_LANDSCAPE)
        {
            // landscape
        }
    }
}

