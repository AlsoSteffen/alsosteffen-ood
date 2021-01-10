import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Vector;

/**
 * <p>A slide. This class has drawing functionality.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide
{
    private final static int WIDTH = 1200;
    private final static int HEIGHT = 800;

    protected String title; //The title is kept separately
    protected Vector<SlideItem> items; //The SlideItems are kept in a vector

    public Slide()
    {
        items = new Vector<>();
    }

    public static int getWidth()
    {
        return WIDTH;
    }

    public static int getHeight()
    {
        return HEIGHT;
    }

    /**
     * Adds a slide item to the slide
     *
     * @param item SlideItem to add to the slide
     */
    public void append(SlideItem item)
    {
        items.addElement(item);
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String newTitle)
    {
        title = newTitle;
    }

    /**
     * Adds a TextItem to the slide
     *
     * @param level   style level of the TextItem
     * @param message message of the TextItem
     */
    public void append(int level, String message)
    {
        append(new TextItem(level, message));
    }

    public Vector<SlideItem> getSlideItems()
    {
        return items;
    }

    public int getSize()
    {
        return items.size();
    }

    /**
     * Draws the slide
     *
     * @param g    Graphics used to paint the slide
     * @param area Rectangle area of the slide to paint
     * @param view ImageObserver used in drawing the slide
     */
    public void draw(Graphics g, Rectangle area, ImageObserver view)
    {
        float scale = getScale(area);
        int y = area.y;

        // sets the slideItem to be the title
        SlideItem slideItem = new TextItem(0, getTitle());
        Style style;

        for (int number = 0; number < getSize(); number++)
        {
            style = Styles.getStyle(slideItem.getLevel());
            slideItem.draw(area.x, y, scale, g, style, view);
            y += slideItem.getBoundingBox(g, view, scale, style).height;

            slideItem = getSlideItems().elementAt(number);
        }
    }

    //Returns the scale to draw a slide
    private float getScale(Rectangle area)
    {
        return Math.min(((float) area.width) / ((float) WIDTH), ((float) area.height) / ((float) HEIGHT));
    }
}
