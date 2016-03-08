/**
 * Created by vkukanauskas on 07/03/2016.
 */
public class CoverDTO {
    private String headline;
    private String name;
    private String project_type;

    public CoverDTO(String headline, String name, String project_type) {
        this.headline = headline;
        this.name = name;
        this.project_type = project_type;
    }

    //region getter and setter

    public String getHeadline() {
        return headline;
    }

    public String getName() {
        return name;
    }

    public String getProject_type() {
        return project_type;
    }


    //endregion getter and setter

}
