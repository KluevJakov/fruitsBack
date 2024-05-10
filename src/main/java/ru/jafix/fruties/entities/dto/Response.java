package ru.jafix.fruties.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class Response {
    @JsonProperty("images")
    private String[] images;

    @JsonProperty("parameters")
    private Map<String, Object> parameters;

    @JsonProperty("info")
    private String info;

}
