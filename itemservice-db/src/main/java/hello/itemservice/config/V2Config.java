package hello.itemservice.config;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.jpa.JpaItemRepositoryV2;
import hello.itemservice.repository.jpa.JpaItemRepositoryV3;
import hello.itemservice.repository.jpa.vo.ItemQueryRepositoryV2;
import hello.itemservice.repository.jpa.vo.ItemRepositoryV2;
import hello.itemservice.service.ItemService;
import hello.itemservice.service.ItemServiceV1;
import hello.itemservice.service.ItemServiceV2;
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
public class V2Config {

	private final EntityManager em;
	
	private final ItemRepositoryV2 itemRepositoryV2;
	
	@Bean
    public ItemService itemService() {
    	return new ItemServiceV2(itemRepositoryV2, itemQueryRepositoryV2());
    }

	@Bean
	public ItemQueryRepositoryV2 itemQueryRepositoryV2() {
		return new ItemQueryRepositoryV2(em);
	}
	
	@Bean
	public ItemRepository itemRepository() {
		return new JpaItemRepositoryV3(em);
	}

}
