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
@Table(name="function_catalog")
public class FunctionCatalog extends BaseEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="ID")
   private Long id;

   @Column(name="FUNCTION_CODE")
   private String functionCode;

   @Column(name="FUNCTION_NAME")
   private String functionName;

   @Column(name="DESCRIPTION")
   private String description;

   @Column(name="STATUS")
   private Integer status;

}
