package enumarations;

public enum Controls
{
    // Text for File Menu
    EXIT("Exit"),
    NEW("New"),
    SAVE("Save"),
    OPEN("Open"),

    // Text for Help Menuy
    HELP("Help"),
    ABOUT("About"),

    // Text for View
    VIEW("View"),
    GOTO("Go to"),
    PAGENR("Page Number?"),
    PREV("Prev"),
    NEXT("Next");

    private final String control;

    Controls(String control)
    {
        this.control = control;
    }

    public String getControl()
    {
        return this.control;
    }
}
