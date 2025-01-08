package tw.com.ispan.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tw.com.ispan.domain.CustomerBean;

@SpringBootTest
public class CustomerServiceTests {
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void testLogin() {
		CustomerBean bean1 = customerService.login("Babe", "B");
		System.out.println("bean1="+bean1);
		
		CustomerBean bean2 = customerService.login(null, "B");
		System.out.println("bean2="+bean2);	
		
		CustomerBean bean3 = customerService.login("", "B");
		System.out.println("bean3="+bean3);	

		CustomerBean bean4 = customerService.login("Babe", null);
		System.out.println("bean4="+bean4);	

		CustomerBean bean5 = customerService.login("Babe", "");
		System.out.println("bean5="+bean5);	

	}
}
