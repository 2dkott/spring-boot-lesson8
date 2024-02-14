package com.example.Lesson8;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TestRecoveryClass {

    @RecoverException(noRecoverFor = {NoRecoveryExceptionB.class})
    public int testExceptionB(int flag) throws NoRecoveryExceptionB{
        if (flag < 0) throw new NoRecoveryExceptionB();
        if (flag > 2) throw new AssertionError();
        return flag;
    }

    @RecoverException(noRecoverFor = {NoRecoveryExceptionA.class})
    public String testExceptionA(String flag) throws Exception {
        if (flag.isEmpty()) throw new NoRecoveryExceptionA();
        if (flag.length() > 10) throw new Exception("testa");
        return flag;
    }
}
