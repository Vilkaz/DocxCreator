import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;

import java.io.File;

/**
 * Created by vkukanauskas on 08/03/2016.
 */
public class DocxController {

    private static ObjectFactory factory = Context.getWmlObjectFactory();

    public static WordprocessingMLPackage getNewDocx() {
        return new WordprocessingMLPackage();
    }

    public static WordprocessingMLPackage getDocxFromTemplate(String templateURL) {

        WordprocessingMLPackage wordMLPackage = null;
        try {
            wordMLPackage = WordprocessingMLPackage
                    .load(new File(templateURL));
        } catch (Docx4JException e) {
            e.printStackTrace();
        }

        return wordMLPackage;
    }

    public static void InsertDTOVariablesIntoDocx(WordprocessingMLPackage Docx, Object dto) {
        MainDocumentPart mainDocumentPart = Docx.getMainDocumentPart();
    }





    public static StatusReport saveDocxInPath(WordprocessingMLPackage document, String path) {
        StatusReport statusReport = new StatusReport();
        try {
            document.save(new File(path) );
            statusReport.setStatus("ok");
        } catch (Docx4JException e) {
            e.printStackTrace();
            statusReport.setStatus("exception");
            statusReport.setException(e);
        }
        return  statusReport;
    }

    public static void addPageBreak(WordprocessingMLPackage document) {
        MainDocumentPart documentPart = document.getMainDocumentPart();
        P p = PController.getPageBreakP();
        documentPart.addObject(p);
    }


}
