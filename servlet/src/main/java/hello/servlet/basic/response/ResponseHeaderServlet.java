package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //[status-line]
        response.setStatus(HttpServletResponse.SC_OK);

        //[response-headers]
        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("my-header", "hello");

        //[편의 메서드]
//        response.setContentType("text/plain");
//        response.setCharacterEncoding("utf-8");

        //[쿠키]
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600);
        response.addCookie(cookie);

        redirect(response);

        //[메세지 바디]
        PrintWriter writer = response.getWriter();
        writer.println("안녕하세요");
    }

    private void redirect(HttpServletResponse response) throws IOException {
//        response.setStatus(HttpServletResponse.SC_FOUND);
//        response.setHeader("Location", "/basic/hello-form.html");
        // 이렇게 변경 가능
        response.sendRedirect("basic/hello-form.html");
    }

}
