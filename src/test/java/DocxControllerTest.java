import junit.framework.TestCase;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.junit.Assert;


/**
 * Created by vkukanauskas on 08/03/2016.
 */
public class DocxControllerTest extends TestCase {

    public void testGetNewDocxClassName(){
        WordprocessingMLPackage newDocx = DocxController.getNewDocx();
        Assert.assertEquals("org.docx4j.openpackaging.packages.WordprocessingMLPackage",newDocx.getClass().getName().toString());
    }

    public void testGetNewDocxContentSize(){
        WordprocessingMLPackage newDocx = DocxController.getNewDocx();
        MainDocumentPart mainDocumentPart = newDocx.getMainDocumentPart();
       Assert.assertTrue(mainDocumentPart==null);
    }


}