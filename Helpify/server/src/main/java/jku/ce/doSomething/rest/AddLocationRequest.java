package jku.ce.doSomething.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddLocationRequest {
    String streetName;
    String streetNumber;
    String communeName;
    String postalCode;
    String regionName;
    String federalStateName;
    String stateName;
}
