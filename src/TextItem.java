import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A text item.</p>
 * <p>A text item has drawing capabilities.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem
{
    private final String text;

    private static final String EMPTYTEXT = "No Text Given";

    //A textitem of int level with text string
    public TextItem(int level, String string)
    {
        super(level);
        text = string;
    }

    public TextItem()
    {
        this(0, EMPTYTEXT);
    }

    public String getText()
    {
        return text == null ? EMPTYTEXT : text;
    }

    /**
     * Returns the AttributedString for the Item
     *
     * @param style the Style by the String
     * @param scale the scale used by the String
     * @return AttributedString
     */
    public AttributedString getAttributedString(Style style, float scale)
    {
        AttributedString attrStr = new AttributedString(getText());

        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());

        return attrStr;
    }

    /**
     * Returns the bounding box of an item
     *
     * @param g        the graphics used to draw on the bounding box
     * @param observer ImageObserver used while the image is being drawn
     * @param scale    the scale used by the text
     * @param myStyle  the style used by the text
     * @return Rectangle - has the size of the boundingBox
     */
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer,
                                    float scale, Style myStyle)
    {
        List<TextLayout> layouts = getLayouts(g, myStyle, scale);

        int xsize = 0, ysize = (int) (myStyle.leading * scale);

        for (TextLayout layout : layouts)
        {
            Rectangle2D bounds = layout.getBounds();

            if (bounds.getWidth() > xsize)
            {
                xsize = (int) bounds.getWidth();
            }

            if (bounds.getHeight() > 0)
            {
                ysize += bounds.getHeight();
            }

            ysize += layout.getLeading() + layout.getDescent();
        }

        return new Rectangle((int) (myStyle.indent * scale), 0, xsize, ysize);
    }

    /**
     * Draws a TextItem
     *
     * @param x       the x axis starting point for drawing
     * @param y       the y axis starting point for drawing
     * @param scale   the scale used to draw the item
     * @param g       the Graphics used to draw the item
     * @param myStyle the style used to draw the item
     * @param o       ImageObserver used while the item is being drawn
     */
    public void draw(int x, int y, float scale, Graphics g,
                     Style myStyle, ImageObserver o)
    {
        if (text == null || text.length() == 0)
        {
            return;
        }

        List<TextLayout> layouts = getLayouts(g, myStyle, scale);

        Point pen = new Point(x + (int) (myStyle.indent * scale),
                              y + (int) (myStyle.leading * scale));

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(myStyle.color);

        for (TextLayout layout : layouts)
        {
            pen.y += layout.getAscent();

            layout.draw(g2d, pen.x, pen.y);

            pen.y += layout.getDescent();
        }
    }

    /**
     * Gets the TextLayouts of TextItems
     *
     * @param g     Graphics used in the text layouts
     * @param s     Style used in the text layouts
     * @param scale scale used by the text layouts
     * @return 'List<TextLayout>' - List of all the layouts used
     */
    private List<TextLayout> getLayouts(Graphics g, Style s, float scale)
    {
        List<TextLayout> layouts = new ArrayList<>();

        AttributedString attrStr = getAttributedString(s, scale);

        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();

        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);

        float wrappingWidth = (Slide.WIDTH - s.indent) * scale;

        while (measurer.getPosition() < getText().length())
        {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }

        return layouts;
    }

    /**
     * returns the TextItem level and contained text
     *
     * @return String
     */
    public String toString()
    {
        return "TextItem[" + getLevel() + "," + getText() + "]";
    }
}
