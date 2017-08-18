package jp.ac.hosei.media.lti_sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.imsglobal.aspect.Lti;
import org.imsglobal.lti.launch.LtiLaunch;
import org.imsglobal.lti.launch.LtiVerificationResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LtiController {
	
	@Lti
	@RequestMapping(value="/launch", method=RequestMethod.POST)
	public String ltiEntry(HttpServletRequest request, LtiVerificationResult result) {
		if(!result.getSuccess()) {
			return "error";
		}
		LtiLaunch launch = result.getLtiLaunchResult();
		System.out.println("=== LtiLaunch ===");
		System.out.println("context_id: "+launch.getContextId());
		System.out.println("resource_link_id: "+launch.getResourceLinkId());
		System.out.println("user_id: "+launch.getUser().getId());
		System.out.println("user_role: "+launch.getUser().getRoles());
		
		System.out.println("=== HttpServletRequest ===");
		//sort
		Set<Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
		List<Entry<String, String[]>> l = new ArrayList<Entry<String,String[]>>(entrySet);
		Collections.sort(l, (e1, e2)->e1.getKey().compareTo(e2.getKey()));
		
		for(Entry<String, String[]> entry : l) {
			System.out.println(entry.getKey()+": "+Arrays.toString(entry.getValue()));
		}
		return "success";		
	}
}
