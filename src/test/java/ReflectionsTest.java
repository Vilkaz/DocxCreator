import DTO.CoverDTO;
import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by vkukanauskas on 16/03/2016.
 */
public class ReflectionsTest {


    @org.junit.Test
    public void testGetHashmapFromDTO() {
        DTO.CoverDTO dto = new CoverDTO("test Headline", "test name", "test_projekt");
        HashMap<String, String> mapping = Reflections.getHashmapFromDTO(dto);
        Assert.assertTrue(mapping.get("headline").equals("test Headline"));
        Assert.assertTrue(mapping.get("name").equals("test name"));
        Assert.assertTrue(mapping.get("projectType").equals("test_projekt"));
    }

}