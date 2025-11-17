package com.kirusa.instablogs.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vb_blogger_contact")
public class BloggerContact {

    @Id
    @Column(name = "contact_id")
    private String contactId;

    @Column(name = "blogger_id")
    private Long bloggerId;

    @Column(name = "kvsms_node_id")
    private Integer kvsmsNodeId;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "kvsms_network_id")
    private String kvsmsNetworkId;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "contact_type")
    private String contactType;

    @Column(name = "status")
    private String status;

    @Column(name = "is_virtual")
    private Boolean isVirtual;

    @Column(name = "login_id")
    private String loginId;
}
