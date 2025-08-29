package com.nisum.usermanagement.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Entity
@Table(name = "\"phone\"", indexes = {
        @Index(name = "ix_phone_user_id", columnList = "user_id")
})
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(name = "number", nullable = false, length = 30)
    private String number;

    @NotBlank
    @Size(max = 10)
    @Column(name = "citycode", nullable = false, length = 10)
    private String cityCode;

    @NotBlank
    @Size(max = 10)
    @Column(name = "contrycode", nullable = false, length = 10)
    private String countryCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Phone(String number, String cityCode, String countryCode, User user) {
        this.number = number;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
        this.user = user;
    }

    public Long getId() { 
		return id; 
	}

    public String getNumber() { 
		return number; 
	}
    public void setNumber(String number) { 
		this.number = number; 
	}

    public String getCityCode() { 
		return cityCode; 
	}
    public void setCityCode(String cityCode) { 
		this.cityCode = cityCode; 
	}

    public String getCountryCode() { 
		return countryCode; 
	}
    public void setCountryCode(String countryCode) { 
		this.countryCode = countryCode; 
	}

    public User getUser() { 
		return user; 
	}
    public void setUser(User user) { 
		this.user = user; 
	}
}
