package com.shangpin.uaas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.*;

/**
 * 权限
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Permission implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private String roleId;
    private String resourceId;
    private boolean status=true;
    private String description;
    private String uri;

}
