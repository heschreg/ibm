package com.inisirion.ibm.serializer;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonFilter("userCodeFilter")
public class UserResponse {

    public Integer userId;
    public String username;
    public Integer code;

    @JsonIgnore
    public String status;

}
