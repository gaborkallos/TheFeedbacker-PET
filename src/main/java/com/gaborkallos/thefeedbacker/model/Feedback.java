package com.gaborkallos.thefeedbacker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Invoice invoice;

    @Column(length = 500)
    private String feedback;

    private int stars;

    @ManyToOne
    private Shop shop;

    @ManyToOne
    @JsonIgnore
    private Users user;

}
