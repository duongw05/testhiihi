package com.language.service.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="working_offsite")
public class WorkingOffsite extends BaseEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="ID")
   private Long id;

   @Column(name="CODE")
   private String code;

   @Column(name="NAME")
   private String name;

   @Column(name="STATUS")
   private Integer status;

   @Column(name="DESCRIPTION")
   private String description;

   @Column(name="FEE")
   private Double fee;

}
