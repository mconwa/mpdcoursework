package mpdproject.gcu.me.org.assignmenttest1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mpdproject.gcu.me.org.assignmenttest1.DataItem;
import mpdproject.gcu.me.org.assignmenttest1.parser;
import mpdproject.gcu.me.org.assignmenttest1.MainActivity;

public class activity_incidents extends AppCompatActivity implements View.OnClickListener{

    private String widgetString = "";
    private String result = "";

    private boolean networkOk;
    private TextView iText;
    private TextView iOutput;
    private Button getIncidentsButton;
    private String iUrl = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra(Service.MY_SERVICE_PAYLOAD)) {
                DataItem[] dataItems = (DataItem[]) intent
                        .getParcelableArrayExtra(Service.MY_SERVICE_PAYLOAD);
                for (DataItem item : dataItems) {
                    iOutput.append(item.getTitle() + "\n");
                    iOutput.append(item.getDescription() + "\n");
                    iOutput.append(item.getCoordinates() + "\n");
                    iOutput.append(item.getLink() + "\n");
                    iOutput.append(item.getAuthor() + "\n");
                    iOutput.append(item.getComments() + "\n");
                    iOutput.append(item.getPubDate() + "\n");
                }
            } else if (intent.hasExtra(Service.MY_SERVICE_EXCEPTION)){
                String message = intent.getStringExtra(Service.MY_SERVICE_EXCEPTION);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidents);

        iText = (TextView) findViewById(R.id.iText);
        getIncidentsButton = (Button) findViewById(R.id.getIncidentsButton);
        getIncidentsButton.setOnClickListener(this);
        iOutput = (TextView) findViewById(R.id.iOutput);

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(mBroadcastReceiver,
                        new IntentFilter(Service.MY_SERVICE_MESSAGE));

        networkOk = NetworkHelper.hasNetworkAccess(this);
        iOutput.append("Network ok: " + networkOk);

        /**
        // write list to Log for testing
        if (alist != null) {
            Log.e("MyTag", "List not null");
            for (Object o : alist) {
                Log.e("MyTag", o.toString());
            }
        } else {
            Log.e("MyTag", "List is null");
        }
        **/

    } // End of onCreate


    public void onClick(View v) {



    } // End of onClick


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
    public void runClickHandler(View v) {

        if (networkOk) {
            Intent intent = new Intent(this, Service.class);
            intent.setData(Uri.parse(iUrl));
            startService(intent);
        } else {
            Toast.makeText(this, "Network not available!", Toast.LENGTH_SHORT).show();
        }
    }

    public void clearClickHandler(View v) {
        iOutput.setText("");
    }



}
