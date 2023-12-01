package kr.tareun.practice.dependencyInjection;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiService {

    private final AbstractStaticMethod abstractStaticMethod;

    public String staticMethodCall(String name) {
        String called = StaticMethod.staticMethod(name);
        return "nonAbstraction call : " + called;
    }

    public String abstractionStaticMethod(String name) {
        String called = abstractStaticMethod.abstractionStaticMethod(name);
        return "abstraction call : " + called;
    }
}
