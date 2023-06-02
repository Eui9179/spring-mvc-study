# Dispathcer Servlet

## 프론트 컨트롤러

**FontController** **패턴 특징**

- 프론트 컨트롤러 서블릿 하나로 클라이언트 요청을 받는다.
- 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출
- 공통 처리하는 코드
- 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 된다.

![front-controller](https://github.com/Eui9179/spring-mvc-study/assets/83222282/78336797-ab75-4c20-88f7-2ea99105275f)

## 서블릿

서블릿(Servlet)은 클라이언트의 요청을 처리하고 결과를 반환하는 자바 웹 프로그래밍 기술이다.

일반적으로 서블릿은 서블릿 컨테이너에서 관리한다. 서블릿 컨테이너는 서블릿 인스턴스를 생성하고 관리하는 역할을 수행하는 주체로서 톰캣은 WAS의 역할과 서블릿 컨테이너의 역할을 수행하는 대표적인 컨테이너이다. 서블릿 컨테이너의 특징은 다음과 같다

- 서블릿 객체를 생성, 초기화, 호출, 종료하는 생명주기를 관리한다.
- 서블릿 객체는 싱글톤 패턴으로 관리된다.
- 멀티 스레딩을 지원한다.

## DispatcherServlet

스프링에서는 **DispatcherServlet** 이 서블릿의 역할을 수행한다. (모든 url에 대해)
일반적으로 스프링은 톰캣을 사용한다. 그렇기 때문에 서블릿 컨테이너와 DispatcherServlet은 자동 설정된 web, xml의 설정값을 공유한다.

### 스프링의 Front Controller

스프링 웹 MVC의 **DispatcherServlet**이 FrontController 패턴으로 구현되어 있다.

![spring-mvc-flow](https://github.com/Eui9179/spring-mvc-study/assets/83222282/4af02956-9084-4c7a-8f46-543aa2c40664)


위 사진의 동작을 알아보자

1. **DispatcherServlet**으로 **request(HttpServletRequest)** 가 들어오면 DispatcherServlet은 **핸들러 매핑(Handler Mapping)** 을 통해 요청 URI에 매핑된 핸들러를 탐색한다.
2. **핸들러 어댑터(HandlerAdapter)** 로 컨트롤러를 호출한다.
3. **핸들러 어댑터**에 컨트롤러의 응답이 들어오면 **ModelAndView** 로 응답을 가공해 반환한다.
4. **View** 형식으로 리턴하는 컨트롤러를 사용할 때는 **viewResolver** 를 통해 **View**를 받아 리턴한다.

💡 뷰가 없는 어플리케이션은 viewResolver 를 호출하지 않고 REST 형식의 @RequestBody를 사용하고**MessageConverter**를 거처 JSON 형식으로 응답한다.

### 대표적인 Handler Mapping

1.  `ReuqestMappingHandlerMapping` 어노테이션 기반 컨트롤러(`@RequestMapping`)
2. `BeanNameUrlHandlerMapping` 스프링 빈의 이름으로 핸들러를 찾는다.

### 대표적인 Handler Adapter

1.  `RequestMappingHandlerAdapter` 애노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
2. `HttpRequestHandlerAdapter` HttpRequestHandler 처리

### Dispatcher Servlet > doDispatch > service 요약 코드

```java
private final Map<String, Object> handlerMappingMap = Map.of(
		"/members", new MemberListController(),
		"/member/1", new MemberOneController()
)

private void initHandlerAdapters() {
    handlerAdapters.add(new UserControllerHandlerAdapter());
    handlerAdapters.add(new MemberControllerHandlerAdapter());
}

@Override
protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    Object handler = getHandler(request);

    if (handler == null) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return;
    }

    MyHandlerAdapter adapter = getAdapter(handler);

    ModelView mv = adapter.handle(request, response, handler);

    MyView view = viewResolver(mv.getViewName());
    view.render(mv.getModel(), request, response);
}

private Object getHandler(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    return handlerMappingMap.get(requestURI);
}

private MyHandlerAdapter getAdapter(Object handler) {
    for (MyHandlerAdapter adapter : handlerAdapters) {
        if (adapter.supports(handler)) {
            return adapter;
        }
    }
    throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다.");
}

private static MyView viewResolver(String viewName) {
    return new MyView("/WEB-INF/views/" + viewName + ".jsp");
}
```

## @ModelAttribut와 @RequestBody

- `@ModelAttribute`는 `@RequestParam`을 객체화하여 받는 것이다. 따라서 HTTP 메서드가 post라면 form 데이터를 맵핑하고 HTTP 메서드가 get이라면 request parameter를 맵핑한다.
- `@ModelAttribute`는 생략가능하다.
- `@RequestBody`는 HTTP메서드의 Body의 내용을 추출한다.
