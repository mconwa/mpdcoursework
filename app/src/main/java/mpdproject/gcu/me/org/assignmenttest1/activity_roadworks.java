package mpdproject.gcu.me.org.assignmenttest1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class activity_roadworks extends AppCompatActivity implements View.OnClickListener{

    private TextView rText;
    private TextView rOutput;
    private Button getRoadworksButton;
    private String rUrl = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roadworks);

        rText = (TextView) findViewById(R.id.rText);
        getRoadworksButton = (Button) findViewById(R.id.getRoadworksButton);
        getRoadworksButton.setOnClickListener(this);
        rOutput = (TextView) findViewById(R.id.rOutput);
    }

    public void onClick(View v) {
        startProgress();

        if (v == getRoadworksButton)
        {
            new activity_roadworks.Task(rUrl);
        }
    }

    public void startProgress() {
        // run network access on a separate thread;
        new Thread(new activity_roadworks.Task(rUrl)).start();
    }

    class Task implements Runnable {
        private String url;

        public Task(String aurl) {
            url = aurl;
        }

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


}
