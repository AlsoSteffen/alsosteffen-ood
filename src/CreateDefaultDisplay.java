import javax.swing.*;
import java.awt.*;

public class CreateDefaultDisplay
{
    public static final String DEMO_NAME = "Demo presentation";

    public static void openAboutBox(Component parent)
    {
        String message = "JabberPoint is a primitive slide-show program in Java(tm). It\n" +
                "is freely copyable as long as you keep this notice and\n" +
                "the splash screen intact.\n" +
                "Copyright (c) 1995-1997 by Ian F. Darwin, ian@darwinsys.com.\n" +
                "Adapted by Gert Florijn (version 1.1) and " +
                "Sylvia Stuurman (version 1.2 and higher) for the Open" +
                "University of the Netherlands, 2002 -- now.\n" +
                "Author's version available from http://www.darwinsys.com/";

        String title = "About JabberPoint";

        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void openDemoPresentation(Presentation presentation)
    {
        presentation.setTitle(DEMO_NAME);
        Slide slide = new Slide();

        slide.setTitle("JabberPoint");
        slide.append(1, "The Java prestentation tool");
        slide.append(2, "Copyright (c) 1996-2000: Ian Darwin");
        slide.append(2, "Copyright (c) 2000-now:");
        slide.append(2, "Gert Florijn and Sylvia Stuurman");
        slide.append(4, "Calling Jabberpoint without a filename");
        slide.append(4, "will show this presentation");
        slide.append(1, "Navigate:");
        slide.append(3, "Next slide: PgDn or Enter");
        slide.append(3, "Previous slide: PgUp or up-arrow");
        slide.append(3, "Quit: q or Q");
        presentation.append(slide);

        slide = new Slide();
        slide.setTitle("Demonstration of levels and styles");
        slide.append(1, "Level 1");
        slide.append(2, "Level 2");
        slide.append(1, "Again level 1");
        slide.append(1, "Level 1 has style number 1");
        slide.append(2, "Level 2 has style number 2");
        slide.append(3, "This is how level 3 looks like");
        slide.append(4, "And this is level 4");
        presentation.append(slide);

        slide = new Slide();
        slide.setTitle("The third slide");
        slide.append(1, "To open a new presentation,");
        slide.append(2, "use File->Open from the menu.");
        slide.append(1, " ");
        slide.append(1, "This is the end of the presentation.");
        slide.append(new BitmapItem(1, "media/JabberPoint.jpg"));
        presentation.append(slide);
    }
}
