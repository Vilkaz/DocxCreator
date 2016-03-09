package DTO;

/**
 * Created by vkukanauskas on 07/03/2016.
 */
public class CoverDTO {
    private String headline;
    private String name;
    private String projectType;

    public CoverDTO(String headline, String name, String projectType) {
        this.headline = headline;
        this.name = name;
        this.projectType = projectType;
    }

    //region getter and setter

    public String getHeadline() {
        return headline;
    }

    public String getName() {
        return name;
    }

    public String getProjectType() {
        return projectType;
    }


    //endregion getter and setter

}
