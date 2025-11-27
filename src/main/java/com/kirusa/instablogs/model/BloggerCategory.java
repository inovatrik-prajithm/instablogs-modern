package com.kirusa.instablogs.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vb_blogger_category")
public class BloggerCategory {

	@Id
	@Column(name = "category_id")
	private Integer categoryId;

	@Column(name = "category_desc")
	private String categoryDesc;

	@Column(name = "priority")
	private Integer priority;

	@Column(name = "country_list")
	private String countryList; // comma separated list like ",IND,USA,GHANA,"
}
