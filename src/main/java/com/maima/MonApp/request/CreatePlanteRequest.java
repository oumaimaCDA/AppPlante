package com.maima.MonApp.request;

import com.maima.MonApp.model.Address;
import com.maima.MonApp.model.ContactInformation;
import lombok.Data;

import java.util.List;
@Data
public class CreatePlanteRequest {

    private Long id;
    private String name;
    private String description;
    private String planteType;
    private Address address;
    private ContactInformation contactInformation;
    private String opningHours;
    private List<String> images;


}
