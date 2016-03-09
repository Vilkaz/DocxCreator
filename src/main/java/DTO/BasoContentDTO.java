package DTO;

/**
 * Created by vkukanauskas on 09/03/2016.
 */
public class BasoContentDTO {
    private String titleLabel;
    private String steckbrief_id;
    private String location_label;
    private String disciplin_label;
    private String set_name_label;
    private String set_number_label;
    private String set_img_label;
    private String manufacturer_label;
    private String model_description_label;
    private String current_maintanance_label;
    private String repair_price_label;
    private String yearOfConstruction_label;
    private String ps_power_concrete_permission_label;
    private String ps_renew_label;
    private String ps_power_permission_label;
    private String ps_power_set_storage_ps_label;
    private String total_new_inventory_value_label;
    private String KRINKO_RKI_label;
    private String to_do_label;

    public BasoContentDTO(String titleLabel, String steckbrief_id, String location_label, String disciplin_label, String set_name_label) {
        this.titleLabel = titleLabel;
        this.steckbrief_id = steckbrief_id;
        this.location_label = location_label;
        this.disciplin_label = disciplin_label;
        this.set_name_label = set_name_label;
    }

    //region getter and setter

    public String getTitleLabel() {
        return titleLabel;
    }

    public String getSteckbrief_id() {
        return steckbrief_id;
    }

    public String getLocation_label() {
        return location_label;
    }

    public String getDisciplin_label() {
        return disciplin_label;
    }

    public String getSet_name_label() {
        return set_name_label;
    }

    public String getSet_number_label() {
        return set_number_label;
    }

    public String getSet_img_label() {
        return set_img_label;
    }

    public String getManufacturer_label() {
        return manufacturer_label;
    }

    public String getModel_description_label() {
        return model_description_label;
    }

    public String getCurrent_maintanance_label() {
        return current_maintanance_label;
    }

    public String getRepair_price_label() {
        return repair_price_label;
    }

    public String getYearOfConstruction_label() {
        return yearOfConstruction_label;
    }

    public String getPs_power_concrete_permission_label() {
        return ps_power_concrete_permission_label;
    }

    public String getPs_renew_label() {
        return ps_renew_label;
    }

    public String getPs_power_permission_label() {
        return ps_power_permission_label;
    }

    public String getPs_power_set_storage_ps_label() {
        return ps_power_set_storage_ps_label;
    }

    public String getTotal_new_inventory_value_label() {
        return total_new_inventory_value_label;
    }

    public String getKRINKO_RKI_label() {
        return KRINKO_RKI_label;
    }

    public String getTo_do_label() {
        return to_do_label;
    }


    //endregion getter and setter
}
