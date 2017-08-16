package payProject;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-mybatis.xml"})

public class TestMain {
	
	
	@Resource
	private IUserService iUserService;
	
	@Test
	public void test1(){
		System.out.println("*********wqeqweqw*****:"+iUserService.getUserById(1).getUserName());
		
	}

}
