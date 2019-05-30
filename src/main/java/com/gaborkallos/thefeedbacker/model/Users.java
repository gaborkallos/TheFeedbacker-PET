package com.gaborkallos.thefeedbacker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Users {

    @Id
    @GeneratedValue
    private Long id;
    private String emailAddress;
    private String name;
    private LocalDate dateOfBirth;
    private boolean canGetPromotion;

    @OneToMany (cascade = CascadeType.ALL, mappedBy = "user")
    private List<Feedback> feedbackList;
}


