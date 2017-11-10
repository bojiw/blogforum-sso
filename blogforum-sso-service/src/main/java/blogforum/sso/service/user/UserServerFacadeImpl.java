package blogforum.sso.service.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.blogforum.common.enums.BizErrorEnum;
import com.blogforum.common.model.ErrorContext;
import com.blogforum.common.model.Result;
import com.blogforum.common.tools.BaseConverter;
import com.blogforum.sso.dao.redis.RedisClient;
import com.blogforum.sso.pojo.entity.User;

import blogforum.sso.facade.model.UserVO;
import blogforum.sso.facade.user.UserServerFacade;

public class UserServerFacadeImpl implements UserServerFacade {
	/** redis客户端 */
	@Autowired
	protected RedisClient redisClient;

	/** session开头key */
	@Value("${myValue.session_key}")
	protected String			SESSION_KEY;
	
	@Override
	public Result<Boolean> isLogin(String token) {
		if (StringUtils.isEmpty(token)) {
			return new Result<Boolean>(true, new ErrorContext(BizErrorEnum.ILLEGAL_PARAMETER.getCode(), null,
								BizErrorEnum.ILLEGAL_PARAMETER.getMsg()), false);
		}
		//获取redis中保存的session信息
		String userString = redisClient.get(token);
		if (StringUtils.isEmpty(userString)) {
			return new Result<Boolean>(true, false);
		}
		return new Result<Boolean>(true, true);
	}

	@Override
	public Result<UserVO> getUserByToken(String token) {
		
		if (StringUtils.isEmpty(token)) {
			return new Result<UserVO>(true, new ErrorContext(BizErrorEnum.ILLEGAL_PARAMETER.getCode(), null,
								BizErrorEnum.ILLEGAL_PARAMETER.getMsg()), null);
		}
		UserVO userVO = getUserVOByToken(token);
		return new Result<UserVO>(true, userVO);
	}


	/**
	 * 通过token获取redis存的user信息
	 * @param token
	 * @return
	 * @author: wwd
	 * @time: 2017年11月6日
	 */
	private UserVO getUserVOByToken(String token){
		//获取redis中保存的session信息
		StringBuffer session = new StringBuffer(SESSION_KEY).append(":").append(token);
		String userString = redisClient.get(session.toString());
		if (StringUtils.isEmpty(userString)) {
			return null;
		}
		User user = JSON.parseObject(userString,User.class);
		BaseConverter<User, UserVO> userConverter = new BaseConverter<>();
		UserVO userVO = userConverter.convert(user, UserVO.class);
		return userVO;
	}
	
	
	@Override
	public Result<UserVO> getUserByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
