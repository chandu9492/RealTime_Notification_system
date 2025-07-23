package com.hrms.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private String Street;
    private String City;
    private String State;
    private String Zip;
    private String Country;
    private String District;
}
