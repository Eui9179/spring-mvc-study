package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

// foreach: iter
// 변수 출력: soutv
@WebServlet(name = "requestParam", urlPatterns = "/request-param")
public class RequestParam extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username"); // GET 쿼리 파라미터 형식도 지원하고 POST HTML Form 형식도 둘 다 지원한다.
        String age = req.getParameter("age");

        System.out.println("username = " + username);
        System.out.println("age = " + age);

        Enumeration<String> parameterNames = req.getParameterNames();
        parameterNames.asIterator()
                .forEachRemaining(param -> System.out.println(param + ":" + req.getParameter(param)));

        Map<String, String[]> parameterMap = req.getParameterMap();
        System.out.println("[한글]");
        System.out.println("parameterMap = " + parameterMap.get("username")[0]);

        String[] usernames = req.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("name = " + name);
        }

        System.out.println("usernames = " + usernames);
    }
}
