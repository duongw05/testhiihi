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
@Table(name="tbl_user")
public class TblUser extends BaseEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="ID")
   private Long id;

   @Column(name="USERNAME")
   private String username;

   @Column(name="PASSWORD")
   private String password;

   @Column(name="EMAIL")
   private String email;

   @Column(name="PHONE")
   private String phone;

   @Column(name="FULL_NAME")
   private String fullName;

   @Temporal(TemporalType.DATE)
   @Column(name="DOB")
   private Date dob;

   @Column(name="GENDER")
   private Integer gender;

   @Column(name="GROUP_LIST")
   private Long groupList;

   @Column(name="TITLE")
   private String title;

   @Column(name="ACCOUNT_TYPE")
   private Integer accountType;

   @Column(name="AVATAR")
   private String avatar;

   @Column(name="LOCALE")
   private String locale;

   @Column(name="TIME_ZONE")
   private String timeZone;

   @Column(name="ENABLED")
   private Boolean enabled;

   @Column(name="AVATAR_ID")
   private Long avatarId;

   @Column(name="INVESTIGATION_CODE")
   private String investigationCode;

   @Column(name="INVESTIGATION_ID")
   private Long investigationId;

   @Column(name="ID_TEACHER")
   private Long idTeacher;

}
