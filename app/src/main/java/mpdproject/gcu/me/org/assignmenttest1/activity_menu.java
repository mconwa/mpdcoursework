package mpdproject.gcu.me.org.assignmenttest1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_menu extends AppCompatActivity implements View.OnClickListener{

    private TextView menuText;
    private Button incidentsButton;
    private Button floodlineButton;
    private Button roadworksButton;
    private Button plannedRoadworksButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        menuText = (TextView) findViewById(R.id.menuText);
        incidentsButton = (Button) findViewById(R.id.incidentsButton);
        incidentsButton.setOnClickListener(this);
        roadworksButton = (Button) findViewById(R.id.roadworksButton);
        roadworksButton.setOnClickListener(this);
        plannedRoadworksButton = (Button) findViewById(R.id.plannedRoadworksButton);
        plannedRoadworksButton.setOnClickListener(this);
        floodlineButton = (Button) findViewById(R.id.floodlineButton);
        floodlineButton.setOnClickListener(this);
    } // End of onCreate

    @Override
    public void onClick(View v)
    {
        // Check if activity_incidents button has been clicked
        if (v == incidentsButton)
        {
            startActivity(new Intent(activity_menu.this, activity_incidents.class));
        }
        // Check if activity_roadworks button has been clicked
        else if (v == roadworksButton)
        {
            startActivity(new Intent(activity_menu.this, activity_roadworks.class));
        }
        // Check if activity_plannedRoadworks has been clicked
        else if (v == plannedRoadworksButton)
        {
            startActivity(new Intent(activity_menu.this, activity_plannedRoadworks.class));
        }
        // Check if activity_floodline button has been clicked
        else if (v == floodlineButton)
        {
            startActivity(new Intent(activity_menu.this, activity_floodline.class));
        }
    } // End of onClick
}
