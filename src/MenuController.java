import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar
{

    protected static final String ABOUT = "About";
    protected static final String FILE = "File";
    protected static final String EXIT = "Exit";
    protected static final String GOTO = "Go to";
    protected static final String HELP = "Help";
    protected static final String NEW = "New";
    protected static final String NEXT = "Next";
    protected static final String OPEN = "Open";
    protected static final String PAGENR = "Page number?";
    protected static final String PREV = "Prev";
    protected static final String SAVE = "Save";
    protected static final String VIEW = "View";

    protected static final String TESTFILE = "testPresentation.xml";
    protected static final String SAVEFILE = "savedPresentation.xml";

    protected static final String IOEX = "IO Exception: ";

    protected static final String LOADERR = "Load Error";
    protected static final String SAVEERR = "Save Error";

    private final XMLAccessor xmlAccessor;
    private MenuItem menuItem;

    private static final long serialVersionUID = 227L;

    private final Frame parent; //The frame, only used as parent for the Dialogs

    public MenuController(Frame frame, Presentation pres)
    {
        parent = frame;

        this.xmlAccessor = new XMLAccessor();

        // Commands are given to the presentation
        Menu fileMenu = new Menu(FILE);
        add(fileMenu);
        Menu viewMenu = new Menu(VIEW);
        add(viewMenu);
        Menu helpMenu = new Menu(HELP);
        add(helpMenu);

        createOpenPresentation(fileMenu, pres);
        createNewPresentation(fileMenu, pres);
        createSavePresentation(fileMenu, pres);
        createExitPresentation(fileMenu);

        createNextSlide(viewMenu, pres);
        createPreviousSlide(viewMenu, pres);
        createGoTo(viewMenu, pres);

        createAbout(helpMenu, pres);
    }

    /**
     * Creates a new menu item
     *
     * @param name name of the item to be added
     * @return MenuItem - the MenuItem to add to the menu
     */
    public MenuItem mkMenuItem(String name)
    {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }

    /**
     * Creates functionality for opening a presentation
     *
     * @param menu         the menu to add the menuItem to
     * @param presentation the presentation the functionality applies to
     */
    private void createOpenPresentation(Menu menu, Presentation presentation)
    {
        menu.add(this.menuItem = mkMenuItem(OPEN));

        this.menuItem.addActionListener(actionEvent ->
                                        {
                                            presentation.clear();
                                            try
                                            {
                                                this.xmlAccessor.loadFile(presentation, TESTFILE);
                                                presentation.setSlideNumber(0);
                                            }
                                            catch (IOException exc)
                                            {
                                                JOptionPane.showMessageDialog(this.parent, IOEX + exc,
                                                                              LOADERR, JOptionPane.ERROR_MESSAGE);
                                            }
                                            parent.repaint();
                                        });
    }


    /**
     * Creates functionality for creating a new presentation
     *
     * @param menu         the menu to add the menuItem to
     * @param presentation the presentation the functionality applies to
     */
    private void createNewPresentation(Menu menu, Presentation presentation)
    {
        menu.add(this.menuItem = mkMenuItem(NEW));
        this.menuItem.addActionListener(actionEvent ->
                                        {
                                            presentation.clear();
                                            this.parent.repaint();
                                        });
    }


    /**
     * Creates functionality for saving a presentation
     *
     * @param menu         the menu to add the menuItem to
     * @param presentation the presentation the functionality applies to
     */
    private void createSavePresentation(Menu menu, Presentation presentation)
    {
        menu.add(this.menuItem = mkMenuItem(SAVE));
        this.menuItem.addActionListener(e ->
                                        {
                                            try
                                            {
                                                this.xmlAccessor.saveFile(presentation, SAVEFILE);
                                            }
                                            catch (IOException exc)
                                            {
                                                JOptionPane.showMessageDialog(this.parent, IOEX + exc,
                                                                              SAVEERR, JOptionPane.ERROR_MESSAGE
                                                                             );
                                            }
                                        });
    }


    /**
     * Creates functionality for exiting the presentation
     *
     * @param menu the menu to add the menuItem to
     */
    private void createExitPresentation(Menu menu)
    {
        menu.addSeparator();
        menu.add(this.menuItem = mkMenuItem(EXIT));
        this.menuItem.addActionListener(actionEvent -> System.exit(0));
    }


    /**
     * Creates functionality for going to the next slide of a presentation
     *
     * @param menu         the menu to add the menuItem to
     * @param presentation the presentation the functionality applies to
     */
    private void createNextSlide(Menu menu, Presentation presentation)
    {
        menu.add(this.menuItem = mkMenuItem(NEXT));
        this.menuItem.addActionListener(actionEvent -> presentation.nextSlide());
    }


    /**
     * Creates functionality for going to the previous slide of a presentation
     *
     * @param menu         the menu to add the menuItem to
     * @param presentation the presentation the functionality applies to
     */
    private void createPreviousSlide(Menu menu, Presentation presentation)
    {
        menu.add(this.menuItem = mkMenuItem(PREV));
        this.menuItem.addActionListener(actionEvent -> presentation.prevSlide());
    }


    /**
     * Creates functionality for going to a specific slide of the presentation
     *
     * @param menu         the menu to add the menuItem to
     * @param presentation the presentation the functionality applies to
     */
    private void createGoTo(Menu menu, Presentation presentation)
    {
        menu.add(this.menuItem = mkMenuItem(GOTO));
        this.menuItem.addActionListener(actionEvent ->
                                        {
                                            String pageNumberStr = JOptionPane.showInputDialog(PAGENR);

                                            int pageNumber = Integer.parseInt(pageNumberStr);

                                            presentation.setSlideNumber(pageNumber - 1);
                                        });
    }


    /**
     * Creates functionality for the about window
     *
     * @param menu         the menu to add the menuItem to
     * @param presentation the presentation the functionality applies to
     */
    private void createAbout(Menu menu, Presentation presentation)
    {
        menu.add(this.menuItem = mkMenuItem(ABOUT));
        this.menuItem.addActionListener(actionEvent -> CreateDefaultDisplay.openAboutBox(this.parent));
    }

}
