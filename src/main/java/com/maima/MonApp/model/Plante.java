package com.maima.MonApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plante {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    @OneToOne
    private User customer;
    private String name;
    private String description;
    private String planteType;
    @JsonIgnore
    @OneToOne
    private Address address;
    @JsonIgnore
   @Embedded
   private ContactInformation contactInformation;

   private String openingHours;
    @JsonIgnore
   @OneToMany(mappedBy = "plante" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
    @JsonIgnore
   @ElementCollection
   @Column(length = 1000)
    private List<String> images;
    @JsonIgnore
    private LocalDateTime registrationDate;
    @JsonIgnore
    private boolean disponible;

    @JsonIgnore
    @OneToMany(mappedBy = "plante",cascade = CascadeType.ALL)
    private List<Soin> soins= new ArrayList<>();

}
