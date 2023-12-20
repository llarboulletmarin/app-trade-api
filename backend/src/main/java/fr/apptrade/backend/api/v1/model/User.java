package fr.apptrade.backend.api.v1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "last_name", nullable = false, length = 30)
  private String lastName;

  @Column(name = "first_name", nullable = false, length = 30)
  private String firstName;

  @Column(name = "birthdate", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date birthdate;

  @Column(name = "sex", nullable = false, length = 1)
  private String sex;

  @Column(name = "street", nullable = false)
  private String street;

  @Column(name = "zip_code", nullable = false, length = 30)
  private String zipCode;

  @Column(name = "city", nullable = false, length = 30)
  private String city;

  @Column(name = "country", nullable = false, length = 30)
  private String country;

  @Column(name = "register_date", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date registerDate;

  @Column(name = "last_update_date")
  @Temporal(TemporalType.DATE)
  private Date lastUpdateDate;

  @ManyToOne
  @JoinColumn(name = "fkid_role", referencedColumnName = "id")
  private Role role;

}
