package com.kirusa.instablogs.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vb_blog")
public class Blog {

	@Id
	@Column(name = "blog_id")
	private Long blogId;

	@Column(name = "blogger_id")
	private Long bloggerId;

	@Column(name = "blog_category_id")
	private Integer blogCategoryId;

	@Column(name = "blog_type")
	private String blogType;

	@Column(name = "content_uri")
	private String contentUri;

	@Column(name = "thumbnail_uri")
	private String thumbnailUri;

	@Column(name = "created_ts")
	private Long createdTs;

	@Column(name = "status")
	private String status;
}
