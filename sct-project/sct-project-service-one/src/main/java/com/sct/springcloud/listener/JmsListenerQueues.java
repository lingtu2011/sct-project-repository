package com.sct.springcloud.listener;

import org.apache.commons.lang.StringUtils;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JmsListenerQueues {
	
	// 监听消息
	@SuppressWarnings("static-access")
	@JmsListener(destination = "queueOne")
	public void distribute(String json) {
		log.info("#####消息服务平台接受消息内容:{}#####", json);
		if (StringUtils.isEmpty(json)) {
			return;
		}
		JSONObject rootJSON = new JSONObject().parseObject(json);
		JSONObject header = rootJSON.getJSONObject("header");
		String interfaceType = header.getString("interfaceType");

		//下面调用发送邮件服务

	}

}
