import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * <p>The applicatiewindow for a slideviewcomponent</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerFrame extends JFrame
{
    private static final long serialVersionUID = 3227L;

    public final static int WIDTH = 1200;
    public final static int HEIGHT = 800;

    public SlideViewerFrame(String title, Presentation presentation)
    {
        super(title);

        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);


        setFrameControllers(presentation);
        setupWindow(slideViewerComponent.getPreferredSize());
        setWithSlideViewerComponent(slideViewerComponent, presentation);
    }

    public void setupWindow(Dimension dimension)
    {
        // makes sure that the application quits when x button is pressed
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        this.setSize(dimension);

        this.setVisible(true);
    }

    public void setWithSlideViewerComponent(SlideViewerComponent slideViewerComponent, Presentation presentation)
    {
        presentation.setShowView(slideViewerComponent);

        getContentPane().add(slideViewerComponent);
    }

    public void setFrameControllers(Presentation presentation)
    {
        this.addKeyListener(new KeyController(presentation));

        this.setMenuBar(new MenuController(this, presentation));
    }
}
