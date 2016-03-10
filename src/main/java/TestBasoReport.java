import DTO.BasoCoverDTO;
import DTO.BasoTable1DTO;
import DTO.CoverDTO;
import org.apache.commons.lang.RandomStringUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBIntrospector;
import java.io.File;

/**
 * Created by vkukanauskas on 10/03/2016.
 */
public class TestBasoReport {

    private static ObjectFactory factory = Context.getWmlObjectFactory();
    private static String rootDir = System.getProperty("user.dir");
    private static String templateDir = rootDir + "\\src\\main\\resources\\";

    public static void main(String[] args) throws Exception {

        /**
         * als erstes erstelle ich einen Cover und fühle es mit DTO Werten
         */

        WordprocessingMLPackage reportDoc = WordprocessingMLPackage.load(new File(templateDir + "template_BASO_cover.docx"));
        MainDocumentPart mainDocumentPart = reportDoc.getMainDocumentPart();
        mainDocumentPart.variableReplace(DocxController.getHashmapFromDTO(getRandomCoverDTO()));
        reportDoc.save(new File(templateDir + "step1-CoverWithDTOData.docx"));

        /**
         * Nun wird das Zweite template mit Daten gefüllt und hinzugefügt.
         */

        DocxController.addPageBreak(reportDoc);
        WordprocessingMLPackage table1Doc = WordprocessingMLPackage.load(new File(templateDir + "template_BASO_Power_Systems_Table1.docx"));
        MainDocumentPart table1MainDoc = table1Doc.getMainDocumentPart();

        table1MainDoc.variableReplace(DocxController.getHashmapFromDTO(getTable1RandomDTO()));

        /**
         * da die variablen nicht das Bild ersetzen können, ersetzen wir dies mit einer eigener Funktion
         */
        P imageP = PController.getPWithImage(templateDir + "tecracer.png", reportDoc);
        Tbl tbl = (Tbl) JAXBIntrospector.getValue(table1MainDoc.getContent().get(2));
//        tbl.setTblPr(TableController.getTblPrByWidth(5500));
        Tc cell = TableController.getCellWithValue(tbl, "set_img");
        cell.getContent().add(imageP);


        mainDocumentPart.getContent().addAll(table1MainDoc.getContent());
        reportDoc.save(new File(templateDir + "step2-CoverAndTable1.docx"));


        /**
         * nun machen fügen wir tabelle 3 hin und machen das format = queer
         */


    }





    private static BasoTable1DTO getTable1RandomDTO() {
        return new BasoTable1DTO(
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString(),
                getRandomString()
        );
    }


    private static BasoCoverDTO getRandomCoverDTO() {
        return new BasoCoverDTO(getRandomString(), getRandomString(), getRandomString());
    }

    private static String getRandomString() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
