package com.springboot.retrydemo.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonDetailDTO {

    private String name;

    private String age;

    private String individualId;

    private String groupId;
}
