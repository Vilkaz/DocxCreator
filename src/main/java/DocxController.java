import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ObjectFactory;

import javax.xml.bind.JAXBException;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;

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
     * It Works only, if all the Getters are for Strings
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
                    String value = getValueFromGetter(dto, propertyDescriptor);
                    mappings.put(propertyDescriptor.getName(), value);
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return mappings;

    }

    private static boolean isGetter(PropertyDescriptor propertyDescriptor) {
        String name = propertyDescriptor.getName();
        boolean result =  (name.equals("class") || name.startsWith("get"));
        return  !result;
    }

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
}
