package com.nisum.usermanagement.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@NoArgsConstructor
@Entity
@Table(
  name = "\"user\"",
  indexes = {
    @Index(name = "ux_user_email", columnList = "email", unique = true),
    @Index(name = "ix_user_last_login", columnList = "last_login"),
  }
)
public class User {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private String id;

  @NotBlank
  @Size(max = 100)
  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @NotBlank
  @Email
  @Size(max = 320)
  @Column(name = "email", nullable = false, length = 320, unique = true)
  private String email;

  @NotBlank
  @Size(max = 255)
  @Column(name = "password_hash", nullable = false, length = 255)
  private String passwordHash;

  @NotBlank
  @Size(max = 1024)
  @Column(name = "token", nullable = false, length = 1024)
  private String token;

  @Column(name = "is_active", nullable = false)
  private boolean isActive = true;

  @CreationTimestamp
  @Column(name = "created", nullable = false, updatable = false)
  private Instant created;

  @UpdateTimestamp
  @Column(name = "modified", nullable = false)
  private Instant modified;

  @Column(name = "last_login", nullable = false)
  private Instant lastLogin;

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private Set<Phone> phones = new HashSet<>();

  public User(String id, String name, String email, String passwordHash, String token) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
    this.token = token;
    this.isActive = true;
  }

  @PrePersist
  void prePersist() {
    if (lastLogin == null) {
      lastLogin = Instant.now();
    }
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public Instant getCreated() {
    return created;
  }

  public Instant getModified() {
    return modified;
  }

  public Instant getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Instant lastLogin) {
    this.lastLogin = lastLogin;
  }

  public Set<Phone> getPhones() {
    return phones;
  }

  public void addPhone(Phone phone) {
    phones.add(phone);
    phone.setUser(this);
  }

  public void removePhone(Phone phone) {
    phones.remove(phone);
    phone.setUser(null);
  }
}
