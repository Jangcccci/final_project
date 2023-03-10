package com.sh.j3l.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sh.j3l.member.model.dto.Member;
import com.sh.j3l.member.model.service.MailService;
import com.sh.j3l.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
@SessionAttributes({"loginMember"})
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	

	@Autowired
	private MailService mailService;

	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// 회원 목록 조회
	@GetMapping("/memberList.do")
	public void memberList(Model model) {
		List<Member> members = memberService.selectAllMember();
		model.addAttribute("members", members);
		log.debug("members = {}", members);
	}

	// 로그인 페이지 이동 메서드
	@GetMapping("/memberLogin.do")
	public void memberLogin() {}
	
	// 로그인 메서드
	@PostMapping("/loginSuccess.do")
	public String loginSuccess(HttpSession session) {
		log.debug("loginSuccess 핸들러 호출!");
		// 필요한 로그인후처리
		String location = "/";
		
		// 시큐리어티가 지정한 리다이렉트 url 가져오기
		SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		if(savedRequest != null)
			location = savedRequest.getRedirectUrl();
		
		log.debug("location = {}", location);
		
		return "redirect:" + location;
	}
	
	// 로그아웃 메서드
	@GetMapping("/memberLogout.do")
	public String memberLogout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		
		return "redirect:/";
	}
	
	// 회원 삭제 
	@PostMapping("/deleteMember.do")
	public String deleteMember(@RequestParam("id") String id, RedirectAttributes redirecAttr) {
		int result = memberService.deleteMember(id);
		log.debug("id ={}", id);
		
		if(result > 0) {
			redirecAttr.addFlashAttribute("msg", "회원 추방 성공");
		} else {
			redirecAttr.addFlashAttribute("msg", "회원 추방 실패");			
		}
		return "redirect:/member/memberList.do";
	}
	
	// 회원 검색
	@GetMapping("/searchMember")
	public String searchMember(@RequestParam("id") String id, Model model) {
	    
		List<Member> searchMember = memberService.searchById(id);
	    log.debug("searchMember = {}", searchMember);
	    
	    model.addAttribute("members", searchMember);

	    return "member/memberList";
	    
	}
	    
	// 회원가입 관련 메서드들
	// 회원가입 페이지 이동 메서드
	@GetMapping("/memberEnroll.do")
	public void memberEnroll() {}
	
	// 회원정보 중복체크 페이지 이동 메서드
	@GetMapping("/insertMember.do")
	public void insertMember() {}
	
	// 회원정보 중복체크 메서드
	@GetMapping("/duplicationCheck.do")
	public String duplicationCheck(@RequestParam String name, @RequestParam String birth, @RequestParam String phone, Model model) {
		phone = "010" + phone;
		Member member = new Member(null, null, name, phone, null, birth, 0, null, null);
		model.addAttribute("member", member);
		member = memberService.duplicationCheck(member);
		if(member != null) {
			String _id = member.getId();
			String id = _id.substring(0, 3);
			for(int i = 0; i < _id.length()-3; i++) {
				id += "*";
			}
			member.setId(id);
			model.addAttribute("member", member);
			return "member/duplication";
		}
		return "member/insertMember2";
	}
	
	@GetMapping("/duplication.do")
	public void duplication() {}
	
	@GetMapping("/overlapId.do")
	@ResponseBody
	public String overlapId(@RequestParam String id) {
		Member member = memberService.selectMemberById(id);
		if(member == null)
			return "true";
		else
			return "false";
	}
	
	// 인증메일 발송 메서드
	@GetMapping("/authentication.do")
	@ResponseBody
	public String mailCheck(@RequestParam String email) {
		return mailService.joinEmail(email);
	}
	
	// 회원가입 메서드
	@PostMapping("/enroll.do")
	public String enrollMember(Member member, RedirectAttributes redirectAttr) {
		String password = passwordEncoder.encode(member.getPassword());
		member.setPassword(password);
		int result = memberService.insertMember(member);
		redirectAttr.addFlashAttribute("msg", "회원가입 성공!");
		return "redirect:/";
	}
	
	// 아이디 찾기 이동 메서드
	@GetMapping("/findId.do")
	public void findId() {}
	
	// 아이디 찾기 메서드
	@PostMapping("/findIdByEmail.do")
	@ResponseBody
	public String findIdByEmail(@RequestParam String email) {
		return memberService.findIdByEmail(email); 
	}
	
	// 비밀번호 찾기 이동 메서드
	@GetMapping("/findPassword.do")
	public void findPassword() {}
	
	// 임시비밀번호 발급 메서드
	@PostMapping("/resetPassword.do")
	@ResponseBody
	public String resetPassword(@RequestParam String id, @RequestParam String email) {
		String password = mailService.resetPassword(email);
		Member member = new Member();
		member.setId(id);
		member.setPassword(passwordEncoder.encode(password));
		
		int result = memberService.updateMember(member);
		
		return null;
	}
	
	// 마이페이지 이동 메서드
	@GetMapping("/myPage.do")
	public void myPage() {}

	// 회원정보 수정 관련 메서드
	// 비밀번호 인증 메서드
	@GetMapping("/certified.do")
	@ResponseBody
	public String certified(@RequestParam String id, @RequestParam String password, Model model) {
		Member member = memberService.selectMemberById(id);
		// 일치
		if(member != null && passwordEncoder.matches(password, member.getPassword())) {
			return "true";
		}
		// 불일치
		else {
			return "false";
		}
	}
	
	// 이메일 수정 메서드
	@PostMapping("/updateEmail.do")
	@ResponseBody
	public int updateEmail(Member member, Authentication authentication) {
		
		int result = memberService.updateMember(member);
		
		Member newMember = memberService.selectOneMember(member.getId());
		Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
				newMember,
				authentication.getCredentials(),
				authentication.getAuthorities()
				);
		SecurityContextHolder.getContext().setAuthentication(newAuthentication);
		
		return result;
	}
	
	// 비밀번호 수정 메서드
	@PostMapping("/updatePwd.do")
	public String updatePwd(Member member, RedirectAttributes redirectAttr) {
		
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		
		int result = memberService.updateMember(member);
		
		redirectAttr.addFlashAttribute("msg", "비밀번호를 성공적으로 수정했습니다. 다시 로그인 해주세요.");
		
		return "redirect:/member/memberLogout.do";
	}
	
	// 회원탈퇴 메서드
	@PostMapping("/withdrawalMember.do")
	public String withdrawalMember(@RequestParam String id, RedirectAttributes redirectAttr, SessionStatus session) {
		int result = memberService.deleteMember(id);
		redirectAttr.addFlashAttribute("msg", "정상적으로 탈퇴됐습니다. 이용해주셔서 감사합니다.");
		return "redirect/member/memberLogout.do";
	}
}
