package database;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "LOCAL_CITY".
 */
@Entity
public class LocalCity {

    @Id(autoincrement = true)
    private Long id;
    private String city;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public LocalCity() {
    }

    public LocalCity(Long id) {
        this.id = id;
    }

    @Generated
    public LocalCity(Long id, String city) {
        this.id = id;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
