package com.gaborkallos.thefeedbacker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.Mapping;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {


    @Id
    private Long id;
    @OneToOne
    private Invoice invoice;

    private String feedback;

    @ManyToOne
    @JsonIgnore
    private Users user;

}
