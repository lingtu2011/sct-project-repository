package com.sct.springcloud.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sct.springcloud.dto.SctUserInfoDTO;

@RestController
@RequestMapping("/project/two")
public class RestOtherProjectController {

	@Autowired
	private RestTemplate restTemplate;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/users/list/v1", method = RequestMethod.GET)
	public List<SctUserInfoDTO> getUserAll() {
		List<SctUserInfoDTO> sctUserInfoDTOs = restTemplate.getForObject("http://localhost:8010/project/one/users/list/v1", List.class);
		return sctUserInfoDTOs;
	}
	
}
