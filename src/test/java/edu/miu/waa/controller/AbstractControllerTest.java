package edu.miu.waa.controller;

import edu.miu.waa.WaaApplication;
import edu.miu.waa.config.TestSecurityConfig;
import org.apiguardian.api.API;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = WaaApplication.class, properties = "spring.profiles.active=unit-test")
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-unit-test.properties")
@Transactional
public class AbstractControllerTest {
  private static final String API ="api/v1";
}
