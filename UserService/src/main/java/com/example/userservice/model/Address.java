package com.example.userservice.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Address {

    private Long addressId;
    private String province;
    private String city;
    private String area;

}
