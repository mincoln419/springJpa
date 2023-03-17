package hello.itemservice.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.jdbctemplate.JdbcTemplateItemRepositoryV3;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * hello.itemservice.config
 * JdbcTemplateV1Config.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 17. 오후 12:49:39
 * @desc    : 
 * @version : x.x
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class JdbcTemplateV3Config {
	private final DataSource dataSource;
	
	@Bean
	public ItemService itemService() {
		return new ItemServiceV1(itemRepository());
	}

	@Bean
	public ItemRepository itemRepository() {
		return new JdbcTemplateItemRepositoryV3(dataSource);
	}

}
