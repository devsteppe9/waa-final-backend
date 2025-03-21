package edu.miu.waa.repo;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-unit-test.properties")
@ActiveProfiles("unit-test")
public class AbstractRepoTest {}
