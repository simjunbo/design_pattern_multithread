package threadpermessage.threadfactory;

import java.util.concurrent.Executors;

/**
 * Client(의뢰자) 역할
 * Host 역할에 대하여 요구(request)를 한다. Host 역할이 그 요구를 어떻게 실현하고 있는지 Client는 모른다.
 * Host에 문자표시를 요구하는 클래스
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("main BEGIN");
        // factory method
        // ThreadFactory
        // 쓰레드 생성의 상세 내용을 제어할 수 있다.
/*        Host host = new Host(
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                }
        );*/

        // Executor 방식
        // 참고로 ThreadFactory, Executors의 선택에 방식에 따라서 Host는 변경이 필요 없기 때문에 재사용성이 높다.
        Host host = new Host(
                Executors.defaultThreadFactory()
        );

        host.request(10, 'A');
        host.request(20, 'B');
        host.request(30, 'C');
        System.out.println("main END");
    }
}
