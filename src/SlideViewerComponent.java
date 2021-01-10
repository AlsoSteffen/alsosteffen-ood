import javax.swing.*;
import java.awt.*;


/**
 * <p>SlideViewerComponent is a graphical component that ca display Slides.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent
{

    private Slide slide; //The current slide
    private final JFrame frame;
    private Presentation presentation; //The presentation

    private static final long serialVersionUID = 227L;

    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;

    public SlideViewerComponent(Presentation pres, JFrame frame)
    {
        setBackground(BGCOLOR);

        presentation = pres;

        this.frame = frame;
    }

    /**
     * Gets the default dimension of the component
     *
     * @return Dimension - the default dimensions of the component
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(Slide.getWidth(), Slide.getHeight());
    }

    /**
     * Updates the contents of the slide
     *
     * @param presentation the presentation to update the contents
     * @param data         the data to add to the updated slide
     */
    public void update(Presentation presentation, Slide data)
    {
        if (data == null)
        {
            return;
        }

        this.presentation = presentation;
        this.slide = data;

        repaint();

        frame.setTitle(presentation.getTitle());
    }

    /**
     * paints the components of the slide
     *
     * @param g Graphics used in painting the components
     */
    public void paintComponent(Graphics g)
    {
        paintBackground(g, BGCOLOR);

        if (presentation.getSlideNumber() < 0 || slide == null)
        {
            return;
        }

        paintText(g, COLOR);

        slide.draw(g, getTextArea(), this);
    }

    /**
     * Paints the text of the slide
     *
     * @param g     Graphics used in painting the text
     * @param color Color to paint the text
     */
    private void paintText(Graphics g, Color color)
    {
        g.setFont(Font.getFont(Font.SERIF));
        g.setColor(color);
        g.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " +
                             presentation.getSize(), XPOS, YPOS);
    }

    /**
     * Paints the background of the slide
     *
     * @param g     Graphics used in painting the background
     * @param color Color to paint the background
     */
    private void paintBackground(Graphics g, Color color)
    {
        g.setColor(color);
        g.fillRect(0, 0, getSize().width, getSize().height);
    }

    /**
     * Gets the area the text is painted in
     *
     * @return Rectangle - sized according to the slide
     */
    private Rectangle getTextArea()
    {
        return new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
    }
}
