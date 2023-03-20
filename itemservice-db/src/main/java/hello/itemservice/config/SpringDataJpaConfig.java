package hello.itemservice.config;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.jpa.JpaItemRepositoryV1;
import hello.itemservice.repository.jpa.JpaItemRepositoryV2;
import hello.itemservice.repository.jpa.SpringDatajpaItemRepository;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;

/**
 * <pre>
 * hello.itemservice.config
 * SpringDataJpaConfig.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 20. 오전 10:13:18
 * @desc    : 
 * @version : x.x
 */
@Configuration
@RequiredArgsConstructor
public class SpringDataJpaConfig {

	private final SpringDatajpaItemRepository datajpaItemRepository;
	
	@Bean
    public ItemService itemService() {
    	return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
    	return new JpaItemRepositoryV2(datajpaItemRepository);
    }

}
