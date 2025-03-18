package edu.miu.waa.repo;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-unit-test.properties")
@ActiveProfiles({"unit-test"})
@Transactional
public class AbstractRepoTest {}
