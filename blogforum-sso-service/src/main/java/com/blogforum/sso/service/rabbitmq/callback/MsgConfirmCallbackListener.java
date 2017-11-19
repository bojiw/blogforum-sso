package com.blogforum.sso.service.rabbitmq.callback;

import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * 消息是否成功投递到rabbitmq 回调类
 * @author wwd
 *
 */
@Component
public class MsgConfirmCallbackListener implements ConfirmCallback {

	/**
	 * 消息的回调
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		System.out.println(" 回调id:" + correlationData);
		if (ack) {
			System.out.println("消息成功消费");
		} else {
			System.out.println("消息消费失败:" + cause);
		}

	}
}
