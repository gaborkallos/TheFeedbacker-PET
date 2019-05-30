package com.gaborkallos.thefeedbacker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Shop {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;

    @OneToOne
    private City city;
    @OneToOne
    private Country country;

    private String postCode;
    private String address;

}
