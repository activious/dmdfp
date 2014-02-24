package dmdfp;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * Created by khk on 2/21/14.
 */
public class Helper
{
    public static void printXML(Document doc)
    {
        try
        {
            new XMLOutputter(Format.getPrettyFormat()).output(doc, System.out);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void printXML(Element elm)
    {
        try
        {
            new XMLOutputter(Format.getPrettyFormat()).output(elm, System.out);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
