package com.sct.springcloud.control;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sct.springcloud.dto.SctUserInfoDTO;
import com.sct.springcloud.feign.SctProjectOneUserInfoFeign;

@RestController
@RequestMapping("/project/two1")
public class SctProjectTwoContoller {

	private final Logger log = Logger.getLogger(getClass());
	@Autowired
	private SctProjectOneUserInfoFeign sctProjectOneUserInfoFeign;
	
	/** http://localhost:9001/hystrix 对服务的监控，不是接口的哈
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/users/list/{id}/v1", method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "queryByIdHystrix")
	public List<SctUserInfoDTO> queryById(@PathVariable(value="id",required = true)String id) {
		List<SctUserInfoDTO> sctUserInfoDTOs = null;
		try {
			sctUserInfoDTOs = sctProjectOneUserInfoFeign.queryById(id);
		} catch (Exception e) {
			log.info("异常信息"+e.getMessage());
		}
		if (null == sctUserInfoDTOs) {
			throw new RuntimeException("该ID：" + id + "没有没有对应的信息");
		}
		return sctUserInfoDTOs;
	}
	
	public List<SctUserInfoDTO> queryByIdHystrix(@PathVariable("id") String id){
		List<SctUserInfoDTO> sctUserInfoDTOs = new ArrayList<SctUserInfoDTO>();
		SctUserInfoDTO sctUserInfoDTO = new SctUserInfoDTO();
		sctUserInfoDTO.setUserName("异常");
		sctUserInfoDTOs.add(sctUserInfoDTO);
		return sctUserInfoDTOs;
	}
}
