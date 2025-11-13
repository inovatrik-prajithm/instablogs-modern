package com.kirusa.instablogs.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "country")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") 
    private Long id;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "sim_country_iso")
    private String simCountryIso;
    
    @Column(name = "isd_code", length = 5)
	String isdCode;
    
    private String mccMnc;
}
