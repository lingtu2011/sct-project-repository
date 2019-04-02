package com.sct.springcloud.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 角色
 */
@Data
public class SysRoleVO implements Serializable {

	private static final long serialVersionUID = -2054359538140713354L;

	private Long id;
	private String code;
	private String name;
	private Date createTime;
	private Date updateTime;
}
