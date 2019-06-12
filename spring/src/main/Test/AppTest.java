import com.boundless.Bean.Animal;
import com.boundless.Bean.Person;
import com.boundless.config.AppConfig;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppTest {

    @Test
    public void appTest(){
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Person p = context.getBean(Person.class);
        System.out.println(p);

        Animal cat = context.getBean(Animal.class);
        System.out.println(cat);
    }

}
