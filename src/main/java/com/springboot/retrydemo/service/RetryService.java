package com.springboot.retrydemo.service;

import com.springboot.retrydemo.dao.PersonDetail;
import com.springboot.retrydemo.model.PersonDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;


@Service
public class RetryService {

       @Autowired
       PersonDetail personDetail;

       private ThreadLocal<List<PersonDetailDTO>> lastSuccessfulResult =
            ThreadLocal.withInitial(CopyOnWriteArrayList::new);



        @Retryable(value = { RuntimeException.class },maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 2))
        public  List<PersonDetailDTO> fetchPersonDetail(Map<String,String> input) throws Exception {

            List<String> keyValueList = input.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "_" + entry.getValue())
                    .collect(Collectors.toList());

            List<PersonDetailDTO> output=personDetail.getPersonDetails(keyValueList);

            Set<String> objectKeys = output.stream()
                    .map(PersonDetailDTO::getIndividualId)
                    .collect(Collectors.toSet());


            List<String> missingKeys = input.keySet().stream()
                    .filter(key -> !objectKeys.contains(key))
                    .collect(Collectors.toList());




            if (!missingKeys.isEmpty()) {

                lastSuccessfulResult.get().clear();
                lastSuccessfulResult.get().addAll( output.stream()
                        .filter(obj -> !missingKeys.contains(obj.getIndividualId()))
                        .collect(Collectors.toList()));

                throw new RuntimeException("Missing keys found: " + missingKeys);
            }

            lastSuccessfulResult.get().clear();
            lastSuccessfulResult.get().addAll(output);


            return output;

        }

        @Recover
        public List<PersonDetailDTO> recover(RuntimeException e, Map<String, String> input) {

             return new ArrayList<>(lastSuccessfulResult.get());


        }
    }



