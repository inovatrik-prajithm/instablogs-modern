package com.kirusa.instablogs.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vb_blogger_contact")
public class BloggerContact {

	@Id
	private String contactId;

	private Long bloggerId;
	private boolean isPrimary;

}
