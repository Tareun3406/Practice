package kr.tareun.practice.multiTransaction;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TransactionInnerService {

    private final TransactionRepository transactionRepository;

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
