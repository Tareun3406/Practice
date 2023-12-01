package kr.tareun.practice.dependencyInjection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiTestWithAbstraction {

    private AbstractStaticMethod abstractStaticMethod;
    private DiService diService;

    class AbstractStaticMethodTestImpl implements AbstractStaticMethod {

        @Override
        public String abstractionStaticMethod(String name) {
            return name;
        }
    }

    @BeforeEach
    public void beforeEach() {
        abstractStaticMethod = new AbstractStaticMethodImpl();
        diService = new DiService(abstractStaticMethod);
    }


    @Test
    public void testWithStaticMethod() {
        // given
        String name = "static method";

        // when
        String called = diService.abstractionStaticMethod(name);

        // then
        Assertions.assertThat(called).isEqualTo("abstraction call : " + name + " hello!");
    }

}