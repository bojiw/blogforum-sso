package blogforum.sso.service.user;

import org.springframework.stereotype.Service;

import blogforum.sso.facade.facade.user.UserServerFacade;

@Service
public class UserServerFacadeImpl implements UserServerFacade {

	@Override
	public Boolean isLogin(String token) {
		// TODO Auto-generated method stub
		return true;
	}

}
