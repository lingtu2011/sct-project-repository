package com.sct.springcloud.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain=true)
public class SctUserInfoVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String uuid;
	private String userCode;
	private String userName;
	private String age;
	private String email;
	private String lang;
	private Integer isActivated;
	private Integer isDeleted;
	private String createBy;
	private Date createDate;
	private String lastUpdateBy;
	private Date lastUpdateDate;
	
}
