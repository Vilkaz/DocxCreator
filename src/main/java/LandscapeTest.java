import org.apache.fop.cli.Main;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBIntrospector;
import java.io.File;
import java.math.BigInteger;
import java.util.List;


/**
 * Created by vkukanauskas on 10/03/2016.
 */
public class LandscapeTest {

    private static ObjectFactory factory = Context.getWmlObjectFactory();
    private static String rootDir = System.getProperty("user.dir");
    private static String templateDir = rootDir + "\\src\\main\\resources\\";

    public static void main(String[] args) throws Exception {
        //landscape experiment feld

        WordprocessingMLPackage landscapePAckage = WordprocessingMLPackage.load(new File(templateDir+"template_BASO_cover.docx"));
//        WordprocessingMLPackage landscapePAckage = WordprocessingMLPackage.createPackage(PageSizePaper.A4, true);
        landscapePAckage.getMainDocumentPart().addStyledParagraphOfText("Title", "Flach liegend");
        landscapePAckage.getMainDocumentPart().addStyledParagraphOfText("Subtitle",
                "This is a subtitle!");
        landscapePAckage.save(new File(templateDir + "\\landscape.docx"));

        MainDocumentPart mdp1 = landscapePAckage.getMainDocumentPart();

        WordprocessingMLPackage landscapePAckage2 = WordprocessingMLPackage.load(new File(templateDir+"template_BASO_cover.docx"));
        landscapePAckage2.getMainDocumentPart().addStyledParagraphOfText("Title", "Hochkant Startseite");
        landscapePAckage2.getMainDocumentPart().addStyledParagraphOfText("Subtitle",
                "This is a subtitle!");

        MainDocumentPart mdp2 = landscapePAckage2.getMainDocumentPart();

        DocxController.addPageBreak(landscapePAckage2);
        mdp2.getContent().add(PController.getPortraitP());
        mdp2.addStyledParagraphOfText("Title", "FLACH");
        mdp2.getContent().add(getTestTbl());
        mdp2.getContent().add(PController.getLandscapeP());
        mdp2.addStyledParagraphOfText("Title", "Wieder hochkant");
        mdp2.getContent().add(getTestTbl());
        mdp2.getContent().add(PController.getPortraitP());
        mdp2.addStyledParagraphOfText("Title", "und noch einmal Flach");
        mdp2.getContent().add(getTestTbl());
        mdp2.getContent().add(PController.getLandscapeP());
        mdp2.addStyledParagraphOfText("Title", "und schon wieder hochkant");

//        DocxController.addPageBreak(landscapePAckage2);
//        mdp2.getContent().addAll(mdp1.getContent());


        System.out.println("debug here");

        landscapePAckage2.save(new File(templateDir + "\\landscape2.docx"));


    }


    private static Tbl getTestTbl() {
        WordprocessingMLPackage document = new WordprocessingMLPackage();
        try {
            document = WordprocessingMLPackage.load(new File(templateDir + "template_BASO_contentPage2.docx"));
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
        MainDocumentPart mdp = document.getMainDocumentPart();
        Tbl table = (Tbl) JAXBIntrospector.getValue(mdp.getContent().get(0));
        return table;
    }


}
