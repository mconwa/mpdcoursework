package mpdproject.gcu.me.org.assignmenttest1;

/**
 * Created by mconwa201 on 3/19/2018.
 */

public class WidgetClass {

    private String title;
    private String description;
    private String coordinates;

    public WidgetClass()
    {
        title = "";
        description = "";
        coordinates = "";
    }

    public WidgetClass(String atitle,String adescription,String acoordinates)
    {
        title = atitle;
        description = adescription;
        coordinates = acoordinates;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String atitle) {title = atitle;}

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String adescription)
    {
        description = adescription;
    }

    public String getCoordinates()
    {
        return coordinates;
    }

    public void setCoordinates(String acoordinates)
    {
        coordinates = acoordinates;
    }

    public String toString()
    {
        String temp;

        temp = "Title: " + title +
                "Description: " + description +
                "Coordinates: " + coordinates;

        return temp;
    }

} // End of class
