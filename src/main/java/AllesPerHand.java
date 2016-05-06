import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ObjectFactory;

import java.io.File;

/**
 * Created by vkukanauskas on 06/05/2016.
 */
public class AllesPerHand {

    private static ObjectFactory factory = Context.getWmlObjectFactory();
    private static String rootDir = System.getProperty("user.dir");
    private static String saveDir = "C:\\development\\VMs\\sharedFolder\\";
    private static String templateDir = rootDir + "\\src\\main\\resources\\";


    public static void main(String[] args) throws Exception{

        /**
         * als erstes erstelle ich einen Cover und fühle es mit DTO Werten
         */

        WordprocessingMLPackage reportDoc = WordprocessingMLPackage.load(new File(templateDir + "template_BASO_cover.docx"));
//        MainDocumentPart mainDocumentPart = reportDoc.getMainDocumentPart();
//        mainDocumentPart.variableReplace(Reflections.getHashmapFromDTO(getRandomCoverDTO()));

        reportDoc.save(new File(saveDir + "hand-step1.docx"));

    }
}
