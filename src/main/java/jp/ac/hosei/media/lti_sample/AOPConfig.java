package jp.ac.hosei.media.lti_sample;

import org.imsglobal.aspect.LtiKeySecretService;
import org.imsglobal.aspect.LtiLaunchVerifier;
import org.imsglobal.lti.launch.LtiOauthVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AOPConfig {
	@Autowired
	private LtiKeySecretService keyService;
	
	@Bean
	public LtiLaunchVerifier ltiLaunchVerifier() {
		return new LtiLaunchVerifier(keyService, new LtiOauthVerifier());
	}

}
