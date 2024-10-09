package soul.dev.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import soul.dev.common.config.AxonXstreamConfig;

@SpringBootApplication
@Import({ AxonXstreamConfig.class })
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
