package com.sct.springcloud.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sct.springcloud.constant.QueueConstant;
import com.sct.springcloud.dao.SctUserInfoDao;
import com.sct.springcloud.dto.SctUserInfoDTO;
import com.sct.springcloud.entity.SctUserInfoVO;
import com.sct.springcloud.service.SctUserInfoService;
import com.sct.springcloud.util.JsonUtil;
import com.sct.springcloud.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SctUserInfoServiceImpl implements SctUserInfoService {

	@Autowired
	private SctUserInfoDao sctUserInfoDao;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	/**
	 * TODO 查询全部用户
	 * chenyang
	 *下午5:33:48
	 */
	@Override
	@Cacheable(value="sctUserInfoDTOs#${select.cache.timeout:7000}",key="#root.methodName")
	public List<SctUserInfoDTO> queryAll() {
		List<SctUserInfoVO> list = sctUserInfoDao.queryAll();
		List<SctUserInfoDTO> sctUserInfoDTOs = new ArrayList<SctUserInfoDTO>();
		for (SctUserInfoVO SctUserInfoVO : list) {
			SctUserInfoDTO sctUserInfoDTO = new SctUserInfoDTO();
			BeanUtils.copyProperties(SctUserInfoVO, sctUserInfoDTO);
			sctUserInfoDTOs.add(sctUserInfoDTO);
		}
		log.info("sctUserInfoDTOs="+JsonUtil.mapToJson(sctUserInfoDTOs));
		return sctUserInfoDTOs;
	}

	/**
	 * TODO 根据id查询
	 * chenyang
	 *下午5:33:48
	 */
	@Override
	@Cacheable(value="queryAll",condition="#id.length()!=0")
	public List<SctUserInfoDTO> queryById(String id) {
		List<SctUserInfoVO> sctUserInfoVOs = sctUserInfoDao.queryById(id);
		List<SctUserInfoDTO> sctUserInfoDTOs = new ArrayList<SctUserInfoDTO>();
		for (SctUserInfoVO sctUserInfoVO : sctUserInfoVOs) {
			SctUserInfoDTO sctUserInfoDTO = new SctUserInfoDTO();
			BeanUtils.copyProperties(sctUserInfoVO, sctUserInfoDTO);
			sctUserInfoDTOs.add(sctUserInfoDTO);
		}
		return sctUserInfoDTOs;
	}

	/**
	 * TODO 发送邮件
	 * chenyang
	 *下午5:33:48
	 */
	@Override
	public void sendMail(String email) {
		ActiveMQQueue activeMQQueue = new ActiveMQQueue(QueueConstant.QUEUE_ONE);
		String jsonStr = this.emailJson(email);
		jmsMessagingTemplate.convertAndSend(activeMQQueue, jsonStr);
		
	}
	
	/**
	 * TODO 创建消息队列数据格式
	 * chenyang
	 *下午5:33:48
	 */
	private String emailJson(String email) {
		JSONObject rootJson = new JSONObject();
		JSONObject header = new JSONObject();
		header.put("interfaceType", "类别");
		JSONObject content = new JSONObject();
		content.put("email", email);
		rootJson.put("header", header);
		rootJson.put("content", content);
		return rootJson.toJSONString();
	}
}
