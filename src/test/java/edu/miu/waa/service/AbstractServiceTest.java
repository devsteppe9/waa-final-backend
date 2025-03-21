package edu.miu.waa.service;

import edu.miu.waa.config.TestSecurityConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Import(TestSecurityConfig.class)
@TestPropertySource(locations = "classpath:application-unit-test.properties")
@Transactional
@ActiveProfiles("unit-test")
public class AbstractServiceTest {}
