package com.juniorsfredo.algafood_api.domain.model;

import com.juniorsfredo.algafood_api.core.validator.Groups;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.ConvertGroup;
import lombok.Data;

@Data
@Embeddable
public class Address {

    @NotBlank
    @Column(name = "address_cep")
    private String cep;

    @NotBlank
    @Column(name = "address_street_name")
    private String streetName;

    @NotBlank
    @Column(name = "address_street_number")
    private String streetNumber;

    @Column(name = "address_complement")
    private String complement;

    @NotBlank
    @Column(name = "address_neighborhood")
    private String neighborhood;

    @Valid
    @ConvertGroup(to = Groups.CityId.class)
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_city_id")
    private City city;
}
