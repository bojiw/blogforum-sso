package com.blogforum.sso.facade.enums;

public enum SsoMsgExchangeNameEnum {

	/**direct模式的交换机*/
	SSO_DIRECT_EXCHANGE("sso-direct-exchange", "direct模式的交换机"),
	
	SSO_TOPIC_EXCHANGE("sso_topic_exchange","Topic模式的交换机"),
	
	SSO_FANOUT_INIT_USER("sso_fanout_init_user","广播交换机 用户初始化消息");

	private String	name;
	private String	memo;

	private SsoMsgExchangeNameEnum(String name, String memo) {
		this.name = name;
		this.memo = memo;
	}

	public SsoMsgExchangeNameEnum getQueueByName(String name) {
		for (SsoMsgExchangeNameEnum queueNameEnum : values()) {
			if (queueNameEnum.name.equals(name)) {
				return queueNameEnum;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
