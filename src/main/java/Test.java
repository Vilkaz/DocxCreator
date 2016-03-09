import DTO.BasoContentDTO;
import DTO.BasoCoverDTO;
import org.docx4j.convert.in.Doc;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Tbl;

import javax.xml.bind.JAXBIntrospector;
import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vkukanauskas on 09/03/2016.
 */
public class Test {
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
        mainDocument =DocxController.getDocxFromTemplate(path);
        MainDocumentPart mainDocumentPart = mainDocument.getMainDocumentPart();
        BasoCoverDTO coverDTO = new BasoCoverDTO("dynamische Headline", "dynamischer String", "dynamischer PRojekt type");
        HashMap<String, String> map = DocxController.getHashmapFromDTO(coverDTO);
        mainDocumentPart.variableReplace(map);

        /**
         * Jetzt wollen wir je nach Sprache die DTOs für den content kriegen,
         * idealerweise eine ArrayList von DTOs
         */
        ArrayList<BasoContentDTO> dtoList = getListOfContentDTO();
        ArrayList<HashMap<String, String >> listOfMaps = new   ArrayList<HashMap<String, String >>();

        for (Object dto: dtoList){
            listOfMaps.add(DocxController.getHashmapFromDTO(dto));
        }


        WordprocessingMLPackage testDoc;
        testDoc = WordprocessingMLPackage.load(new File(templateDir+"tableTemplate.docx"));
        MainDocumentPart testDMP = testDoc.getMainDocumentPart();
        testDMP.variableReplace(getMockupMap());
        testDoc.save(new File(templateDir+"zwischenstand2.docx"));




        /**
         * jetzt haben wir alle DTOs werte in die hashmap übertragen
         * als nächstes schnappen wir uns leere vorlage aus dem content template,
         * setzen dort die Werte der Hashmap ein, holen uns die tabelle davon
         * und fügen es ans ende des bereits vorhandenen Documents hinzu
         */

        for (HashMap<String, String> variables : listOfMaps){
            WordprocessingMLPackage document;
            document = WordprocessingMLPackage.load(new File(templateDir+"tableTemplate.docx"));
            MainDocumentPart mdp = document.getMainDocumentPart();
            mdp.variableReplace(getMockupMap());
            document.save(new File(templateDir+"zwischenstand.docx"));
            Tbl table = (Tbl)JAXBIntrospector.getValue( mdp.getContent().get(0));
            List<Object> lalalist = mainDocumentPart.getContent();
            mainDocumentPart.getContent().add(table);
//            mainDocumentPart.variableReplace(variables);
            DocxController.addPageBreak(mainDocument);
        }

//        mainDocumentPart.variableReplace(getMockupMap());

        mainDocument.save(new File(templateDir+"\\result1.docx"));

        System.out.println("debug here");

    }

    private static HashMap<String, String> getMockupMap(){
        HashMap<String, String> map = new HashMap<>();
        map.put("feld1", "dynamisches  feld 1 ");
        map.put("feld2", "dynamischer feld 2");
        map.put("feld3", "dynamischer feld 3");
        map.put("feld4", "dynamischer feld 4");
        map.put("feld5", "dynamischer feld 5");
        return  map;
    }



    private static ArrayList<BasoContentDTO> getListOfContentDTO(){
        BasoContentDTO dto1 = new BasoContentDTO("titel eins", "steckbirefid1", "string location label", "disziplin_labelDYnamisch", "nameLAbel");
        BasoContentDTO dto2 = new BasoContentDTO("titel 2", "steckbirefid2", "string location label 2", "disziplin_labelDYnamisch 2", "nameLAbel 2");
        BasoContentDTO dto3 = new BasoContentDTO("titel 3", "steckbirefid3", "string location label 3", "disziplin_labelDYnamisch  3", "nameLAbel 3");
        BasoContentDTO dto4 = new BasoContentDTO("titel 4", "steckbirefid4", "string location label 4", "disziplin_labelDYnamisch  4", "nameLAbel 4");
        ArrayList<BasoContentDTO> list = new ArrayList<BasoContentDTO>(Arrays.asList(dto1,dto2, dto3, dto4));
        return list;
    }

    private static void trimmContent(){

    }


}
