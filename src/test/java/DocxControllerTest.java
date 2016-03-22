import DTO.CoverDTO;
import junit.framework.TestCase;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;


/**
 * Created by vkukanauskas on 08/03/2016.
 */
public class DocxControllerTest extends TestCase {
    final String rootDir = System.getProperty("user.dir");
    final String templateDir = rootDir + "\\src\\test\\resources\\";
    final String templateUrl = templateDir + "test1.docx";

    @Test
    public void testGetNewDocxClassName() {
        WordprocessingMLPackage newDocx = DocxController.getNewDocx();
        Assert.assertEquals("org.docx4j.openpackaging.packages.WordprocessingMLPackage", newDocx.getClass().getName().toString());
    }

    @Test
    public void testGetNewDocxContentSize() {
        WordprocessingMLPackage newDocx = DocxController.getNewDocx();
        MainDocumentPart mainDocumentPart = newDocx.getMainDocumentPart();
        Assert.assertTrue(mainDocumentPart == null);
    }

    @Test
    public void testGetDocxFromTemplateByFile() {
        WordprocessingMLPackage newDocx = DocxController.getDocxFromTemplate(templateUrl);
        MainDocumentPart mainDocumentPart = newDocx.getMainDocumentPart();
        Assert.assertTrue(mainDocumentPart != null);
    }



    @Test
    public void testSaveDocxInPath() {
        WordprocessingMLPackage doc = getTestTemplate();
        String path = templateDir + "deleteMe.docx";
        File deleteMe = new File(path);
        deleteMe.delete();
        DocxController.saveDocxInPath(doc, path);
        File checkFile = new File(path);
        Assert.assertTrue(checkFile != null);
    }


    @Test
    public void testInsertHashmapIntoDocument() {
        CoverDTO dto = new CoverDTO("test Headline", "test name", "test_projekt");
        WordprocessingMLPackage document = getTestTemplate();
        DocxController.InsertDTOVariablesIntoDocx(document, dto);
    }
    

    //region private helpers


    private WordprocessingMLPackage getTestHashTemplate() {
        WordprocessingMLPackage document = null;
        try {
            document = WordprocessingMLPackage.load(new File(templateDir+"\\hashMapTest.docx"));
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
        return document;
    }

    private WordprocessingMLPackage getTestTemplate() {
        WordprocessingMLPackage document = null;
        try {
            document = WordprocessingMLPackage.load(new File(templateUrl));
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
        return document;
    }


    private HashMap<String, String> getTestHachMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("headline", "dynamische Headline");
        map.put("name", "dynamischer name");
        map.put("test123", "dynamisches test123");
        return  map;
    }

    
    //endregion private helpers

}