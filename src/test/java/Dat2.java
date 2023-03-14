import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import groovy.transform.ToString;

import java.io.Serializable;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dat2 {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("email")
    public String email;
    @JsonProperty("first_name")
    public String firstName;
    @JsonProperty("last_name")
    public String lastName;
    @JsonProperty("avatar")
    public String avatar;

    public Integer getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAvatar() {
        return avatar;
    }
}
