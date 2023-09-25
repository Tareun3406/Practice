package kr.tareun.practice.multiTransaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionInnerService transactionInnerService;

    // 내부에서 호출하는 메서드중 index 2일 때 실행되는 메서드는 실행 마지막에 예외를 던짐

    // 같은 서비스 클래스 내에서 트랜잭션 분리는 불가, 트랜잭션 하나로 실행됨 => 예외 발생시 모두 롤백
    @Transactional
    public void transactionInOneService1() {
        for (int index = 1; index < 4; index++){
            transactionChild(index * 10, index);
        }
    }

    // 서비스 클래스를 하나더 만들어 트랜잭션을 별도로 적용, 단 예외 발생시 이후의 코드는 진행되지 않음. => index 1=커밋됨, 2=롤백, 3=실행 안함
    @Transactional
    public void transactionInnerService1() {
        for (int index = 1; index < 4; index++){
            transactionInnerService.transactionChild(index * 10, index);
        }
    }

    // 같은 서비스 클래스 내에서 트랜잭션의 분리가 불가, child 메서드에서 던져진 예외가 본 메서드에서 catch 될시 롤백이 이루어지지 않음 => 모두 커밋됨
    @Transactional
    public void transactionInOneService2() {
        for (int index = 1; index < 4; index++){
            try{
                transactionChild(index * 10, index);
            } catch (RuntimeException runtimeException) {
                log.error(runtimeException.getMessage());
            }
        }
    }

    // 서비스 클래스를 하나더 만들어 트랜잭션을 별도로 적용, child 메서드가 각각 트랜잭션이 적용됨 => 1=커밋, 2=롤백, 3=커밋.
    @Transactional
    public void transactionInnerService2() {
        for (int index = 1; index < 4; index++){
            try {
                transactionInnerService.transactionChild(index * 10, index);
            } catch (RuntimeException runtimeException) {
                log.error(runtimeException.getMessage());
            }
        }
    }

    // 같은 클래스 내에서 호출 시 '@Transactional(propagation = Propagation.REQUIRES_NEW)' 옵션은 의미 없음
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transactionChild(int id,int count) {

        TransactionEntity entity1 = new TransactionEntity(id++, "test3");
        TransactionEntity entity2 = new TransactionEntity(id, "test4");
        transactionRepository.save(entity1);
        transactionRepository.save(entity2);

        if (count == 2){
            throw new RuntimeException("롤백 테스트 예외");
        }
    }

}
