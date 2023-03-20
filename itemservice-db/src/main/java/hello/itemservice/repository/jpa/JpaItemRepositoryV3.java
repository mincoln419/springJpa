package hello.itemservice.repository.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import hello.itemservice.domain.Item;
import static hello.itemservice.domain.QItem.item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * hello.itemservice.repository.jpa
 * JpaItemRepository.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 20. 오전 9:15:46
 * @desc    : query dsl 적용
 * @version : x.x
 */
@Slf4j
@Repository
@Transactional
public class JpaItemRepositoryV3 implements ItemRepository {
	
	private final EntityManager em;
	private final JPAQueryFactory query;
	
	
	/**
	 * @param query
	 */
	public JpaItemRepositoryV3(EntityManager em) {
		this.em = em;
		this.query = new JPAQueryFactory(em);
	}

	@Override
	public Item save(Item item) {
		em.persist(item);
		return item;
	}

	@Override
	public void update(Long itemId, ItemUpdateDto updateParam) {
		Item findItem = em.find(Item.class, itemId);
		findItem.setItemName(updateParam.getItemName());
		findItem.setPrice(updateParam.getPrice());
		findItem.setQuantity(updateParam.getQuantity());
	}

	@Override
	public Optional<Item> findById(Long id) {
		Item item = em.find(Item.class, id);
		return Optional.ofNullable(item);
	}

	public List<Item> findAll_old(ItemSearchCond cond) {
		
		Integer maxPrice = cond.getMaxPrice();
		String itemName = cond.getItemName();

		BooleanBuilder builder =  new BooleanBuilder();
		if(StringUtils.hasText(itemName)) {
			builder.and(item.itemName.like("%" + itemName + "%"));
		}
		
		if(maxPrice != null) {
			builder.and(item.price.loe(maxPrice));
		}
		
		List<Item> result = query
			.select(item)
			.from(item)
			.where(builder)
			.fetch();
		
		return result;
	}

	@Override
	public List<Item> findAll(ItemSearchCond cond) {
		Integer maxPrice = cond.getMaxPrice();
		String itemName = cond.getItemName();
		
		BooleanBuilder builder =  new BooleanBuilder();
		

		List<Item> result = query
			.select(item)
			.from(item)
			.where(likeItemName(itemName), maxPrice(maxPrice))
			.fetch();
		
		return result;
	}
	

	private BooleanExpression maxPrice(Integer maxPrice) {
		if(maxPrice != null) {
			return item.price.loe(maxPrice);
		}
		return null;
	}

	private BooleanExpression likeItemName(String itemName) {
		if(StringUtils.hasText(itemName)) {
			return item.itemName.like("%" + itemName + "%");
		}
		return null;
	}

}
