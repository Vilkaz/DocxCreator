import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by vkukanauskas on 16/03/2016.
 */
public class Reflections {

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
}
