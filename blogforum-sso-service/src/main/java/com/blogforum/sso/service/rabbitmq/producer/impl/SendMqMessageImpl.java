package com.blogforum.sso.service.rabbitmq.producer.impl;

import java.util.UUID;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.blogforum.sso.facade.enums.SsoMsgExchangeNameEnum;
import com.blogforum.sso.facade.enums.SsoMsgQueueNameEnum;
import com.blogforum.sso.service.rabbitmq.producer.SendMqMessage;


@Component
public class SendMqMessageImpl implements SendMqMessage {

	private RabbitTemplate rabbitTemplate;

	/**
	 * 构造方法注入
	 */
	@Autowired
	public SendMqMessageImpl(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMsg(Object content, SsoMsgExchangeNameEnum exchangeName, SsoMsgQueueNameEnum queueName) {
		//转换为json
		String message = JSON.toJSONString(content);
		//回调id
		CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
		
		rabbitTemplate.convertAndSend(exchangeName.getName(), queueName.getName(), message, correlationId);
	}
}
