package gymManager.com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import gymManager.com.vo.LoginVO;


/**
 * 관리자 인증여부 체크 인터셉터
 * @author 정수인
 * @since 2019.11.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2019.11.01  정수인          최초 생성
 *  
 *  </pre>
 */

public class AuthAdminInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다. 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// session 객체 attribute에서 사용자 정보를 담고있는 loginVO를 가져온다
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("loginVO");

		// 세션에 loginVO 객체가 없는 경우 다시 첫 페이지로 redirect
		if (obj == null) {
			response.sendRedirect("/coverView.do");
			return false;
		} else {
			// 세션에 저장된 사용자의 타입이 관리자이면 true, 아니면 false 반환
			LoginVO loginVO = (LoginVO) obj;
			if (loginVO.getUsrType().equals("admin")) {
				return true;
			} else {
				return false;
			}
		}

	}

}
