package com.list.repositories.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class ExceptionResponseDaoTest {

	@Test
	void testSearchClient() {
		ExceptionResponseDao dao = new ExceptionResponseDao();
		dao.setTitle("exception");
		dao.setExceptionMessages("general exception");
		dao.setStatus("NotFound");
		ExceptionResponseDao dao1 = new ExceptionResponseDao();
		dao1.setTitle(dao.getTitle());
		dao1.setExceptionMessages(dao.getExceptionMessages());
		dao1.setStatus(dao.getStatus());
		Assertions.assertTrue(dao1.equals(dao));
		log.info("hashcode value" + dao.hashCode());
		log.info("object string" + dao1.toString());
		Assertions.assertEquals(dao, dao1);

		ExceptionResponseDao dao2 = new ExceptionResponseDao("RestTemplate", "msg", "BadRequest");
		Assertions.assertNotEquals(dao, dao2);

	}

}
