import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import groovy.transform.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dat implements Serializable {

    @JsonProperty("page")
    public Integer page;
    @JsonProperty("per_page")
    public Integer perPage;
    @JsonProperty("total")
    public Integer total;
    @JsonProperty("total_pages")
    public Integer totalPages;
    @JsonProperty("data")
    public Dat2[] data;
    @JsonProperty("support")
    public Sup support;

    public Integer getPage() {
        return page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Dat2[] getData() {
        return data;
    }

    public Sup getSupport() {
        return support;
    }
}
