package com.springboot.retrydemo.dao;

import com.springboot.retrydemo.model.PersonDetailDTO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PersonDetail {

    private static Map<String,PersonDetailDTO> personData=new HashMap<>();

    static
    {
        personData.put("123"+"150550-01",PersonDetailDTO.builder().name("Suzy").age("23").individualId("123").
                groupId("150550-01").build());
        personData.put("456"+"150550-01",PersonDetailDTO.builder().name("Peppa").age("25").individualId("456").
                groupId("150550-01").build());

    }


    public List<PersonDetailDTO> getPersonDetails(List<String> gaIdIndIds)
    {

        return gaIdIndIds.stream()
                .filter(personData::containsKey)
                .map(personData::get)
                .collect(Collectors.toList());

    }
}
