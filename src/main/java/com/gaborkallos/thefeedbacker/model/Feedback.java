package com.gaborkallos.thefeedbacker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    private String feedback;
    @OneToOne
    private Invoice invoice;
    @ManyToOne
    private User user;

}
