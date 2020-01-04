import com.spring.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: LZJ
 * @Date: 2020/1/4 21:54
 * @Version 1.0
 */
public class UserServiceImplTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring-tx.xml");
        UserService service = context.getBean(UserService.class);
        service.createUser("lzj");
    }
}
