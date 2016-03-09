package DTO;

/**
 * Created by vkukanauskas on 09/03/2016.
 */
public class BasoCoverDTO {
    private String headline;
    private String name;
    private String typeOfProject;

    public BasoCoverDTO(String headline, String name, String typeOfPRoject) {
        this.headline = headline;
        this.name = name;
        this.typeOfProject = typeOfPRoject;
    }


    //region getter and setter

    public String getHeadline() {
        return headline;
    }

    public String getName() {
        return name;
    }

    public String getTypeOfProject() {
        return typeOfProject;
    }


    //endregoin getter adn setter
}
