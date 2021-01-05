import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * <p>This is the KeyController (KeyListener)</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class KeyController extends KeyAdapter
{
    private final Presentation presentation; //Commands are given to the presentation

    public KeyController(Presentation p)
    {
        this.presentation = p;
    }

    public void keyPressed(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_ADD:
            case KeyEvent.VK_PLUS:
                this.presentation.nextSlide();
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_SUBTRACT:
            case KeyEvent.VK_MINUS:
                this.presentation.prevSlide();
                break;
            case KeyEvent.VK_Q:
                System.exit(0);
            default:
                break;
        }
    }
}
