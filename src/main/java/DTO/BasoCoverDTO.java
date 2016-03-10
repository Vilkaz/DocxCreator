package DTO;

/**
 * Created by vkukanauskas on 09/03/2016.
 */
public class BasoCoverDTO {
    private String headline;
    private String name;
    private String projectType;

    public BasoCoverDTO(String headline, String name, String typeOfPRoject) {
        this.headline = headline;
        this.name = name;
        this.projectType = typeOfPRoject;
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


    //endregoin getter adn setter
}
