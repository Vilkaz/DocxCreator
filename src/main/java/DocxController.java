import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

/**
 * Created by vkukanauskas on 08/03/2016.
 */
public class DocxController {

    public  static WordprocessingMLPackage getNewDocx(){
        return new WordprocessingMLPackage();
    }
}
