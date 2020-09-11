package com.spring.project.naver;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.spring.project.member.model.MemberVO;
import com.spring.project.member.service.InterMemberService;

@Controller
public class NaverController {
	
	/* NaverLoginBO */
	@Autowired
    private NaverLoginBO naverLoginBO;
    private String apiResult = null;
    
    @Autowired
    private void setNaverLoginBO(NaverLoginBO naverLoginBO) {
        this.naverLoginBO = naverLoginBO;
    }
    
	@Autowired
	private InterMemberService service;

    
    //로그인 첫 화면 요청 메소드
    @RequestMapping(value = "/naverlogin", method = { RequestMethod.GET, RequestMethod.POST })
    public String login(Model model, HttpSession session) {
        
        /* 네이버아이디로 인증 URL을 생성하기 위하여 naverLoginBO클래스의 getAuthorizationUrl메소드 호출 */
        String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
        
        //https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=sE***************&
        //redirect_uri=http%3A%2F%2F211.63.89.90%3A8090%2Flogin_project%2Fcallback&state=e68c269c-5ba9-4c31-85da-54c16c658125
        System.out.println("네이버:" + naverAuthUrl);
        

        //네이버 
        model.addAttribute("url", naverAuthUrl);

        /* 생성한 인증 URL을 View로 전달 */
        return "naver/naverlogin";
    }
    
    //네이버 로그인 성공시 callback호출 메소드
    @RequestMapping(value = "/callback", method = { RequestMethod.GET, RequestMethod.POST })
    public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
            throws IOException {
        System.out.println("여기는 callback");
        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);
        //로그인 사용자 정보를 읽어온다.
        apiResult = naverLoginBO.getUserProfile(oauthToken);
        System.out.println(naverLoginBO.getUserProfile(oauthToken).toString());
        model.addAttribute("result", apiResult);
        System.out.println("result"+apiResult);
        
        /* 네이버 로그인 성공 페이지 View 호출 */        
        
        int index = apiResult.indexOf("response");
        
         String json = apiResult.substring(index, apiResult.length());
         
         int index2 = json.indexOf("{");
         
         String jsonObject = json.substring(index2, json.length());
        

        
        JSONObject jsonObj = new JSONObject(jsonObject);
        
        
        String id = (String) jsonObj.get("id");
        String email = (String) jsonObj.get("email");  		
        String name = (String) jsonObj.get("name");	
   
		System.out.println(id+" "+email+" "+name);
        
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("pwd", id);  // 네이버 아이디의 고유한 값 이걸 pwd로 쓴다.
		map.put("userid", email); //네이버 아이디 고유한값 xxx@naver.com- userid로 쓴다.
		map.put("name", name);	
		
		
		// 네이버 아이디 중복검사
		int n = service.getNaverDuplicate(map);		
		
		  model.addAttribute("n", n);
        if(n==1) {
        	MemberVO loginuser = service.getLoginMember(map);    
        	session.setAttribute("loginuser", loginuser);
        }      
        
        return "naver/naverSuccess";
    }

}