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
@Table(name="school_type")
public class SchoolType extends BaseEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="ID")
   private Long id;

   @Column(name="CODE")
   private String code;

   @Column(name="NAME")
   private String name;

   @Column(name="NAME_EN")
   private String nameEn;

   @Column(name="STATUS")
   private Integer status;

   @Column(name="DESCRIPTION")
   private String description;

}
