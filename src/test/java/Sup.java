import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import groovy.transform.ToString;

import java.io.Serializable;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sup implements Serializable {
    @JsonProperty("url")
    public String url;
    @JsonProperty("text")
    public String text;

    public String getUrl() {
        return url;
    }

    public String getText() {
        return text;
    }
}
