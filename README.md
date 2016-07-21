#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* ServletContextListener을 상속 받은 ContextLoaderListener의 contextInitialized 함수가 톰캣 서버 시작시 호출됨
  * 이 과정에서 리소스 폴더에 있는 jwp.sql 읽어와서 DB를 생성해준다.(h2는 메모리 디비이므로 어플리케이션 종료시 들어있는 데이터는 자동 삭제 됨)
* DispatcherServlet은 클라이언트의 모든 요청을 받아들이는 역할을 하는 컨트롤러로써 init 함수 내에서 모든 웹 어플리케이션의 요청 url을 처리하는 RequestMapping 클래스를 초기화 해준다. RequestMapping 클래를 통해서 요청 url을 어떠한 컨트롤러로 전달해줄 것인지 결정해준다.

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* localhost:8080으로 브라우저에서 접속을 하게 되면 Dispatcher 서블릿에서 http 정보를 받아서 요청 URL에 따라 컨트롤러를 받아 오고 해당 컨트롤러에서 요청정보를 처리해서 브라우저에 html과 리소스 파일등을 전달해준다.
  * localhost:8080 요청 -> Filter 실행 -> DispatcherServlet에서 url에 맞는 controller를 받아옴 -> 해당 컨트롤러의 service 실행

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* Dao와 같은 객체를 사용할 때 멀티쓰레드 환경에서는 동기화 문제가 발생할 수 있다. 그렇게 되면 제대로 동작하지 않게된다. 그래서 사용하는 방법은 싱글톤 패턴을 사용해서 Dao를 관리를 한다(?). 정확한 답은 잘 모르겠습니다.
