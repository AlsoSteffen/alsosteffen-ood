import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * <p>The abstract class for items on a slide.<p>
 * <p>All SlideItems have drawing capabilities.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public abstract class SlideItem
{
    private int level = 0; //The level of the SlideItem

    public SlideItem(int lev)
    {
        level = lev;
    }

    public int getLevel()
    {
        return level;
    }

    public abstract Rectangle getBoundingBox(Graphics g,
                                             ImageObserver observer, float scale, Style style);

    public abstract void draw(int x, int y, float scale,
                              Graphics g, Style style, ImageObserver observer);
}
