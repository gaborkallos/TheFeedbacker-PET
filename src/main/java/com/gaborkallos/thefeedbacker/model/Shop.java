package com.gaborkallos.thefeedbacker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne
    private City city;
    @OneToOne
    private Country country;

    @OneToMany
    private List<ShopAdmin> admins;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shop")
    private List<Feedback> feedbacks;

    private String postCode;
    private String address;

    public void addNewShopAdmin(ShopAdmin admin){
        admins.add(admin);
    }
}
