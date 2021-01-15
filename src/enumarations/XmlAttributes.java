package enumarations;

public enum XmlAttributes
{
    /**
     * Names of xml tags of attributes
     */
    SHOWTITLE("showtitle"),
    SLIDETITLE("title"),
    SLIDE("slide"),
    ITEM("item"),
    LEVEL("level"),
    KIND("kind"),
    TEXT("text"),
    IMAGE("image");


    private final String attribute;

    XmlAttributes(String attribute)
    {
        this.attribute = attribute;
    }

    public String getAttribute()
    {
        return this.attribute;
    }
}
