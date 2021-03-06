## Thread
- 순차, 병렬, 병행
  - 순차(sequential) : 복수의 업무를 순서대로 처리
  - 병렬(parallel) : 복수의 업무를 동시에 처리
  - 병행(concurrent) : 순차, 병렬보다 추상도가 높은 표현. 한 개의 업무를 어떠한 순서로 처리하든 상관 없는 여러 개의 작업.(분할)
- 데이터 레이스(data race) or 레이스 컨디션(race condition)
  - 쓰레드 A와 쓰레드 B가 경쟁함으로써 예기치 않게 발생하는 상황
- 배타제어 or 상호배타
  - 쓰레드가 어느 부분을 실행하고 있으면 다른 쓰레드가 그 부분을 실행할 수 없게 만드는 것 (synchronized)
  - 쓰레드의 배타제어를 하는 구조를 모니터(monitor)라 하며 락을 취하고 있는 것을 모니터를 소유(own)한다. 혹은 락을 홀드(hold)한다고 표현한다.
- 쓰레드 협조
  - wait set (쓰레드 대합실)
    - 모든 인스턴스는 wait set을 가진다. 그 인스턴스의 wait 메소드를 실행한 후 동작을 정지하고 있는 쓰레드들의 집합이다.
    - 쓰레드는 wait 메소드를 실행하면 동작을 일시 정지하고 wait set에 들어간다.
    - 다음 한 상황이 발생하면 wait set에서 나올 수 있다.
      - 1) 다른 쓰레드에서 notify 메소드에 의해 깨어난다.
      - 2) 다른 쓰레드에서 notifyAll 메소드에 의해 깨어난다.
      - 3) 다른 쓰레드에서 interrupt 메소드에 의해 깨어난다.
      - 4) wait 메소드가 타임아웃 된다.
  - wait 메소드 (쓰레드를 wait set에 넣는다)
    - wait 메소드를 사용하면 쓰레드가 wait set에 들어간다. (쓰레드가 obj 상에서 wait 하고 있다.)
    - 메소드 안에 wait()가 있으면 this.wait()와 같은 의미이므로 실행한 쓰레드는 this의 wait set으로 들어간다.
    - wait 메소드를 실행하기 위해서 쓰레드가 락을 가지고 있어야 하는데(규칙) wait set에 들어갈 때는 락을 일단 해제한다.
  - notify 메소드 (쓰레드를 wait set에서 꺼낸다)
    - notify 메소드를 사용하면 wait set에 있는 쓰레드 한개를 꺼낸다.
    - notify 메소드를 실행하기 위해서는 wait 메소드와 마찬가지로 쓰레드가 락을 가지고 있어야 한다. (규칙)
    - notify 메소드를 실행했을 때 wait set에 대기중인 쓰레드가 여러개 있는 경우 어느 쓰레드가 선택될지는 정해져 있지 않다.
  - notifyAll 메소드 (모든 쓰레드를 wait set에서 꺼낸다)
    - notifyAll 메소드를 사용하면 wait set에 있는 모든 쓰레드를 꺼낸다.
    - notify가 한 개의 쓰레드를 깨우는데 반해 notifyAll은 모든 쓰레드를 깨운다.
    - notify보다 notifyAll을 사용하는 편이 낫다.
  - wait, notify, notifyAll은 Object 클래스의 메소드이다.
  - 주의점
    - 락을 가지지 못한 쓰레드가 wait, notify, notifyAll 메소드를 호출하면 IlegalMonitorStateException이 발생된다.
- 쓰레드의 상태 변화
  - NEW, RUNNABLE, TERMINATED, WAITING, TIMED_WAITING, BLOCKED
![state-machine-example-java-6-thread-states](https://user-images.githubusercontent.com/7076334/53339235-1a691180-3949-11e9-8e48-301c386e78ba.png)

## MultiThread 평가기준
- 안정성 (객체를 망가뜨리지 않을 것)
  - 객체가 설계자의 의도와는 다른 상태가 되는 것을 의미한다.
  - 복수의 쓰레드가 이용해도 안전성이 유지되는 클래스를 쓰레드 세이프(thread-safe) 한 클래스라 부른다.
- 생존성 (필요한 처리가 이뤄질 것)
  - 프로그램이 중간에 멈추면 상태가 변화하지 않지만 (안정성 확보) 작동하지 않으면 아무런 소용이 없다. 
  - 안정성과 생존성은 상반된다. 안전성만 중시하다가 생존성을 읽을 수 있다. ex) 데드락
- 재사용성 (클래스를 다시 사용할 수 있을 것)
  - 필수는 아니지만 프로그램 품질을 높이는데 필요한 조건이다.
  - 멀티 쓰레드 프로그램에서는 쓰레드 배타제어의 구조나 방침을 클래스 안에 잘 숨기면 재사용성이 높은 프로그램이 된다.
- 수행 능력 (고속, 대량으로 처리할 수 있을 것)
  - 필수는 아니지만 프로그램 품질을 높이는데 필요한 조건이다.
  - throughput(쓰루풋) : 단위 시간 당 처리 수. 많은 처리가 가능하면 작업 처리량이 높아진다.
  - responsiveness(응답성) : 요구를 한 뒤 반응이 나타날 때까지 걸리는 시간. 시간이 짧을수록 응답성이 좋은 것이다.
  - capacity(용량) : 동시에 처리할 수 있는 수.
  - 그 밖에 efficiency(효율), scalability(확장성), degradation(저하) 등이 있다.
- 평가기준의 정리
  - 안전성과 생존성을 지키는 것은 필수 이다.
  - 위 조건을 만족한 상태에서 재사용성과 수행 능력을 어떻게 높이느냐가 관건이다. (품질 향상)
  
## Single Threaded Execution (이 다리를 건널 수 있는 사람은 오직 한 명)
- Single Threaded Execution은 한 개의 쓰레드에 의한 실행
- Critical Section(위험 구역), Critical Region 이라고도 불린다.
- synchronized의 역할
  - synchronized 메소드는 동시에 한 개의 쓰레드에서만 실행되는 것을 보증한다.
- Single Threaded Execution 패턴의 등장인물
  - SharedResource(공유자원) 역할
    - safeMethod : 복수의 쓰레드에서 동시에 호출해도 아무런 문제가 없는 메소드
    - unsafeMethod : 복수의 쓰레드에서 동시에 호출하면 안 되기 때문에 가드가 필요한 메소드 (synchronized)
    - 크리티컬 세션(critical section) : 싱글 쓰레드로 동작시켜야 되는 범위
- 언제 사용하면 좋을까?
  - 멀티 쓰레드
  - 복수의 쓰레드가 액세스할 가능성이 있을 때
  - 상태 변화할 가능성이 있을 때
  - 안정성 확보할 필요가 있을 때
- 생존성과 데드락
  - Single Threaded Execution 패턴을 사용하면 데드락(dead lock)을 일으킬 위험이 있다.
  - 데드락이란 2개의 쓰레드가 2개의 락을 차지하고서 서로 상대방 쓰레드가 락을 해제하기를 기다리는 현상이다. (생존성 상실)
- 상속이상
  - 다형성 때문에 자식에서 safeMethod를 오버라이딩 해서 재 구현하면 unsafeMethod가 될 수도 있다.
- 크리티컬 섹션의 크기와 수행 능력 (수행 능력이 떨어지는 이유)
  - 락을 취득하는데 시간이 걸리기 때문
  - 쓰레드의 충돌로 대기하게 되기 때문
- 관련 패턴
  - Guarded Suspension
    - Single Threaded Execution은 쓰레드가 대기 상태에 들어 가는 조건이 '어떠한 쓰레드가 가드되어 있는 unsafeMethod를 실행하고 있는 중인가 아닌가' 여부이지만 Guarded Suspension은 쓰레드가 대기 상태에 들어가는 조건이 '객체 상태가 적절한가 아닌가' 이다.
    - Guarded Suspension을 구성할 때 객체의 상태를 체크하는 부분에서 Single Threaded Execution 패턴이 사용된다.
  - Read-Write lock
    - Single Threaded Execution은 한 개의 쓰레드가 가드되어 있는 unsafeMethod를 실행하고 있다면 다른 쓰레드들은 전원 실행이 완료되기를 기다리지만 Read-Write lock은 복수의 쓰레드가 read 메소드를 동시에 실행할 수 있다. write 메소드를 실행하려 한 쓰레드 만이 대기한다.
    - Read-Write Lock을 구성할 때 쓰레드의 종류나 개수를 체크하는 부분에서 Single Threaded Execution 패턴이 사용된다.
  - Immutable
    - Single Thread Execution에는 unsafeMethod를 한 개의 쓰레드에서만 실행하도록 가드하지만, Immutable은 객체의 상태가 변하지 않기 때문에 모든 메소드가 safeMethod이다.
  - Thread-Specific Storage
    - Single Threaded Execution은 복수의 쓰레드가 SharedResource 역할에 액세스 하지만, Thread-Specific Storage은 쓰레드마다 고유 영역이 확보되어 있고, 고유 영역에는 한 개의 쓰레드에서만 액세스할 수 있다. (가드 필요 없음)
- 보 강
  - synchronized는 this의 락을 취해야 한다. (유일성)
  - long과 double은 최소 단위로 취급하지 않는다. (synchronized 하지 않다.) =>32비트에서는 원자적, 64비트에서는 원자적이지 않다.
  - 계수 세마포어 : 어떤 영역을 최대 N개의 쓰레드까지 실행
  
![singlethreadedexecution](https://user-images.githubusercontent.com/7076334/53421386-e52ff280-3a20-11e9-8323-cc8c8c1a075d.jpg)


## Immutable (망가뜨리고 싶어도 망가지지 않는다.)
- 어떨 때 사용할까?
  - 인스턴스 상태가 변하지 않는 것이 필요할 때
    - setter 미존재, 필드 final
  - 인스턴스가 공유되어 빈번하게 액세스될 때
- mutable한 클래스와 immutable한 클래스 비교
  - ex) StringBuffer (mutable)과 String (immutable)
- 표준 클래스 라이브러리에서 사용되는 Immutable 패턴
  - java.lang.String : 문자열
  - java.math.BigInteger : 큰 수
  - java.util.regex.Patter : 정규패턴
  - java.lang.Intger : 래퍼 클래스들
- 관련 패턴
  - Singel Thread Execution
    - Single Thread Execution 패턴은 한 개의 쓰레드가 인스턴스의 상태를 바꾸는 중에 다른 쓰레드가 인스턴스에 액세스 못하게 한다.
    - 쓰기와 쓰기의 충돌(wirte-write conflict)
    - 읽기와 쓰기의 충돌(read-write conflict)
    - Immutable은 상태가 없기 때문에 위 충돌이 일어나지 않는다. read-read은 일어나지만 이것은 충돌이 아니다.
  - Read-Write lock
    - Read-Write Lock 패턴은 read-read에서 conflict가 일어나지 않는다는 점을 이용한다.
    - write-write나 read-write와 같이 conflict가 일어나는 경우 쓰레드의 배타제어를 한다. read-read는 배타제어 안함.(수행능력 높임)
  - Flyweight
    - Flyweight 패턴에서는 메모리의 이용 효율을 높이기 위해 인스턴스를 공유한다. (동일 인스턴스)
- 보 강
  - final 클래스 : 상속 불가
  - final 메소드 : 오버라이드 불가
  - final 인스턴스 필드 : 선언시나 생성자에서 처리
  - fianl 클래스 필드 : 선언시나 정적 초기화 블록에서 처리
    
![immutable](https://user-images.githubusercontent.com/7076334/53419939-24a90f80-3a1e-11e9-8a5b-fea95b2d1ddd.jpg)


## Guarded Suspension (준비가 될 때까지 기다려라)
- Guarded : 보호받고 있다. 지켜지고 있다, Suspension : 일시 정지함
- 지금 이 처리를 행하면 안 될 때 처리하기 직전에 쓰레드를 기다리게 하는 패턴
- 조건부 synchronized
  - Single Threaded Execution 패턴에서는 쓰레드가 한 개라도 크리티컬 섹션 안에 있으면 다른 쓰레드는 들어가지 않고 대기한다.
  - Guarded Suspension 패턴에서는 쓰레드의 대기 여부가 가드 조건에 의해 결정된다.
- 여러가지 이름
  - guarded suspension : 가드되어 있는 실행을 일시 중단한다.
  - guarded wait : 가드 상태에서 기다린다.
  - busy wait : 바쁘게 기다린다.
  - spin lock : 돌아가며 락한다.
  - polling : 여론 조사를 한다.
- 관련 패턴
  - Single Threaded Execution
    - 가드 조건을 테스트 하는 부분과 테스트 후 상태를 변경하는 부분에는 Single Threaded Execution 패턴이 사용된다.
  - Balking
    - 가드 조건이 충족되지 않을 때 Guarded Suspension 패턴에서는 쓰레드는 가드 조건이 충족될 때까지 기다리지만 Baliking 패턴에서는 가드 조건이 충족되는 것을 기다리지 않고 돌아 간다.
  - Producer-Consumer
    - Producer 역할이 데이터를 놓을 때와 Consumer 역할이 데이터를 취할 때 양쪽 모두 Guarded Suspension 패턴이 사용된다.
  - Future
    - Future 패턴에서는 원하는 정보를 취하려 할때 아직 준비가 안 된 상태라면 Guarded Suspension 패턴을 사용하여 기다린다.
  
![guardedsuspension](https://user-images.githubusercontent.com/7076334/53434366-c9851600-3a39-11e9-9b74-dcdbe6429de9.jpg)

## Balking (필요 없으면 관둬라)
- 지금 이 처리를 실행하면 곤란하다거나 당장 실행할 필요가 없는 경우 처리 직전에 실행을 중단하고 돌아 가는 패턴
- 어떠한 경우에 사용하는가? (적용 가능성)
  - 굳이 처리할 필요가 없는 경우
    - 굳이 처리할 필요가 없을 경우 사용하면 수행 능력이 높아진다.
  - 가드 조건이 충족되기를 기다리고 싶지 않은 경우
    - Balking 특징은 기다리지 않는데 있다. 가드 조건을 만족하지 않으면 바로돌아 간다. (응답성 높아짐)
  - 가드 조건을 만족하는 것이 처음 1회뿐인 경우
    - Singleton에서 객체 최초 1회 초기화 시키는 경우랑 비슷한듯
- 단 한번만 상태가 변하는 변수를 일반적으로 래치(latch)라고 한다.
- balk 결과 표현 방법
  - balk 발생을 무시하는 방법 : 호출 하는 측에 balk 발생을 전하지 않는 방법 (간단)
  - balk 발생을 반환 값으로 표현하는 방법 : balk 발생한 사실을 boolean 형 값으로 표현하는 경우가 있다. (true/false)
  - balk 발생을 예외로 표현하는 방법 : 메소드로부터 예외를 throw 하게 된다.
- 관련 패턴
  - Guarded Suspension
    - Guarded Suspension 패턴에서는 쓰레드가 guardedMethod의 가드 조건이 만족될 때 까지 기다린다. (wait)
    - Balking 패턴에서는 쓰레드는 가드 조건을 만족하지 않으면 바로 돌아간다.
  - Observer 패턴
    - Observer 패턴을 멀티 쓰레드 환경에서 사용할 때 Balking 패턴을 이용하는 경우가 있다.
- 보 강 (타임아웃)
  - Balking 패턴과 Guarded Suspension 패턴의 중간
    - 두 패턴의 중간 단계로서 가드 조건을 만족할 때까지 일정 시간 기다리는 방법도 있다. (일정 시간 만족 못하면 balk 처리)
  - wait가 종료되는 경우
    - notify 메소드가 실행되었을 때 (어떤 쓰레드를 깨울지는 정해져 있지 않음.)
    - notifyAll 메소드가 실행되었을 때 (wait 셋 안의 모든 쓰레드 깨움)
    - interrupt 메소드 실행 (notify, notifyAll 메소드는 인스턴스 대상으로 발행하지만, interrupt는 쓰레드에 대하여)
    - 타임아웃이 일어났을 때 (wiat 메소드의 인수로 지정한 타임아웃 시간을 결과한 경우)
  - synchronized에는 타임아웃이 없으며, 인터럽트도 불가능하다.
    - synchronized로 락을 취하려고 블록 하고 있는 상태와 wait를 실행하고 wait 셋 안에 있는 상태
    - 1) synchronized로 락을 취하려고 블록하고 있는 상태
      - 쓰레드를 타임아웃 시킬 방법이 없다. 
      - interrupt를 실행하더라도 InterruptedException이 통보되지 않는다.
      - 락을 취하고 synchronized 안에 들어간 후에 wait, sleep, join 등의 인터럽트 상태를 의식하는 메소드를 호출 하던가, isInterrupted 메소드나 interrupt 메소드를 사용하여 인터럽트 상태 조사 후 직접 throw 해야 한다.
    - 2) wait을 실행하고 wait 셋 안에 있는 상태
      - wait(timeout) 사용
      - interrupt를 실행하면 InterruptedException이 통보
  - java.util.concurrent에서의 타임아웃
    - 1) 예외로 타임아웃
      - java.util.concurrent.Future 인터페이스의 get 메소드
      - java.util.concurrent.Exchanger 클래스의 exchange 메소드
      - java.util.concurrent.CyclicBarrier 클래스의 await 메소드
      - java.util.concurrent.CountDownLatch 클래스의 await 메소드
    - 2) 반환 값으로 타임아웃
      - java.util.concurrent.BlockingQueue 인터페이스 (offer 메소드의 반환값 false, poll 메소드 반환값 null 일 때 타임아웃 표시)
      - java.util.concurrent.Semaphoer 클래스 (tryAcquire 메소드에서는 반환 값이 false일 때 타임아웃 표시)
      - java.util.concurrent.locks.Lock 인터페이스 (tryLock 메소드에서는 반환 값이 false일 때 타임아웃 표시) 
    
![balking](https://user-images.githubusercontent.com/7076334/53535888-a34d9c00-3b47-11e9-8209-bf111c8fb2ba.jpg)


## Producer-Consumer (내가 만들고 당신이 사용한다)
- producer는 생산자란 뜻이며 데이터를 작성하는 쓰레드를 가리킨다. 또한 consumer는 소비자란 뜻으로 데이터를 이용하는 쓰레드를 가리킨다.
- Producer-Consumer 패턴에서는 생산자와 소비자 사이에 중개 역할이 존재하는데 이 중개 역할이 양쪽 쓰레드간 처리 속도의 차이를 메우게 된다.
- 생산자 소비자가 1:1 일 때는 Pipe 패턴이라고도 부른다.
- put 메소드
  - Guarded Suspension
    - put 메소드에는 Guarded Suspension 패턴이 사용되고 있다.
- take 메소드
  - System.out.println(e);
    - take 메소드에도 Guarded Suspension 패턴이 사용되고 있다.
- 안정성을 지키는 Channel 역할 (재상용성)
  - synchrnoized, wait, notifiyAll 등 사용하여 멀티 쓰레드의 동작을 제어하고 있는 코드는 모두 Channel 클래스 안에 있다.
- 어떤 순서로 Data 역할을 건에야 할까
  - Channel 역할이 Data 역할을 여러 개 가지고 있는 경우 어떠한 순서로 건네야 할까?
    - 큐(FIFO) - 먼저 받은 것부터 건넨다.
    - 스택(LIFO) - 나중에 받은 것을 먼저 건넨다.
    - 우선 순위를 매키는 큐(Proiory Queue) - 우선할 것을 먼저 건넨다.
- 배타제어(synchronized) 고려 사항
  - 쓰레드의 협조동작에서는 중간에 들어가는 것을 생각하자
  - 쓰레드의 배타제어에서는 지켜야 할 것을 생각하자
- Consumer가 단수이면?
  - Consumer끼리 배타제어를 고려하지 않아도 된다. (Producmer, Consumer 역할끼리 간섭은 주의)
- 관련 패턴
  - Mediator
    - Mediator 패턴에서는 중간에 ConcreteMediator 역할리 처리를 조정, 제어 한다.
    - Producer-Consumer 패턴의 Channel 역할도 Producer와 Consumer 역할을 조정, 제어 한다.
  - Worker Thread
    - Producer-Consumer 패턴에서는 Producer 역할이 Consumer 역할에게 데이터를 건넨다고 하는 부분에 초점이 있다.
    - Worekr Thread 패턴에서도 client 역할이 Worker 역할에게 데이터를 건넨다.
    - Worker Thread 패턴에서는 데이터를 처리하는 쓰레드를 공유(쓰레드 풀) 비용을 낮춘다.
  - Command 패턴
    - Producer-Consumer 패턴에서 Producer 역할이 Consumer 역할에게 건네는 Data 역할 부분에 Command 패턴이 사용되는 경우가 있다.
  - Strategy 패턴
    - Producer-Consumer 패턴에서 Producer 역할이 Consumer 역할에게 건네는 Data 역할의 순서를 정하는 부분에서 Strategy 패턴을 사용할 수 있다.
- 보 강
  - interruptedException이 붙는 메소드는 시간이 걸릴 지는 모르나 취소할 수 있는 메소드
    - java.lang.Object 클래스의 wait 메소드
    - java.lang.Thread 클래스의 sleep 메소드
    - java.lang.Thread 클래스의 join 메소드
  - sleep 메소드와 interrupt 메소드 (sleep 락 필요 없음)
    - sleep하고 있는 쓰레드는 interrupt가 호출되면 일시 정지를 중단하고 interrupted Execption이라고 하는 예외를 통보한다.
  - wait 메소드와 interrupt 메소드 (wait 락 필요)
    - wait하고 있는 쓰레드는 interrupt가 호출되면 notify를 기다리지 않고 wait 셋에서 나오게 된다.
    - 주의 할 점은 쓰레드는 wait 셋에 들어갈 때 일단 락을 해제한다. wait중 interrupt를 호출 받은 쓰레드(취소 쓰레드)는 락을 취한 다음 InterruptedExecption을 통보 한다.
    - notify/notifyAll은 java.lang.Object 클래스 메소드이며 실행하기 위해 락을 취해야 한다.
    - 반면 interrupt는 java.lang.Thread 클래스 메소드이며 락을 취할 필요 없이 쓰레드를 직접 깨운다.
  - join 메소드와 interrupt 메소드 (join 락 필요 없음)
    - join하고 있는 메소드로 다른 쓰레드가 종료되기를 기다리고 있을 때도 interrupt 메소드로 취소할 수 있다.
  - 쓰레드에서 sleep, wait, join 등의 메소드를 호출했을 때 비로서 InterruptedExecption이 통보된다.
  - isInterrupted 메소드
    - 지정한 쓰레드가 인터럽트 상태라면 true / 인터럽트 상태가 아니라면 false
  - Thread.interrupted 메소드
    - 지정한 쓰레드가 인터럽트 상태라면 true / 인터럽트 상태가 아니라면 false.
    - Thread.interrupted를 호출하면 인터럽트 상태는 삭제된다. (쓰레드를 비인트럽트 상태로 만든다.)
  - interrupt는 인터럽트 상태로 만드는 것이며, interrupted는 인터럽트 상태를 삭제 하는 메소드다.
  - Thread의 stop(deprecate)는 안전성을 위협하기 때문에 사용하지 말자
- 보 강2
  - BlockingQueue (Producer-Consumer의 Channel 역할)
    - BlockingQueue는 적절한 상태가 될 때까지 쓰레드가 블록(wait)하는 큐이다.
    - put과 take 메소드를 가지고 있다.
  - 종류
    - ArrayBlockingQueue (배열 베이스)
    - LinkedBlockingQueue (연결 리스트)
    - ProirotyBlockingQueue (우선순위)
    - DelayQueue (일정 시간이 지나면 take 할 수 없는)
    - SynchronousQueue (직접 건네는)
    - ConcurrentLinkedQueue (요소 수에 제한이 없는 쓰레드 세이프한 큐)
    
![producerconsumer](https://user-images.githubusercontent.com/7076334/53575989-1261d980-3bb6-11e9-91d6-85ab41309806.jpg)

## Read-Write Lock (다 같이 읽는 것은 상관없지만 읽는 중간에 쓰는건 안된다.)
- 읽는 처리를 실행해도 인스턴스의 상태가 바뀌지 않으므로 여러 개의 쓰레드가 동시에 읽는 것은 문제가 없다.
- 하지만 읽고 있는 중간에 쓰면 상태가 달라진다.
- 쓰기에 대한 배타제어와 읽기에 대한 배타제어를 분리하면 수행 능력을 높일 수 있다.
- 읽기 처리끼리 충돌하지 않는 점을 이용하여 수행 능력을 높이다.
  - 읽기 처리는 SharedReosurce 역할의 상태를 변경하지 않기 때문에 상호 배타제어를 할 필요가 없다.
- 읽기 처리가 무거울 때 유효
- 읽기 빈도가 쓰기 빈도보다 높을 때 유효
- 락의 의미
  - synchronized (물리적인 락) : 2개 이상의 쓰레드가 동시에 확보할 수 없다.
  - Read-Write Lock (논리적인 락) : Java 언어 사양이 아니라 프로그래머가 구현한 장치 (wait, notify/notifyAll 이용)
- 관련 패턴
  - Immutable
    - Immutable 패턴은 내부 상태가 절대 변하지 않는다.
    - Read-Write Lock 패턴은 Reader 역할끼리 배타제어를 없애 수행 능력을 높인다.
  - Single Thread Execution
    - Single Thread Execution 패턴은 프로그램의 특정 부분을 실행할 수 있는 쓰레드의 수를 한 개로 제한한다.
    - Read-Write Lock 패턴은 프로그램의 특정 부분을 실행할 수 있는 Write 역할의 쓰레드를 한 개로 제한한다. Reader 역할의 수는 제한하지 않는다.
  - Guarded Suspension
    - Guarded Suspension 패턴은 Read-Write Lock 패턴의 ReadWriteLock 역할을 만들 때 사용한다.
  - Before/After
    - Read-Write Lock 패턴은 락의 해제를 잊어버리는 것을 방지하기 위해 Before/After 패턴과 함께 사용되는 경우가 있다.
  - Strategized Locking
    - Read-Write Lock 패턴에서는 Reader 역할과 Write 역할의 특징이 살린 배타제어를 실행하여 수행 능력을 높인다.
    - Strategized Locking 패턴에서는 동기를 위한 기구를 파라미터화하여 훨씬 유연한 배타제어를 실현한다.
- 보 강
  - java.util.concurrent.locks 패키지
    - java.util.concurrent.locks.ReadWriteLock 인터페이스는 읽기 위한 락과, 쓰기 위한 락을 별도의 객체로서 취급한다.
    - java.util.concurrent.locks.ReentrantReadWriteLock 클래스는 위의 인터페이스를 구현 한 것
      - 공평성 : 락의 취득 순서를 공평(fair) 하게 할 것인지 선택 가능
      - 재입장 가능성 : Reader 역할의 쓰레드가 쓰기 위한 락을 취하거나, Write 역할의 쓰레드가 읽기 위한 쓰레드를 취하는 것이 가능하다.
      - 락의 다운그레이드 : 쓰기 위한 락을 취한다 -> 읽기 위한 락을 취한다 -> 쓰기 위한 락을 해제한다. (읽기 위한 락 -> 쓰기 위한 락 불가)
      - 편리한 메소드 : 현재 대기중인 쓰레드 수를 구하는 메소드 등 제공

![readwritelock](https://user-images.githubusercontent.com/7076334/53653235-37c21680-3c8e-11e9-9425-bffa74d68e5b.jpg)

## Thread-Per-Message (이 일을 부탁한다)
- per라고 하는 것은 ~마다 라고 하는 의미니까 직역 하면 '메시지마다 쓰레드' 라는 뜻이 된다.
- 어떠한 명령이나 요구마다 새로 한 개의 쓰레드가 할당되고 그 쓰레드가 처리를 실행한다.
- Thrad-Per-Message 패턴을 사용하면 메시지를 의뢰하는 측과 메시지를 처리하는 측의 쓰레드가 서로 달라진다.
- 응답성을 높이고 지연 시간을 줄인다.
  - Thread-Per-Message 패턴을 사용하면 Client 역할에 대한 Host 역할의 응답성이 높아지고 지연 시간은 짧아진다.
- 처리 순서를 상관하지 않을 때 사용한다.
- 반환 값이 불필요한 경우에 사용한다.
- 메소드 호출 + 쓰레드 기동 -> 메시지 송신
  - request가 돌아왔다고 해서 handle이 정상적으로 처리되었다고는 할 수 없다.
- 관련 패턴
  - Futuer
    - Thread-Per-Message 패턴에서는 새로운 쓰레드를 기동시켜 실제 처리를 맡긴다. (처리 결과를 구할 수 없다.)
    - Futur 패턴에서는 반환 값을 저장하는 인스턴스(futuer 역할)를 미리 만들어 둔다. 쓰레드는 처리가 완료 되면 Future 역할에 반환 값을 입력한다.
  - Worker Thread
    - Thread-Per-Message 패턴에서는 요구를 제시할 때마다 쓰레드를 기동한다.
    - Worker Thread 패턴에서는 미리 필요한 개수만큼 쓰레드를 기동시켜 두고 쓰레드를 돌려가며 사용한다.
- 보 강
  - 프로세스와 쓰레드
    - 한 개의 프로세스 안에 복수의 쓰레드가 구축
    - 쓰레드는 메모리를 공유 한다.
      - 프로세스와 쓰레드의 최대 차이점은 메모리 공유의 유무이다.
      - 쓰레드는 메모리를 공유 하는데 메모리 중 (공유 메모리와 캐시 메모리 2종류) 쓰레드가 공유하는 것은 공유 메모리이다.
      - 쓰레드가 메모리를 공유 하기 때문에 배타제어가 필요하다.
    - 쓰레드는 콘텍스트 스위치(context-switch)가 가볍다.
      - 프로세스와 쓰레드의 또 다른 차이점은 콘텍스트 스위치의 무게다.
- 정 리
  - java.lang.Thread 클래스 : 가장 기본적인 쓰레드 생성,기동 클래스
  - java.lang.Runnable 인터페이스 : 쓰레드가 실행하는 일을 나타내는 인터페이스
  - java.util.concurrent.ThreadFactory 인터페이스 : 쓰레드 생성을 추상화한 인터페이스
  - java.util.concurrent.Executor 인터페이스 : 쓰레드의 실행을 추상화한 인터페이스
  - java.util.concurrent.ExecutorService 인터페이스 : 재사용되는 쓰레드의 실행을 추상화한 인터페이스
  - java.util.concurrent.ScheduledExecutorService 인터페이스 : 스케줄링 된 쓰레드의 실행을 추상화한 인터페이스
  - java.util.concurrent.Executors 인터페이스 : 인터페이스 생성의 유틸리티 클래스
  
![threadpermessage](https://user-images.githubusercontent.com/7076334/53684320-107f4e00-3d4f-11e9-898a-91fff14e0c1a.jpg)

## Worker Thread (일이 올 때까지 기다리고, 일이 오면 작업한다)
- Worker Thread 패턴에서는 워커 쓰레드가 일을 하나씩 가지러 가고, 또 처리를 한다. 일이 없으면 워크 쓰레드는 새로운 일이 올 때까지 기다린다.
- Worker Thread를 Background Thread라고 한다.
- ThreadPool : Worker Thread를 많이 보관하고 있는 장소
- 쓰루풋의 향상
  - Worker Thread 패턴에서는 쓰레드를 돌려가면서 사용하고 재활용함으로써 쓰루풋을 높인다.
- 용량의 제어
  - Worker 역할의 수
    - java.util.concurrent.ThreadPoolExecutor 클래스는 이러한 용량 제어를 실행하는 클래스이다.
  - Request 역할의 수
    - Request 역할의 수가 많으면 Client 역할과 Worker 역할간 처리속도의 차이를 메울 수 있다.
    - 한편으로 Request 역할을 보관하는데 대량의 메모리를 소비한다. (용량과 리소스의 이율배반 존재)
- invocation과 execution 분리
  - 메소드의 기동과 메소드의 실행을 분리한다. 메소드를 기동하는 것을 invocation(동사는 invoke), 메소드를 실행하는 것을 execution(동사는 execute) 라고 한다.
  - Worker Thread 패턴이나 Thread-Per-Message 패턴은 메소드의 invocation과 execution을 분리하고 있다.
  - 응답성의 향상
    - invocation과 execution을 분리하지 않으면 execution에 시간이 걸리는 경우 invocation 처리까지 늦어진다.
  - 실행 순서의 제어(스케줄링)
    - invocation과 execution을 분리해 두면 invoke 순서와 상관없이 execute 할 수 있다.
- 관련 패턴
  - Producer-Consumer
    - Worker Thread 패턴에서 Request 역할을 전달하는 부분을 Producer(Client)-Consumer(Worker) 패턴으로 되어 있다.
  - Thread-Per-Message
    - Thread-Per-Message 패턴은 새로 만든 쓰레드에 실제 처리기를 맡겨 응답성을 높이다. 그러나 쓰레드 기동 시간이 있다.
    - Worker Thread 패턴에서는 Thread-Per-Message 패턴과 마찬가지로 별도의 쓰레드에게 실제 처리를 맡긴다. 하지만 미리 기동해 둔다. (쓰레드 풀)
  - Command
    - Worker Thread 패턴 가운데 Worker 역할이 업무를 받아서 작업하는 부분에 Command 패턴이 사용되는 경우가 있다.
    - Request => Command 역할에 대응
  - Future
    - Client 역할이 Worker 역할의 실행 결과를 구하고자 할 때, Future 패턴을 사용한다.
  - Flyweight
    - Flyweight는 인스턴스를 공유함으로써 메모리를 절약한다.
    - Worker Thread 패턴은 워커 쓰레드를 공유하여 쓰레드 기동 시간을 절약하고 있다.
  - Thread-Specific Storage
    - Thread-Specific Storage 패턴은 쓰레드의 고유 영역을 확보하기 위한 것이다.
    - 워커 쓰레드에는 각각의 업무에 고유 정보 (Reuqest에 있다.)를 담지 않기 때문에 Thread-Specific Storage 패턴을 사용하는 것은 불가능하다.
  - Active Object
    - Worker Thread 패턴은 Active Object 패턴의 일부로서 사용되고 있다.
- 보 강
  - ThreadPoolExecutor 클래스
    - ThreadPoolExecutor에는 쓰레드풀(워커 쓰레드 보관, 관리)에 대하여 다음과 같은 설정을 할 수 있습니다.
      - 쓰레드풀의 크기 지정
      - 쓰레드를 미리 생성할 것인지 필요에 따라 생성할 것인지 지정
      - 쓰레드를 생성할 때의 팩토리(java.util.concurrent.ThreadFactory)지정
      - 필요 없어진 워커 쓰레드를 종료시키기까지의 시간 지정
      - 실행하는 일을 건넬 때의 큐잉 방법 지정
      - 실행 거부(reject)하는 방법 지정
      - 실행의 전처리,후처리 지정
    - Executors.newFixedThreadPool 메소드
      - 인수로 지정한 개수의 워커 쓰레드를 생성하는 쓰레드풀을 만든다.
      - 만들어진 워커 쓰레드는 재사용된다.
      - newFixedThreadPool의 인수에 ThreadFactory 객체를 추가하면 새로운 워커 쓰레드는 그 ThreadFactory를 사용하여 생성된다.
    - Executors.newCachedThreadPool 메소드
      - 필요에 따라 워커 쓰레드가 자동 생성되는 쓰레드풀을 만든다.
      - 만들어진 워커 쓰레드는 재사용되고, 일이 없어진 워커 쓰레드는 약 60초동안만 캐시로서 보관되고 자동 삭제된다.
      - newCachedThreadPool의 인수에 ThreadFactory 객체를 추가하면 새로운 워커 쓰레드는 그 ThreadFactory를 사용하여 생성된다.
    - Executors.newScheduledThreadPool 메소드
      - 리퀘스트를 일정 시간 후에 실행하거나 반복실행하는 쓰레드풀을 만든다.
      - 리퀘스트가 없을 때에도 보관해 두는 쓰레드의 개수를 인수로 지정한다.
      - newScheduledThreadPool 인수에 ThreadFactory 객체를 추가하면 새로운 워커 쓰레드는 그 ThreadFactory를 사용하여 생성된다.
   
![workerthread](https://user-images.githubusercontent.com/7076334/53735481-56145600-3eca-11e9-829f-606c460435e8.jpg)

## Future (먼저 교환권을 받어라)
- Future 패턴은 시간이 걸리는 처리를 포함하고 있는 보통의 클래스에 대하여 교환권을 만들어 주고, 멀티 쓰레드화하여 수행 능력을 높이는 패턴이다.
- 관련 패턴
  - Thread-Per-Message
    - Thread-Per-Message 패턴은 시간이 걸리는 처리를 새로 만든 쓰레드에 맡기는 패턴이다. 처리 결과가 필요한 경우 Future를 사용한다.
  - Builder
    - Builder 패턴은 완성된 성과물을 나중에 가지러 간다.
    - Future 패턴은 조립을 시작한 시점에 Future 역할의 객체가 만들어진다. 그리고 나중에 Future 역할을 사용하여 실제 성과물에 액세스 한다.
  - Proxy
    - Proxy 패턴에서는 RealSubject 역할을 만드는데 시간이 걸리기 때문에 Proxy 역할이 대리인으로서 최대한 처리 한다. 그래도 RealSubject 역할이 필요한 경우 역할을 만든다.(Virtual Proxy 패턴)
    - Future 패턴에서는 RealData 역할을 만드는데 시간이 걸리기 때문에 Future 역할이 교환권이 된다. 그리고 별도의 쓰레드에서 RealData 역할을 만들어 둔다.
  - Guarded Suspension
    - Future 역할을 사용하면 RealData 역할에 액세스하는 메소드는 Guarded Suspension 패턴을 사용하여 가드 한다. 가드 조건은 RealData 역할이 만들어져 있을 것이다.
  - Balking
    - Future 역할은 1번만 대입할 수 있는 변수 이므로 래치의 일종으로 간주할 수 있다. Futuer 역할에 RealData 역할이 2번 이상 설정되는 것을 방지하고자 할때는 Balking 패턴이 사용된다.
- 보 강
  - java.util.concurrent 패키지
    - java.util.concurrent.Callable 인터페이스는 반환 값이 있는 처리의 호출을 추상화한 인터페이스이다.
    - java.util.concurrent.Future 인터페이스는 Future 역할에 해당 하는 인터페이스이다.

![future](https://user-images.githubusercontent.com/7076334/54087721-2c12d600-4399-11e9-9cec-2b2db4a8080e.jpg)

## Two-Phase Termination (뒷정리를 하고 나서 자도록 해라)
- 종료 처리를 확실하게 하고 나서 쓰레드를 종료시키는 패턴
  - 안전하게 종료할 것(안전성)
  - 반드시 종료 처리를 할 것(생존성)
  - 종료 요구가 제시되면 가능한 빨리 종료 처리에 들어갈 것(응답성)
- Thread 클래스의 stop 메소드를 사용해서는 안 된다.
  - stop 메소드를 사용하면 인스턴스의 안전성이 보장되지 않기 때문이다.
- interrupt 메소드를 사용하는 이유는 쓰레드 상태가 sleep 인 경우 즉시 끝나지 않기 때문에
- join 메소드를 사용하면 지정한 쓰레드가 종료되길 기다린다.
- isAlive 메소드를 사용하면 지정한 쓰레드가 종료했는지 조사할 수 있다.
- 셧다운 후크(shutdown hook)
  - Java 실행 처리계 전체가 종료할 때 (System.exit) start하는 쓰레드를 말한다.
- 관련 패턴
  - Before/After
    - Two-Phase Termination 패턴에서는 종료 처리를 확실하게 실행하기 위해 finally를 사용한다.
  - Multiphase Cancellation
    - Multiphase Cancellation 패턴은 쓰레드를 종료시키려고 하는데 일정 시간 안에 종료 하지 않았을 때 점점 강한 종료 요구를 제시하는 패턴이다.
  - Multi-Phase Startup
    - Two-Phase Termination 패턴은 종료 요구를 받았을 때 곧바로 종료하는 것이 아니라 [종료 처리 중] 단계를 거침으로써 안전하게 쓰레드를 종료 시킨다.
    - Multi-Phase Startup 패턴은 복수의 서브 시스템이 존재할 때 다단계를 거쳐 시스템 전체를 기동하는 패턴이다.
  - Balking
    - Two-Phase Termination 패턴에서 종료 처리 중에 부적절한 처리가 이뤄지는 것을 막기 위해 Balking 패턴을 사용하는 경우가 있다.
- 보 강
  - java.util.concurrent.CountDownLatch
    - 어떤 쓰레드가 지정한 쓰레드가 종료되기를 기다릴 때는 java.lang.Thread 클래스의 join 메소드를 이용한다.
    - java.util.concurrent.CountDownLatch 클래스를 사용하면 지정한 회수만큼 countdown 메소드가 호출되기를 기다린다.
  - java.util.concurrent.CyclicBarrier
    - 쓰레드의 동기를 몇 번이고 반복하고 싶을 때에는 java.util.concurrent.CyclicBarrier 클래스를 사용하는 것이 편리하다.
  
![Two-Phase](https://user-images.githubusercontent.com/7076334/54198246-55527400-4509-11e9-8b05-67d354c41f5b.jpg)

## Thread-Specific Storage (쓰레드마다 가지고 있는 락커)
- Thread-Specific Storage 패턴은 입구는 한 개이나 내부에 쓰레드 고유의 영역이 따로 만들어져 있는 패턴이다.
- 다음과 같이 불리기도 한다.
  - Per-Thread Attribute (각 쓰레드의 속성)
  - Thread-Specific Data (쓰레드 고유의 데이터)
  - Thread-Specific Field (쓰레드 고유의 필드)
  - Thread-Local Storage (쓰레드의 지역적인 기억영역)
- java.lang.ThreadLocal
  - set 메소드 : 인수로 지정한 인스턴스를 이 메소드를 호출한 쓰레드(현재의 쓰레드)에 대응시켜서 보관하기 위한 것이다. 자기 락커에 자기 짐을 넣는 것에 해당한다.
  - get 메소드 : get을 호출한 쓰레드(현재의 쓰레드)에 대응하는 인스턴스를 취득하기 위한 것이다. 한 번도 set으로 보관한 것이 없을 경우 반환 값은 null 이다. 자기 락커에서 자기 짐을 꺼내는 것에 해당한다.
  - 쓰레드 고유 정보를 보관하는 장소
    - 쓰레드 외부(thread-external)에 쓰레드 고유의 정보를 보관한다.
      - ThreadLocal의 인스턴스는 락커 룸이다. 각각의 쓰레드, 즉 락커는 모두 락커 룸에 모여있다.
      - 이 처럼 쓰레드 고유의 정보를 쓰레드 외부에 모아 두는 방법을 쓰레드 외부라고 한다.
    - 쓰레드 내부(thread-internal)에 쓰레드 고유의 정보를 보관한다.
      - 쓰레드 소스를 읽었을 때 쓰레드 고유의 정보가 무엇인지 쉽게 알 수 있다. 
      - 대신 쓰레드의 고유 정보를 추가하려면 MyThread 클래스를 수정해야 한다.
  - 컨텍스트의 위험성
    - TSObjectCollection 역할이 현재의 쓰레드를 자동적으로 판단한다. 즉 쓰레드에 관한 정보를 건넬 필요가 없다. (컨텍스트 도입)
    - 컨텍스트가 편리하기는 하지만 처리에 사용되는 정보가 직접 눈으로 확인할 수 없다는 위험성도 가지고 있다.
- 관련 패턴
  - Singleton
    - Singleton 패턴은 인스턴스의 수가 한 개임을 보증하는 패턴이다.
    - Thread-Specific Storage 패턴은 쓰레드에 고유한 영역을 확보하므로 이른바 쓰레드 단위의 Singleton으로 간주할 수 있다.
  - Worker Thread (threadpool)
    - Worker Thread 패턴에서 각 워커 쓰레드는 하나의 일이 끝나면 다음 일을 위해 재사용된다.
    - Worker Thread 패턴과 Thread-Specific Sotrage 패턴을 동시에 사용하는 것은 일반적으로 곤란한다.
  - Single Threaded Execution
    - Thread-Specific Storage 패턴은 각각의 쓰레드에 고유 영역을 부여한다.
    - 그 영역에 액세스하는 것은 각각 한 개의 쓰레드 뿐이라 Single Threaded Execution 패턴을 사용하여 배타제어를 할 필요가 없다.
  - Proxy
    - Proxy 패턴은 객체의 인터페이스(API)를 바꾸지 않으면서 객체의 액세스 방법을 바꾼다.
    - Thread-Specific Storage 패턴에서는 TSObjectProxy 역할이 TSObject 역하의 대리인이 되는 부분에 Proxy 패턴이 사용된다.
- 보 강
  - 액터 베이스와 태스크 베이스
    - 액터 베이스(actor-based) - 주체에 무게를 둔다.
    - 태스크 베이스(task-based) - 객체에 무게를 둔다.
  - 액터 베이스 (Thread)
    - 액터 베이스 접근방식은 쓰레드를 나타내는 인스턴스에게 업무 실행에 필요한 정보(컨텍스트, 상태)를 주는 것이다.
    - 각 쓰레드는 다른 쓰레드로부터 받은 정보를 사용하여 처리를 하고, 자신의 내부상태를 변화 시킨다. (액터)
  - 태스크 베이스 (Ruunnable)
    - 태스크 베이스 접근방식에서는 쓰레드에 정보(컨텍스트, 상태)를 주지 않는다.
    - 쓰레드가 아닌 쓰레드끼리 주고 받는 인스턴스 (메시지, 리퀘스트, 명령어, 태스크) 쪽에 정보를 건넨다.
  - 두 개념은 분리되는 것이 아니라 혼재된다. (액터끼리 태스크를 주고받는 상황)
  - 주 의 : Thread-Specific Storage 패턴은 Worker Thread 패턴과 함께 사용할 수 없다.
  
![threadspecificstorage](https://user-images.githubusercontent.com/7076334/54275597-644f2a00-45ce-11e9-8b08-38864df19c7f.jpg)


## Active Object (비동기 메시지를 맞이하는 능동적인 객체)
- Active Object 패턴을 Actor, Concurrent Object 패턴이라고 부르기도 한다.
- Active Object 패턴에 등장하는 능동적인 객체는 단순히 '자기 고유의 쓰레드를 갖는다' 에서 끝나지 않고, 외부로부터 비동기 메시지를 받아들여 처리할 수 있다. 또 필요에 따라 처리의 결과를 반환하는 특징을 가진다.
- 관련 패턴
  - Producer-Consumer
    - Producer-Consumer 패턴은 Client 역할이 Scheduler 역할에게 ConcreateMethodRequest 역할을 안전하게 건네는데 사용된다.
    - Producer-Consumer 패턴의Channel 역할은 Active Object 패턴의 ActivationQueue 역할에 대응한다.
  - Future
    - Client 역할에게 반환 값(실행 결과)을 건넬 땐 Future 패턴이 사용된다.
  - Worker Thread
    - Scheduler 역할을 실현하는 부분에서 Worker Thread 패턴이 사용된다.
  - Thread-Specific Sotrage
    - Thread-Specific Storage 패턴은 쓰레드에게 고유 정보를 안겨주는 패턴이다.
    - Active Object 패턴에서는 메소드를 기동하는 쓰레드와 메소드를 실행하는 쓰레드가 다르기 때문에 Thread-Specific Sotrage 패턴을 적용하기 어렵다.

![KakaoTalk_20190314_001943328](https://user-images.githubusercontent.com/7076334/54291782-7346d400-45f0-11e9-950d-f49ab13e53bd.jpg)

## Appendix A
- reorder 란
  - reorder란 최적화를 위해 컴파일이나 Java 가상머신이 프로그램의 처리 순서를 바꾸는 것을 말한다.
  - 멀티 쓰레드 프로그램에서는 reorder가 원인이 되어 이상 작동을 하는 경우가 있다.
- visibility 란
  - 쓰레드 A가 필드 x에 입력한 값을 쓰레드 B에서 읽을 수 있다고 한다. 이것을 두고 '쓰레드 A가 x에 입력한 내용을 쓰레드 B가 볼 수 있다(visible이다)고 말한다.
- synchronized
  - synchronized는 쓰레드의 배타제어를 한다.
    - synchronized가 있으면 쓰레드는 거기에서 lock/unlock 액션을 실행한다.
    - lock/unlock이 이뤄지는 것은 synchronized 부분만 아니라 wait 메소드 내부에서 기다릴 때에도 락을 해제한다. 또한 wait 메소드로부터 나올 때에도 락을 다시 취한 뒤 움직인다.
    - 이미 다른 쓰레드가 락을 취하고 있다면 다른 쓰레드는 wait 셋에 들어간다. 배타제어 (mutual exclusion)가 실현된다.
  - 동기화
    - Java 메모리 모델은 어떠한 쓰레드가 unlock보다 먼저 실행한 입력은 모두 lock을 실행한 쓰레드가 볼 수 있다는 것을 보증한다.
- volatile
  - volatile은 동기화를 한다. (캐시되지 않는다.)
  - volatile은 쓰레드의 배타제어를 하지 않는다.
  - 앞에서 "long과 double은 최소 단위로 취급하지 않는다. (synchronized 하지 않다.) =>32비트에서는 원자적, 64비트에서는 원자적이지 않다." 라고 했는데 volatile은 long과 double을 최소 단위로 취급한다.
- final
  - 변하지 않는 필드는 final로 하라
    - java 메모리 모델은 final 필드에 관하여 생성자가 끝나면 정상적으로 보인다는 사실을 보증한다.
  - 생성자에서 this를 놓치지 말라 (주의하라)
    - 생성자 안에서 클래스 필드에 this를 대입하는 것은 위험하다. 다른 쓰레드가 클래스 필드를 경유하여 만들기 시작한 인스턴스에 액세스할지도 모르기 때문이다.
    - 생성자 안에서 this를 인수로 부여한 메소드를 것도 위험하다. 그 메소드가 다른 쓰레드로부터 액세스 되는 장소에 this를 놓을 가능성이 있기 때문이다.
  
  
  

  

  
  
  

