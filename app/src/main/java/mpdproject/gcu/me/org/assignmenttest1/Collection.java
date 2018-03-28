package mpdproject.gcu.me.org.assignmenttest1;
/**
 * Created by mconwa201 on 3/19/2018.
 */
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;

public class Collection extends AppCompatActivity {

    private LinkedList<WidgetClass> alist;

    //alist = new LinkedList<WidgetClass>()
    //alist.add(aWidget)

    public LinkedList<WidgetClass>
    retrieveWidget()
    {
        return alist;
    }

}
