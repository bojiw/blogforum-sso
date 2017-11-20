package com.blogforum.sso.service.rabbitmq.producer;

import com.blogforum.sso.facade.enums.SsoMsgExchangeNameEnum;
import com.blogforum.sso.facade.enums.SsoMsgQueueNameEnum;

/**
 * 消息发送接口
 * @author wwd
 *
 */
public interface SendMqMessage {

	/**
	 * 发送消息
	 * @param content消息内容
	 * @param exchangeName 交换机名
	 * @param queueName 队列名
	 * @author: wwd
	 * @time: 2017年11月19日
	 */
	public void sendMsg(Object content, SsoMsgExchangeNameEnum exchangeName, SsoMsgQueueNameEnum queueName);
}
