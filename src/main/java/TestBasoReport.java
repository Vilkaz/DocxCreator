import DTO.BasoCoverDTO;
import DTO.BasoTable1DTO;
import org.apache.commons.lang.RandomStringUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.namespace.QName;
import java.io.File;
import java.math.BigInteger;

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
        mainDocumentPart.variableReplace(Reflections.getHashmapFromDTO(getRandomCoverDTO()));

        /**
         * Jetzt wird InhaltsAnzeige (ToC) eingefügt
         */
        String tocLabel = "Inhaltsverzeichniss"; // muss in iwelcher DTO auch sein
        mainDocumentPart.addStyledParagraphOfText("Title", tocLabel);
        P tocP = factory.createP();
        mainDocumentPart.getContent().add(tocP);
        addTableOfContents(mainDocumentPart);
        DocxController.addPageBreak(reportDoc);


        /**
         * Nun wird das Zweite template mit Daten gefüllt und hinzugefügt.
         */
        WordprocessingMLPackage table1Doc = WordprocessingMLPackage.load(new File(templateDir + "template_BASO_Power_Systems_Table1.docx"));
        MainDocumentPart table1MainDoc = table1Doc.getMainDocumentPart();
        table1MainDoc.variableReplace(Reflections.getHashmapFromDTO(getTable1RandomDTO()));

        /**
         * da die variablen nicht das Bild ersetzen können, ersetzen wir dies mit einer eigener Funktion
         */

        P imageP = PController.getPWithImage(templateDir + "tecracer.png", reportDoc);
        Tbl tbl = (Tbl) JAXBIntrospector.getValue(table1MainDoc.getContent().get(2));
        Tc cell = TableController.getCellWithValue(tbl, "set_img");
        cell.getContent().add(imageP);
        Tbl tbl1 = (Tbl) JAXBIntrospector.getValue(table1MainDoc.getContent().get(2));
        TblPr tblPr = new TblPr();
        TblWidth tblWidth = new TblWidth();
        tblWidth.setW(BigInteger.valueOf(10000));
        tblPr.setTblW(tblWidth);
        tbl.setTblPr(tblPr);


        /**
         * style adding
         */

        Styles styles = mainDocumentPart.getStyleDefinitionsPart().getJaxbElement();
        ObjectFactory factory = Context.getWmlObjectFactory();

        for (Style s : styles.getStyle()) {
            RPr rpr = s.getRPr();
            if (rpr == null) {
                rpr = factory.createRPr();
                s.setRPr(rpr);
            }
            RFonts rf = rpr.getRFonts();
            if (rf == null) {
                rf = factory.createRFonts();
                rpr.setRFonts(rf);
            }
            // This is where you set your font name.
            rf.setAscii("RotisSansSerif");

//            org.docx4j.wml.Color color =factory.createColor();
//            color.setVal("FF0000");
////            rpr.setColor(color);
//            rpr.getSz().setVal(BigInteger.valueOf(36));
//            HpsMeasure hps = factory.createHpsMeasure();
//            hps.setVal(BigInteger.valueOf(36));
//            rpr.setSz(hps);
        }

        /**
         * ende der Style spielereien
         */


        mainDocumentPart.addStyledParagraphOfText("Heading2", "Hochkannt heading 2Tabelle Sieb So und so");
        mainDocumentPart.getContent().add(tbl1);
        mainDocumentPart.getContent().add(PController.getPortraitP());

        /**
         * nun machen fügen wir tabelle 3 hin und machen das format = queer
         */

        mainDocumentPart.addStyledParagraphOfText("Heading3", "Flachliegende Tabelle");
        WordprocessingMLPackage table2Doc = WordprocessingMLPackage.load(new File(templateDir + "template_BASO_Power_Systems_Table2.docx"));
        MainDocumentPart table2MDP = table2Doc.getMainDocumentPart();
        Tbl tbl2 = (Tbl) JAXBIntrospector.getValue(table2MDP.getContent().get(0));
        mainDocumentPart.getContent().add(tbl2);
        mainDocumentPart.getContent().add(PController.getLandscapeP());
        reportDoc.save(new File(templateDir + "step3-addDritteTabelle.docx"));
    }

    private static void addTableOfContents(MainDocumentPart mainDocumentPart) {
        Document wmlDocumentEl = (Document) mainDocumentPart.getJaxbElement();
        Body body = wmlDocumentEl.getBody();

        ObjectFactory factory = Context.getWmlObjectFactory();

        /* Create the following:
         *
		    <w:p>
		      <w:r>
		        <w:fldChar w:dirty="true" w:fldCharType="begin"/>
		        <w:instrText xml:space="preserve">TOC \o &quot;1-3&quot; \h \z \ u \h</w:instrText>
		      </w:r>
		      <w:r/>
		      <w:r>
		        <w:fldChar w:fldCharType="end"/>
		      </w:r>
		    </w:p>         */
        P paragraphForTOC = factory.createP();
        R r = factory.createR();

        FldChar fldchar = factory.createFldChar();
        fldchar.setFldCharType(STFldCharType.BEGIN);
        fldchar.setDirty(true);
        r.getContent().add(getWrappedFldChar(fldchar));
        paragraphForTOC.getContent().add(r);

        R r1 = factory.createR();
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue("TOC \\o \"1-3\" \\h \\z \\u \\h");
        r.getContent().add(factory.createRInstrText(txt));
        paragraphForTOC.getContent().add(r1);

        FldChar fldcharend = factory.createFldChar();
        fldcharend.setFldCharType(STFldCharType.END);
        R r2 = factory.createR();
        r2.getContent().add(getWrappedFldChar(fldcharend));
        paragraphForTOC.getContent().add(r2);

        body.getContent().add(paragraphForTOC);
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

    public static JAXBElement getWrappedFldChar(FldChar fldchar) {

        return new JAXBElement(new QName(Namespaces.NS_WORD12, "fldChar"),
                FldChar.class, fldchar);

    }

}
