package com.kirusa.instablogs.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "vb_language_data")
@Data
public class VbLanguageData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "country_code")
	private Integer countryCode;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "lang_code")
	private String langCode;

	@Column(name = "lang_name")
	private String langName;

	@Column(name = "lang_data", columnDefinition = "TEXT")
	private String langData; // stored as JSON string
}
