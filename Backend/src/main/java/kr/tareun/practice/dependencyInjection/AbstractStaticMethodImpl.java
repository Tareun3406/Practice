package kr.tareun.practice.dependencyInjection;

import org.springframework.stereotype.Component;

@Component
public class AbstractStaticMethodImpl implements AbstractStaticMethod{
    @Override
    public String abstractionStaticMethod(String name) {
        return StaticMethod.staticMethod(name);
    }
}
