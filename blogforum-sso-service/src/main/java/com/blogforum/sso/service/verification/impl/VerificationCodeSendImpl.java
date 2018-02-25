package com.blogforum.sso.service.verification.impl;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.blogforum.docking.facade.enums.SmsTemplateEnum;
import com.blogforum.docking.facade.model.VerificationEmailVO;
import com.blogforum.docking.facade.model.VerificationSmsVO;
import com.blogforum.sso.common.enums.SSOBizError;
import com.blogforum.sso.common.exception.SSOBusinessException;
import com.blogforum.sso.common.utils.MathCalculationUtil;
import com.blogforum.sso.dao.redis.RedisClient;
import com.blogforum.sso.facade.enums.SsoMsgExchangeNameEnum;
import com.blogforum.sso.service.constant.ServiceConstant;
import com.blogforum.sso.service.rabbitmq.producer.SendMqMessage;
import com.blogforum.sso.service.verification.VerificationCodeSend;

@Component
public class VerificationCodeSendImpl implements VerificationCodeSend {
	private final Logger	logger	= LoggerFactory.getLogger(this.getClass());

	/** 注册时保存到redis的值 */
	@Value("${myValue.register_key}")
	protected String		REGISTER_KEY;

	/** redis客户端 */
	@Autowired
	protected RedisClient	redisClient;

	@Autowired
	private SendMqMessage	sendMqMessage;

	@Override
	public void SendIphoneVerificationCode(String iphone) {
		//获取验证码
		String verificationCode = getVerificationCodeAndSetExRedis(iphone);
		//发送短信消息到对接第三方系统docking
		sendMqMessage.sendMsg(buildSmsVO(iphone, verificationCode),
							SsoMsgExchangeNameEnum.SSO_FANOUT_VERIFICATION_SMS);
		logger.info(MessageFormat.format("成功发送短信 手机号为:{0}，验证码为：{1}", iphone, verificationCode));

	}

	/**
	 * 拼装短信发送VO
	 * 
	 * @param iphone
	 * @param templateParam
	 * @return
	 * @author: wwd
	 * @time: 2018年2月24日
	 */
	private VerificationSmsVO buildSmsVO(String iphone, String templateParam) {
		VerificationSmsVO smsVO = new VerificationSmsVO();
		smsVO.setIphoneNumber(iphone);
		smsVO.setVerificationCode(templateParam);
		smsVO.setSmsTemplateEnum(SmsTemplateEnum.Verification);
		return smsVO;
	}

	@Override
	public String getVerificationCodeAndSetExRedis(String key) {
		StringBuffer newkey = new StringBuffer();
		newkey.append(REGISTER_KEY).append(":").append(key);
		//生成四位验证码
		int verificationCode = MathCalculationUtil.getFourRandom();
		//把验证码设置到redis中并30分钟后过期
		redisClient.setExpire(newkey.toString(), verificationCode, 1800);
		return String.valueOf(verificationCode);

	}

	@Override
	public void SendEmailVerificationCode(String email, String subject) {
		//获取验证码
		String verificationCode = getVerificationCodeAndSetExRedis(email);

		//发送邮件  传入接收邮件人 邮件主题  邮件内容
		sendMqMessage.sendMsg(
							buildEmailVO(email, subject,
												buildMailInfo(verificationCode)),
							SsoMsgExchangeNameEnum.SSO_FANOUT_VERIFICATION_MAIL);

		logger.info(MessageFormat.format("成功发送邮件，邮箱为:{0},邮件内容为{1}{2}", email, verificationCode,
							ServiceConstant.subject));

	}

	private VerificationEmailVO buildEmailVO(String mail, String subject, String mailInfo) {
		VerificationEmailVO emailVO = new VerificationEmailVO();
		emailVO.setMail(mail);
		emailVO.setSubject(subject);
		emailVO.setMailInfo(mailInfo);
		return emailVO;

	}

	/**
	 * 拼装邮件信息
	 * 
	 * @param user
	 * @return
	 */
	private String buildMailInfo(String verificationCode) {
		StringBuffer mailInfo = new StringBuffer();
		mailInfo.append("验证码为:  ").append(verificationCode).append("。");
		return mailInfo.toString();

	}

	
	@Override
	public void checkRegisterKey(String iphoneOrmail, String verificationcode) {
		String code = redisClient.get(REGISTER_KEY + ":" + iphoneOrmail);
		if (!StringUtils.equals(code, verificationcode)) {
			throw new SSOBusinessException(SSOBizError.VECODE_ERROR);
		}
	}
	
}
