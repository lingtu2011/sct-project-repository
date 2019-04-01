package com.sct.springcloud.service;

import java.util.List;

import com.sct.springcloud.dto.SctUserInfoDTO;

public interface SctUserInfoService {

	List<SctUserInfoDTO> queryAll();

	List<SctUserInfoDTO> queryById(String id);

	void sendMail(String email);
}
