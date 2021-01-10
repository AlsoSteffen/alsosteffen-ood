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
    private Font labelFont = null; //The font for labels
    private Presentation presentation = null; //The presentation
    private JFrame frame = null;

    private static final long serialVersionUID = 227L;

    private static final Color BGCOLOR = Color.white;
    private static final Color COLOR = Color.black;
    private static final String FONTNAME = "Dialog";
    private static final int FONTSTYLE = Font.BOLD;
    private static final int FONTHEIGHT = 10;
    private static final int XPOS = 1100;
    private static final int YPOS = 20;

    public SlideViewerComponent(Presentation pres, JFrame frame)
    {
        setBackground(BGCOLOR);

        presentation = pres;

        labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);

        this.frame = frame;
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(Slide.getWidth(), Slide.getHeight());
    }

    public void update(Presentation presentation, Slide data)
    {
        if (data == null)
        {
            repaint();
            return;
        }

        this.presentation = presentation;

        this.slide = data;

        repaint();

        frame.setTitle(presentation.getTitle());
    }

    //Draw the slide
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

    private void paintText(Graphics g, Color color)
    {
        g.setFont(Font.getFont(Font.SERIF));
        g.setColor(color);
        g.drawString("Slide " + (1 + presentation.getSlideNumber()) + " of " +
                             presentation.getSize(), XPOS, YPOS);
    }

    private void paintBackground(Graphics g, Color color)
    {
        g.setColor(color);
        g.fillRect(0, 0, getSize().width, getSize().height);
    }

    private Rectangle getTextArea()
    {
        return new Rectangle(0, YPOS, getWidth(), (getHeight() - YPOS));
    }
}
