import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;

/**
 * Created by vkukanauskas on 03/05/2016.
 */
public class MyToc {

    public static void addToC(MainDocumentPart mainDocumentPart, ObjectFactory factory){

        for (Object o: mainDocumentPart.getContent()){
            P p = (P) o;
            System.out.println(p.getClass().getSimpleName());

           for(Object o2: p.getContent()){
               System.out.println(o2);
           }
        }

    }
}
