
import DTO.BasoTable1DTO;

import DTO.BasoCoverDTO;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBIntrospector;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vkukanauskas on 09/03/2016.
 */
public class Test {
    private static ObjectFactory factory = Context.getWmlObjectFactory();
    private static String rootDir = System.getProperty("user.dir");
    private static String templateDir = rootDir + "\\src\\main\\resources\\";

    public static void main(String[] args) throws Exception {

        /**
         * Als erstes Schnappen wir uns den Cover,
         * dann je nach Sprache das zugeförige Cover DTO erzeugen,
         * die Daten in das document übertragen
         */

        WordprocessingMLPackage mainDocument = new WordprocessingMLPackage();
        String path = templateDir + "template_BASO_cover.docx";
        mainDocument = DocxController.getDocxFromTemplate(path);
        MainDocumentPart mainDocumentPart = mainDocument.getMainDocumentPart();
        BasoCoverDTO coverDTO = new BasoCoverDTO("dynamische Headline", "dynamischer String", "dynamischer PRojekt type");
        HashMap<String, String> map = Reflections.getHashmapFromDTO(coverDTO);
        mainDocumentPart.variableReplace(map);

        /**
         * Jetzt wollen wir je nach Sprache die DTOs für den content kriegen,
         * idealerweise eine ArrayList von DTOs
         */
        ArrayList<BasoTable1DTO> dtoList = getListOfContentDTO();
        ArrayList<HashMap<String, String>> listOfMaps = new ArrayList<HashMap<String, String>>();

        for (Object dto : dtoList) {
            listOfMaps.add(Reflections.getHashmapFromDTO(dto));
        }


        WordprocessingMLPackage testDoc;
        testDoc = WordprocessingMLPackage.load(new File(templateDir + "tableTemplate.docx"));
        MainDocumentPart testDMP = testDoc.getMainDocumentPart();
        testDMP.variableReplace(getMockupMap());
        testDoc.save(new File(templateDir + "zwischenstand2.docx"));


        SectPr sectionLandscape = setPortraitLandscape(mainDocumentPart);


        /**
         * jetzt haben wir alle DTOs werte in die hashmap übertragen
         * als nächstes schnappen wir uns leere vorlage aus dem content template,
         * setzen dort die Werte der Hashmap ein, holen uns die tabelle davon
         * und fügen es ans ende des bereits vorhandenen Documents hinzu
         */


        for (HashMap<String, String> variables : listOfMaps) {
            WordprocessingMLPackage document;
            document = WordprocessingMLPackage.load(new File(templateDir + "tableTemplate.docx"));
            MainDocumentPart mdp = document.getMainDocumentPart();
            mdp.variableReplace(getMockupMap());
            document.save(new File(templateDir + "zwischenstand.docx"));
            Tbl table = (Tbl) JAXBIntrospector.getValue(mdp.getContent().get(0));
            List<Object> lalalist = mainDocumentPart.getContent();
            mainDocument.createPackage(PageSizePaper.A4, false);
            mainDocumentPart.getContent().add(table);
            DocxController.addPageBreak(mainDocument);
            setWideLAndscape(mainDocumentPart, sectionLandscape);
        }


//        mainDocumentPart.variableReplace(getMockupMap());








        mainDocument.save(new File(templateDir + "\\result1.docx"));

        System.out.println("debug here");

    }

    private static SectPr setPortraitLandscape(MainDocumentPart mainDocumentPart) {
        SectPr sectionLandscape = factory.createSectPr();
        SectPr.PgSz landscape = new SectPr.PgSz();
        landscape.setOrient(STPageOrientation.PORTRAIT);
//        landscape.setH(BigInteger.valueOf(11906));
//        landscape.setW(BigInteger.valueOf(16383));
        sectionLandscape.setPgSz(landscape);
        P p = factory.createP();
        PPr createPPr = factory.createPPr();
        createPPr.setSectPr(sectionLandscape);
        p.setPPr(createPPr);
        mainDocumentPart.addObject(p);
        return sectionLandscape;
    }

    private static void setWideLAndscape(MainDocumentPart mainDocumentPart, SectPr sectionLandscape) {
        SectPr sectionLandscape2 = factory.createSectPr();
        SectPr.PgSz landscape2 = new SectPr.PgSz();
        landscape2.setOrient(STPageOrientation.LANDSCAPE);
        landscape2.setH(BigInteger.valueOf(11906));
        landscape2.setW(BigInteger.valueOf(16383));
        sectionLandscape.setPgSz(landscape2);
        P p2 = factory.createP();
        PPr createPPr2 = factory.createPPr();
        createPPr2.setSectPr(sectionLandscape2);
        p2.setPPr(createPPr2);
        mainDocumentPart.addObject(p2);
    }

    private static HashMap<String, String> getMockupMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("feld1", "dynamisches  feld 1 ");
        map.put("feld2", "dynamischer feld 2");
        map.put("feld3", "dynamischer feld 3");
        map.put("feld4", "dynamischer feld 4");
        map.put("feld5", "dynamischer feld 5");
        return map;
    }


    private static ArrayList<BasoTable1DTO> getListOfContentDTO() {
//        BasoTable1DTO dto1 = new BasoTable1DTO("titel eins", "steckbirefid1", "string location label", "disziplin_labelDYnamisch", "nameLAbel");
//        BasoTable1DTO dto2 = new BasoTable1DTO("titel 2", "steckbirefid2", "string location label 2", "disziplin_labelDYnamisch 2", "nameLAbel 2");
//        BasoTable1DTO dto3 = new BasoTable1DTO("titel 3", "steckbirefid3", "string location label 3", "disziplin_labelDYnamisch  3", "nameLAbel 3");
//        BasoTable1DTO dto4 = new BasoTable1DTO("titel 4", "steckbirefid4", "string location label 4", "disziplin_labelDYnamisch  4", "nameLAbel 4");
//        ArrayList<BasoTable1DTO> list = new ArrayList<BasoTable1DTO>(Arrays.asList(dto1, dto2, dto3, dto4));
        return null;
    }

    private static void trimmContent() {

    }
}
