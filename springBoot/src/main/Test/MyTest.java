import com.controller.CustomAnotation.AppCustomComponents.CustomComponentTest;
import com.controller.Config.AppConfig;
import com.controller.CustomAnotation.AppCustomEnableAuto.EnableHelloWorld;
import com.controller.CustomAnotation.AppPropertyCondition.ConditionProperty;
import com.controller.Entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@EnableHelloWorld
@RunWith(SpringRunner.class)
//@TestPropertySource(locations = "classpath:application.properties")
@TestPropertySource(properties = "user.id=10")
//@SpringBootTest(properties = "user.id=11",classes = MyTest.class,webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MyTest {

    @Value("${user.id}")
    private String id;

    @Test
    public void startTest(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        CustomComponentTest test =  context.getBean(CustomComponentTest.class);
        System.out.println("CustomComponentTest = "+test);
    }



    @Test
    public void helloWorldTest(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        String text = context.getBean("helloWorld",String.class);
        System.out.println("data = "+text);
    }


    @Test
    public void conditionTest(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        String value = context.getBean("helloWorld",String.class);
        System.out.println(value);
    }

    @Test
    public void autoConfig(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        String value = context.getBean("helloWorld",String.class);
        System.out.println(value);
    }


    @Test
    public void testUser() {
        System.out.println(" id = "+id);
    }


}
