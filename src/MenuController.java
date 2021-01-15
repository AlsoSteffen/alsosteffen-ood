import enumarations.Controls;

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
    protected static final String TESTFILE = "testPresentation.xml",
            SAVEFILE = "savedPresentation.xml";

    protected static final String IOEX = "IO Exception: ";

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
        Menu fileMenu = new Menu(Controls.NEW.getControl());
        this.add(fileMenu);
        Menu viewMenu = new Menu(Controls.VIEW.getControl());
        this.add(viewMenu);
        Menu helpMenu = new Menu(Controls.HELP.getControl());
        this.add(helpMenu);

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
        menu.add(this.menuItem = mkMenuItem(Controls.OPEN.getControl()));

        this.menuItem.addActionListener(actionEvent ->
                                        {
                                            presentation.clear();
                                            this.xmlAccessor.loadFile(presentation, TESTFILE);
                                            presentation.setSlideNumber(0);
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
        menu.add(this.menuItem = mkMenuItem(Controls.NEW.getControl()));
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
        menu.add(this.menuItem = mkMenuItem(Controls.SAVE.getControl()));
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
        menu.add(this.menuItem = mkMenuItem(Controls.EXIT.getControl()));
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
        menu.add(this.menuItem = mkMenuItem(Controls.NEXT.getControl()));
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
        menu.add(this.menuItem = mkMenuItem(Controls.PREV.getControl()));
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
        menu.add(this.menuItem = mkMenuItem(Controls.GOTO.getControl()));
        this.menuItem.addActionListener(actionEvent ->
                                        {
                                            String pageNumberStr = JOptionPane.showInputDialog(Controls.PAGENR.getControl());

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
        menu.add(this.menuItem = mkMenuItem(Controls.ABOUT.getControl()));
        this.menuItem.addActionListener(actionEvent -> CreateDefaultDisplay.openAboutBox(this.parent));
    }

}
