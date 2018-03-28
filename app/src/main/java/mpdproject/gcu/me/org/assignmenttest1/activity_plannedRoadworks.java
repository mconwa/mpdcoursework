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

public class activity_plannedRoadworks extends AppCompatActivity implements View.OnClickListener{

    private TextView pText;
    private TextView pOutput;
    private Button getPlannedButton;
    private String pUrl = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String result = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_roadworks);

        pText = (TextView) findViewById(R.id.pText);
        getPlannedButton = (Button) findViewById(R.id.getPlannedButton);
        getPlannedButton.setOnClickListener(this);
        pOutput = (TextView) findViewById(R.id.pOutput);
    }

    public void onClick(View v) {
        startProgress();

        if (v == getPlannedButton)
        {

        }
    }

    public void startProgress() {
        // run network access on a separate thread;
        new Thread(new activity_plannedRoadworks.Task(pUrl)).start();
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
