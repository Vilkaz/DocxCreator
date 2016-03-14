import DTO.BasoCoverDTO;
import DTO.BasoTable1DTO;
import DTO.CoverDTO;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.Doc;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBIntrospector;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        mainDocumentPart.addStyledParagraphOfText("","");
        mainDocumentPart.getContent().add(PController.getLandscapeP());
        reportDoc.save(new File(templateDir + "step1-b-pageBreakAdded.docx"));
        WordprocessingMLPackage table1Doc = WordprocessingMLPackage.load(new File(templateDir + "template_BASO_Power_Systems_Table1.docx"));
        MainDocumentPart table1MainDoc = table1Doc.getMainDocumentPart();
        table1MainDoc.variableReplace(DocxController.getHashmapFromDTO(getTable1RandomDTO()));

        /**
         * da die variablen nicht das Bild ersetzen können, ersetzen wir dies mit einer eigener Funktion
         */
        P imageP = PController.getPWithImage(templateDir + "tecracer.png", reportDoc);
        Tbl tbl = (Tbl) JAXBIntrospector.getValue(table1MainDoc.getContent().get(2));
        Tc cell = TableController.getCellWithValue(tbl, "set_img");
        cell.getContent().add(imageP);
        Tbl tbl1 = (Tbl) JAXBIntrospector.getValue(table1MainDoc.getContent().get(2));
        mainDocumentPart.getContent().add(tbl1);
        reportDoc.save(new File(templateDir + "step2-CoverAndTable1.docx"));

        /**
         * nun machen fügen wir tabelle 3 hin und machen das format = queer
         */
        DocxController.addPageBreak(reportDoc);

        WordprocessingMLPackage table2Doc = WordprocessingMLPackage.load(new File(templateDir + "template_BASO_Power_Systems_Table2.docx"));
        MainDocumentPart table2MDP = table2Doc.getMainDocumentPart();
        Tbl tbl2 = (Tbl) JAXBIntrospector.getValue(table2MDP.getContent().get(0));
        mainDocumentPart.getContent().add(tbl2);

        for (Object o : mainDocumentPart.getContent()) {
            String cname = o.getClass().getName();
            if (cname.equals("org.docx4j.wml.P")){
                P pHelper = (P) o;
                PPr tppr = ((P) o).getPPr();
                if (tppr != null && tppr.getSectPr() != null) {
                    System.out.println(tppr.getSectPr().toString());
                }
            }

        }


        reportDoc.save(new File(templateDir + "step3-addDritteTabelle.docx"));
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


    //region javaCodeGeeks tutorial


    private WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage template = WordprocessingMLPackage.load(new FileInputStream(new File(name)));
        return template;
    }

    private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();

        if (obj.getClass().equals(toSearch))
            result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }

        }
        return result;
    }

    private void replacePlaceholder(WordprocessingMLPackage template, String name, String placeholder) {
        List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);

        for (Object text : texts) {
            Text textElement = (Text) text;
            if (textElement.getValue().equals(placeholder)) {
                textElement.setValue(name);
            }
        }
    }

    private void writeDocxToStream(WordprocessingMLPackage template, String target) throws IOException, Docx4JException {
        File f = new File(target);
        template.save(f);
    }

    private void replaceParagraph(String placeholder, String textToAdd, WordprocessingMLPackage template, ContentAccessor addTo) {
        // 1. get the paragraph
        List<Object> paragraphs = getAllElementFromObject(template.getMainDocumentPart(), P.class);

        P toReplace = null;
        for (Object p : paragraphs) {
            List<Object> texts = getAllElementFromObject(p, Text.class);
            for (Object t : texts) {
                Text content = (Text) t;
                if (content.getValue().equals(placeholder)) {
                    toReplace = (P) p;
                    break;
                }
            }
        }

        // we now have the paragraph that contains our placeholder: toReplace
        // 2. split into seperate lines
        String as[] = StringUtils.splitPreserveAllTokens(textToAdd, '\n');

        for (int i = 0; i < as.length; i++) {
            String ptext = as[i];

            // 3. copy the found paragraph to keep styling correct
            P copy = (P) XmlUtils.deepCopy(toReplace);

            // replace the text elements from the copy
            List texts = getAllElementFromObject(copy, Text.class);
            if (texts.size() > 0) {
                Text textToReplace = (Text) texts.get(0);
                textToReplace.setValue(ptext);
            }

            // add the paragraph to the document
            addTo.getContent().add(copy);
        }

        // 4. remove the original one
        ((ContentAccessor) toReplace.getParent()).getContent().remove(toReplace);

    }


    //endregion javaCodeGeeks


}
