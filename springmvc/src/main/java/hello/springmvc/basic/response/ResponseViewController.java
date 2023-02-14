package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {
    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello-v1");
        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello-v2");
        //@Controller 안에서 String을 반환하면 뷰의 이름으로 맵핑
        return "response/hello";
    }

    @RequestMapping("response/hello")
    public void responseViewV3(Model model) {
        /**
        void를 반환하고
        @Controller를 사용하고
        HttpServletResponse나 OutStream(Writer) 같은 Http 메시지 바디를
        처리하는 파라미터가 없으면 요청 URL을 참고해서 논리 뷰 이름으로 사용
         -> 권장하지 않음
         */
        model.addAttribute("data", "hello-v3");
    }
}
