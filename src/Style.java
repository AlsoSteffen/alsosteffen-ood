import java.awt.*;

/**
 * <p>Style stands for Indent, Color, Font and Leading.</p>
 * <p>The link between a style number and a item level is hard-linked:
 * in Slide the style is grabbed for an item
 * with a style number the same as the item level.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Style
{
    private static final String FONTNAME = "Helvetica";
    private final int indent;
    private final Color color;
    private final Font font;
    private final int fontSize;
    private final int leading;

    public Style(int indent, Color color, int points, int leading)
    {
        this.indent = indent;
        this.color = color;
        font = new Font(FONTNAME, Font.BOLD, fontSize = points);
        this.leading = leading;
    }


    public String toString()
    {
        return "[" + indent + "," + color + "; " + fontSize + " on " + leading + "]";
    }

    public Font getFont(float scale)
    {
        return font.deriveFont(fontSize * scale);
    }

    public int getIndent()
    {
        return indent;
    }

    public Color getColor()
    {
        return color;
    }

    public int getLeading()
    {
        return leading;
    }
}
