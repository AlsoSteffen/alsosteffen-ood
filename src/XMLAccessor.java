import enumarations.XmlAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;


/**
 * XMLAccessor, reads and writes XML files
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor
{

    /**
     * Default API to use.
     */
    protected static final String DEFAULT_API_TO_USE = "dom";

    /**
     * Text of messages
     */
    protected static final String PCE = "Parser Configuration Exception",
            UNKNOWNTYPE = "Unknown Element type",
            NFE = "Number Format Exception";


    private String getTitle(Element element, String tagName)
    {
        NodeList titles = element.getElementsByTagName(tagName);
        return titles.item(0).getTextContent();
    }

    /**
     * Loads a file
     *
     * @param presentation - the Presentation to add the file contents to
     * @param filename     - the name of the file to load
     */
    public void loadFile(Presentation presentation, String filename)
    {
        try
        {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(filename)); //Create a JDOM document
            Element doc = document.getDocumentElement();
            NodeList slides = doc.getElementsByTagName(XmlAttributes.SLIDE.getAttribute());

            presentation.setTitle(getTitle(doc, XmlAttributes.SLIDETITLE.getAttribute()));


            loadSlideElements(slides, presentation);
        }
        catch (IOException iox)
        {
            System.err.println(iox.toString());
        }
        catch (SAXException sax)
        {
            System.err.println(sax.getMessage());
        }
        catch (ParserConfigurationException pcx)
        {
            System.err.println(PCE);
        }
    }

    /**
     * Loads the slide elements of NodeList slides
     *
     * @param slides       - the list of slides to load elements from
     * @param presentation - the Presentation to add the elements to
     */
    private void loadSlideElements(NodeList slides, Presentation presentation)
    {
        int slideNumber, itemNumber, max, maxItems;
        Slide slide;

        max = slides.getLength();

        for (slideNumber = 0; slideNumber < max; slideNumber++)
        {
            Element xmlSlide = (Element) slides.item(slideNumber);

            slide = new Slide();
            slide.setTitle(getTitle(xmlSlide, XmlAttributes.SLIDETITLE.getAttribute()));

            presentation.append(slide);

            NodeList slideItems = xmlSlide.getElementsByTagName(XmlAttributes.ITEM.getAttribute());
            maxItems = slideItems.getLength();
            for (itemNumber = 0; itemNumber < maxItems; itemNumber++)
            {
                Element item = (Element) slideItems.item(itemNumber);
                loadSlideItem(slide, item);
            }
        }
    }

    private void loadSlideItem(Slide slide, Element item)
    {
        NamedNodeMap attributes = item.getAttributes();
        String textLevel = attributes.getNamedItem(XmlAttributes.LEVEL.getAttribute()).getTextContent();

        int level = loadTextLevel(textLevel);

        String type = attributes.getNamedItem(XmlAttributes.KIND.getAttribute()).getTextContent();

        if (XmlAttributes.TEXT.getAttribute().equals(type))
        {
            slide.append(new TextItem(level, item.getTextContent()));
        }
        else if (XmlAttributes.IMAGE.getAttribute().equals(type))
        {
            slide.append(new BitmapItem(level, item.getTextContent()));
        }
        else
        {
            System.err.println(UNKNOWNTYPE);
        }
    }

    private int loadTextLevel(String textLevel)
    {
        if (textLevel != null)
        {
            try
            {
                return Integer.parseInt(textLevel);
            }
            catch (NumberFormatException x)
            {
                System.err.println(NFE);
            }
        }
        return 1;
    }

    public void saveFile(Presentation presentation, String filename) throws IOException
    {
        PrintWriter out = new PrintWriter(new FileWriter(filename));
        out.println("<?xml version=\"1.0\"?>");
        out.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");

        saveXmlPresentation(out, presentation);

        out.close();
    }

    private void saveXmlItem(PrintWriter out, SlideItem slideItem)
    {
        out.print("<item kind=");
        if (slideItem instanceof TextItem)
        {
            out.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
            out.print(((TextItem) slideItem).getText());
        }
        else
        {
            if (slideItem instanceof BitmapItem)
            {
                out.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
                out.print(((BitmapItem) slideItem).getImageName());
            }
            else
            {
                System.out.println("Ignoring " + slideItem);
            }
        }
        out.println("</item>");
    }

    private void saveXmlSlide(PrintWriter out, Slide slide)
    {
        out.println("<slide>");
        out.println("<title>" + slide.getTitle() + "</title>");
        Vector<SlideItem> slideItems = slide.getSlideItems();
        for (int itemNumber = 0; itemNumber < slideItems.size(); itemNumber++)
        {
            SlideItem slideItem = slideItems.elementAt(itemNumber);

            saveXmlItem(out, slideItem);
        }
        out.println("</slide>");
    }

    private void saveXmlPresentation(PrintWriter out, Presentation presentation)
    {
        out.println("<presentation>");

        out.print("<showtitle>");
        out.print(presentation.getTitle());
        out.println("</showtitle>");

        for (int slideNumber = 0; slideNumber < presentation.getSize(); slideNumber++)
        {
            Slide slide = presentation.getSlide(slideNumber);

            saveXmlSlide(out, slide);
        }
        out.println("</presentation>");
    }
}
