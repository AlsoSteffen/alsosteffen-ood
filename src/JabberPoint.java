/**
 * JabberPoint Main Program
 * <p>This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class JabberPoint
{
    protected static final String JABVERSION = "Jabberpoint 1.6 - OU version";

    /**
     * The main program
     */
    public static void main(String[] argv)
    {
        Styles.createStyles();

        Presentation presentation = new Presentation();

        new SlideViewerFrame(JABVERSION, presentation);

        if (argv.length == 0)
        {
            // Loads the demo presentation
            CreateDefaultDisplay.openDemoPresentation(presentation);
        }
        else
        {
            new XMLAccessor().loadFile(presentation, argv[0]);
        }

        presentation.setSlideNumber(0);
    }
}
