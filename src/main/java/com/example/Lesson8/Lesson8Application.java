package com.example.Lesson8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Lesson8Application {



	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext context = SpringApplication.run(Lesson8Application.class, args);
		TestTimerClassB classB = context.getBean(TestTimerClassB.class);
		classB.testMinutes(1);
		classB.testSeconds(20);

		TestTimerClassA classA = context.getBean(TestTimerClassA.class);
		classA.testMinutes(1);
		classA.testSeconds(15);

		TestRecoveryClass recoveryClass = context.getBean(TestRecoveryClass.class);
		System.out.println(recoveryClass.testExceptionA("123456789000"));
		System.out.println(recoveryClass.testExceptionA(""));
	}

}
