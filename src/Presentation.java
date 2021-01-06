import java.util.ArrayList;


/**
 * <p>Presentations keeps track of the slides in a presentation.</p>
 * <p>Only one instance of this class is available.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation
{
    private String showTitle;
    private ArrayList<Slide> showList;
    private int currentSlideNumber;
    private SlideViewerComponent slideViewComponent;

    public Presentation()
    {
        this.showList = null;
        this.currentSlideNumber = 0;
        slideViewComponent = null;
        clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent)
    {
        this.slideViewComponent = slideViewerComponent;
        clear();
    }

    public int getSize()
    {
        return showList.size();
    }

    public String getTitle()
    {
        return showTitle;
    }

    public void setTitle(String nt)
    {
        showTitle = nt;
    }

    public void setShowView(SlideViewerComponent slideViewerComponent)
    {
        this.slideViewComponent = slideViewerComponent;
    }

    //Returns the number of the current slide
    public int getSlideNumber()
    {
        return currentSlideNumber;
    }

    /**
     * Sets the slide number at startup and when going to specific slides
     *
     * @param number the slide number to go - if the number isn't within
     *               the range of the slide numbers, it goes to the lowest/highest
     *               in the direction instead.
     */
    public void setSlideNumber(int number)
    {
        currentSlideNumber = number;

        setCurrentSlideValid();

        if (slideViewComponent != null)
        {
            slideViewComponent.update(this, getCurrentSlide());
        }
    }

    public void setCurrentSlideValid()
    {
        if (this.currentSlideNumber < 0)
        {
            this.currentSlideNumber = 0;
        }
        else if (this.currentSlideNumber > this.getSize() - 1)
        {
            this.currentSlideNumber = this.getSize() - 1;
        }
    }

    /**
     * navigates to the previous slide unless the presentation is at the first slide
     */
    public void prevSlide()
    {
        if (currentSlideNumber > 0)
        {
            setSlideNumber(currentSlideNumber - 1);
        }
    }

    /**
     * navigates to the next slide unless the presentation is at the last slide
     */
    public void nextSlide()
    {
        if (currentSlideNumber < (showList.size() - 1))
        {
            setSlideNumber(currentSlideNumber + 1);
        }
    }

    //Remove the presentation
    void clear()
    {
        showList = new ArrayList<>();
        setSlideNumber(-1);
    }

    //Add a slide to the presentation
    public void append(Slide slide)
    {
        showList.add(slide);
    }

    //Return a slide with a specific number
    public Slide getSlide(int number)
    {
        if (number < 0 || number >= getSize())
        {
            return null;
        }
        return showList.get(number);
    }

    //Return the current slide
    public Slide getCurrentSlide()
    {
        return getSlide(currentSlideNumber);
    }

}
