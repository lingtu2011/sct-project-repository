package com.sct.springcloud.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sct.springcloud.dto.SctUserInfoDTO;

@FeignClient(value = "sct-project-service-one")//,fallbackFactory=SctUserInfoFallbackFactory.class)
public interface SctProjectOneUserInfoFeign {

	@RequestMapping(value = "/project/one/users/list/v1", method = RequestMethod.GET)
	public List<SctUserInfoDTO> list();

	@RequestMapping(value = "/project/one/users/list/{id}/v1", method = RequestMethod.GET)
	public List<SctUserInfoDTO> queryById(@PathVariable("id")String id);
}
