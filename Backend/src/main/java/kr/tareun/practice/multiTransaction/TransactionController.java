package kr.tareun.practice.multiTransaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/multi-transaction-1")
    public String transactionInOneService1() {
        try {
            transactionService.transactionInOneService1();
        } catch (RuntimeException runtimeException) {
            log.error(runtimeException.getMessage());
        }
        return "실행 성공";
    }

    @GetMapping("/multi-transaction-2")
    public String transactionInnerService1() {
        try {
            transactionService.transactionInnerService1();
        } catch (RuntimeException runtimeException) {
            log.error(runtimeException.getMessage());
        }
        return "실행 성공";
    }

    @GetMapping("/multi-transaction-3")
    public String transactionInOneService2() {
        transactionService.transactionInOneService2();
        return "실행 성공";
    }

    @GetMapping("/multi-transaction-4")
    public String transactionInnerService2() {
        transactionService.transactionInnerService2();
        return "실행 성공";
    }
}
