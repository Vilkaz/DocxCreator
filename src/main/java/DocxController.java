import DTO.BasoCoverDTO;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    /**
     * It Iterates the Methods, excepts the getClass method, and puts the names and values into Hachmap
     *
     * @param dto
     * @return
     */
    public static HashMap<String, String> getHashmapFromDTO(Object dto) {
        HashMap<String, String> mappings = new HashMap<String, String>();
        try {
            for (PropertyDescriptor propertyDescriptor :
                    Introspector.getBeanInfo(dto.getClass()).getPropertyDescriptors()) {
                if (isGetter(propertyDescriptor)) {
                    mappings.put(propertyDescriptor.getName(), getValueFromGetter(dto, propertyDescriptor));
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return mappings;

    }



    private static boolean isGetter(PropertyDescriptor propertyDescriptor) {
        String name = propertyDescriptor.getName();
        boolean result = (name.equals("class") || name.startsWith("get"));
        return !result;
    }

    /**
     * it itterates the DTO, and puts all the Values from all the getters into Hashmap.
     *
     * @param dto
     * @param propertyDescriptor
     * @return
     */
    private static String getValueFromGetter(Object dto, PropertyDescriptor propertyDescriptor) {
        Method method = propertyDescriptor.getReadMethod();
        String value = "";
        try {
            value = (String) method.invoke(dto, new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
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
        ObjectFactory  factory = Context.getWmlObjectFactory();
        P p = factory.createP();
        R r = factory.createR();
        p.getContent().add(r);
        Br br = factory.createBr();
        r.getContent().add(br);
        br.setType(STBrType.PAGE);
        documentPart.addObject(p);
    }


}
