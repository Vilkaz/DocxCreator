import junit.framework.TestCase;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.HashMap;


/**
 * Created by vkukanauskas on 08/03/2016.
 */
public class DocxControllerTest extends TestCase {
    final String rootDir = System.getProperty("user.dir");
    final String templateDir  = rootDir+"/src/test/resources/";
    final String templateUrl = templateDir+"test1.docx";

    @Test
    public void testGetNewDocxClassName(){
        WordprocessingMLPackage newDocx = DocxController.getNewDocx();
        Assert.assertEquals("org.docx4j.openpackaging.packages.WordprocessingMLPackage",newDocx.getClass().getName().toString());
    }

    @Test
    public void testGetNewDocxContentSize(){
        WordprocessingMLPackage newDocx = DocxController.getNewDocx();
        MainDocumentPart mainDocumentPart = newDocx.getMainDocumentPart();
       Assert.assertTrue(mainDocumentPart==null);
    }

    @Test
    public void testGetDocxFromTemplateByFile(){
        WordprocessingMLPackage newDocx = DocxController.getDocxFromTemplate(templateUrl);
        MainDocumentPart mainDocumentPart = newDocx.getMainDocumentPart();
        Assert.assertTrue(mainDocumentPart!=null);
    }

    @Test
    public void testGetHashmapFromDTO(){
        CoverDTO dto = new CoverDTO("test Headline", "test name", "test_projekt");
        HashMap<String, String> mapping =  DocxController.getHashmapFromDTO(dto);
        Assert.assertTrue(mapping.get("headline").equals("test Headline"));
        Assert.assertTrue(mapping.get("name").equals("test name"));
        Assert.assertTrue(mapping.get("project_type").equals("test_projekt"));
    }


    @Test
    public void testInsertHashmapIntoTemplate(){
        HashMap<String, String> mappings = new HashMap<String, String>();
        mappings.put("headline", "test Headline");
        mappings.put("name", "test name");
        mappings.put("project_type", "test_projekt");

    }




}