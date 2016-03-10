import org.docx4j.jaxb.Context;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBIntrospector;
import java.math.BigInteger;

/**
 * Created by vkukanauskas on 10/03/2016.
 */
public class TableController {

    /**
     * this function searches the entire table cell after cell for an cell that has exact value as requested.
     * then it deletes that value, and returns that exact cell for further manipulation.
     *
     * I had to do that Casts, to come to the api of TR Tc and P
     *
     * @param table
     * @param value
     * @return
     */
    public static Tc getCellWithValue(Tbl table, String value) {
        Tc tcForImage = new Tc();
        for (Object tr : table.getContent()) {
            for (Object tc : ((Tr) tr).getContent()) {
                Tc tcc = (Tc) JAXBIntrospector.getValue(tc);
                int pIndex = 0;
                for (Object cellP : tcc.getContent()) {
                    P p = (P) cellP;
                    Object pValue;
                    try {
                        pValue = JAXBIntrospector.getValue(p);
                        if (pValue.toString().equals(value)) {
                            /**
                             * i could not simply .remove(Pindex) so i replaced it with fresh new P();
                             */
                            tcc.getContent().set(pIndex, new P());
                            tcForImage = tcc;
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    pIndex++;
                }
            }
        }
        return tcForImage;
    }


    public static TblPr getTblPrByWidth(int width) {
        ObjectFactory factory = Context.getWmlObjectFactory();
        TblPr tblPr = factory.createTblPr();
        TblWidth tblWidth = factory.createTblWidth();
        tblWidth.setType(TblWidth.TYPE_DXA);
        tblWidth.setW(BigInteger.valueOf(width));
        tblPr.setTblW(tblWidth);
        return tblPr;
    }

}
