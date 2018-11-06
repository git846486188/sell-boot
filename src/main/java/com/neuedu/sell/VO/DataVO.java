package com.neuedu.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;
import java.util.List;
@Data
public class DataVO {
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    private List<FoodsVO> foods;

}
