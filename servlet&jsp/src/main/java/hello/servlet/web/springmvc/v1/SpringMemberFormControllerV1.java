package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
/**
 * 스프링이 자동으로 스프링 빈으로 등록한다.
 * 스프링 MVC에서 애노테이션 기반 컨트롤러로 인식한다.
 * ==
 *  @Component //컴포넌트 스캔을 통해 스프링 빈으로 등록
 *  @RequestMapping
 * 위 두가지 애너테이션으로도 똑같이 동작한다.
 */
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process(){
        return new ModelAndView("new-form");
    }
}
