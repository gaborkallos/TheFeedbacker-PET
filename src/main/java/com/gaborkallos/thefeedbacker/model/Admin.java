package com.gaborkallos.thefeedbacker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String accessRole;
    private boolean systemAdmin;
    @ManyToOne
    @JsonIgnore
    private Shop shop;
}
