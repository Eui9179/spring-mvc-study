package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j //밑에 적은 log와 동일
@RestController
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    private String logTest() {
        String name = "Spring";

        // level
        log.trace("trace log={}", name);
        log.debug("debug log={}", name); //디버그
        log.info("info log={}", name); //정보
        log.warn("warn log={}", name); //경고
        log.error("error log={}", name); //에러

        return "ok";
    }
}
