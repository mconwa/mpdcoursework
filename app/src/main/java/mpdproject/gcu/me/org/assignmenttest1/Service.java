package mpdproject.gcu.me.org.assignmenttest1;

/**
 * Created by mconwa201 on 3/27/2018.
 */



import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;

import mpdproject.gcu.me.org.assignmenttest1.DataItem;
import mpdproject.gcu.me.org.assignmenttest1.parser;
import mpdproject.gcu.me.org.assignmenttest1.MainActivity;
import mpdproject.gcu.me.org.assignmenttest1.HttpActivity;

    public class Service extends IntentService {

        public static final String TAG = "MyService";
        public static final String MY_SERVICE_MESSAGE = "myServiceMessage";
        public static final String MY_SERVICE_PAYLOAD = "myServicePayload";
        public static final String MY_SERVICE_EXCEPTION = "myServiceException";

        public Service() {
            super("MyService");
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            Uri uri = intent.getData();
            Log.i(TAG, "onHandleIntent: " + uri.toString());

            String response;
            try {
                response = HttpActivity.downloadUrl(uri.toString(), "nadias", "NadiasPassword");
            } catch (IOException e) {
                e.printStackTrace();
                Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
                messageIntent.putExtra(MY_SERVICE_EXCEPTION, e.getMessage());
                LocalBroadcastManager manager =
                        LocalBroadcastManager.getInstance(getApplicationContext());
                manager.sendBroadcast(messageIntent);
                return;
            }

            DataItem[] dataItems = parser.parseFeed(response);

            Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
            messageIntent.putExtra(MY_SERVICE_PAYLOAD, dataItems);
            LocalBroadcastManager manager =
                    LocalBroadcastManager.getInstance(getApplicationContext());
            manager.sendBroadcast(messageIntent);
        }
        @Override
        public void onCreate() {
            super.onCreate();
            Log.i(TAG, "onCreate");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.i(TAG, "onDestroy");
        }

    }



