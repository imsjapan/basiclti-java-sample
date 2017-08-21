package jp.ac.hosei.media.lti_sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.imsglobal.aspect.Lti;
import org.imsglobal.lti.launch.LtiLaunch;
import org.imsglobal.lti.launch.LtiVerificationException;
import org.imsglobal.lti.launch.LtiVerificationResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LtiWebController {
	
	@Lti
	@RequestMapping(value="/launchweb", method=RequestMethod.POST)
	public String ltiEntryWeb(HttpServletRequest request, LtiVerificationResult result, Model model) throws LtiVerificationException {
		if(!result.getSuccess()) {
			System.out.println("=== error ===");
			System.out.println(result.getMessage());
			
			model.addAttribute("status", HttpStatus.UNAUTHORIZED);
			model.addAttribute("error", result.getMessage());
			return "error";
		}
		LtiLaunch launch = result.getLtiLaunchResult();
		
		Map<String, String> ltiParams = new HashMap<String,String>();
		ltiParams.put("context_id", launch.getContextId());
		ltiParams.put("resource_link_id", launch.getResourceLinkId());
		ltiParams.put("user_id", launch.getUser().getId());
		List<String> roles = launch.getUser().getRoles();
		model.addAttribute("ltiParams", ltiParams);
		model.addAttribute("roles", roles);
		
		System.out.println("=== LtiLaunch ===");
		System.out.println("context_id: "+launch.getContextId());
		System.out.println("resource_link_id: "+launch.getResourceLinkId());
		System.out.println("user_id: "+launch.getUser().getId());
		System.out.println("user_role: "+launch.getUser().getRoles());
	
		//sort
		Set<Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
		List<Entry<String, String[]>> l = new ArrayList<Entry<String,String[]>>(entrySet);
		Collections.sort(l, (e1, e2)->e1.getKey().compareTo(e2.getKey()));
		
		model.addAttribute("reqParams", l);
		
		System.out.println("=== HttpServletRequest ===");
		for(Entry<String, String[]> entry : l) {
			System.out.println(entry.getKey()+": "+Arrays.toString(entry.getValue()));
		}
		
		return "index";
	}
}
