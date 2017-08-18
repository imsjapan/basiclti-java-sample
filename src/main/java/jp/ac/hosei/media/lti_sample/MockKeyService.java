package jp.ac.hosei.media.lti_sample;

import org.imsglobal.aspect.LtiKeySecretService;
import org.springframework.stereotype.Service;

@Service
public class MockKeyService implements LtiKeySecretService {

	@Override
	public String getSecretForKey(String key) {
		return "secret";
	}

}
