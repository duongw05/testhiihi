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
@Table(name="school")
public class School extends BaseEntity {
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

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="WORKING_OFFSITE_ID")
   private WorkingOffsite workingOffsite;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="SCHOOL_TYPE_ID")
   private SchoolType schoolType;

   @Column(name="IS_PREMIUM")
   private Integer isPremium;

   @Column(name="MAP")
   private String map;

   @Column(name="LATITUDE")
   private String latitude;

   @Column(name="LONGTITUDE")
   private String longtitude;

   @Column(name="AREA")
   private String area;

   @Column(name="ADDRESS")
   private String address;

   @Column(name="PROGRAMS")
   private String programs;

   @Column(name="AREA_CVCT")
   private String areaCvct;

   @Column(name="MINUTE_NUM")
   private Double minuteNum;

   @Column(name="RHTA")
   private Long rhta;

   @Column(name="SCHEDULER")
   private Long scheduler;

   @Column(name="SCHEDULER_CUM_TA")
   private Long schedulerCumTa;

   @Temporal(TemporalType.DATE)
   @Column(name="START_YEAR")
   private Date startYear;

   @Column(name="SCHOOL_DIAGRAM")
   private String schoolDiagram;

   @Column(name="DESCRIPTION")
   private String description;

}
