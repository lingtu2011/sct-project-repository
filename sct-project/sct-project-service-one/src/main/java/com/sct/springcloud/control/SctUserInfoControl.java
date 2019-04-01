package com.sct.springcloud.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sct.springcloud.dto.SctUserInfoDTO;
import com.sct.springcloud.service.SctUserInfoService;

@RestController
@RequestMapping("/project/one")
public class SctUserInfoControl{
	@Autowired
	private SctUserInfoService sctUserInfoService;

	@RequestMapping(value = "/users/list/v1", method = RequestMethod.GET)
	public List<SctUserInfoDTO> list(){
		return sctUserInfoService.queryAll();
	}
	
	@RequestMapping(value = "/users/list/{id}/v1", method = RequestMethod.GET)
	public List<SctUserInfoDTO> queryById(@PathVariable("id")String id){
		return sctUserInfoService.queryById(id);
	}
	
	@RequestMapping(value = "/users/byParam/v1", method = RequestMethod.POST)
	public List<SctUserInfoDTO> queryUserByParam(Map<String,Object> param){
		
		return new ArrayList<SctUserInfoDTO>();
	}
	
	@RequestMapping(value = "/users/sendMail/v1", method = RequestMethod.GET)
	public void sendMail(@RequestParam(value="email")String email){
		sctUserInfoService.sendMail(email);
	}

}
