package mpdproject.gcu.me.org.assignmenttest1;

/**
 * Created by mconwa201 on 3/27/2018.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class NetworkHelper {

    public static boolean hasNetworkAccess(Context context) {

        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
