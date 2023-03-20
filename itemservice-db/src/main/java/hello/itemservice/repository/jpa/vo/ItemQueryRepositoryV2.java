package hello.itemservice.repository.jpa.vo;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;

import static hello.itemservice.domain.QItem.item;

/**
 * <pre>
 * hello.itemservice.repository.jpa.vo
 * ItemQueryRepositoryV2.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 20. 오후 2:41:00
 * @desc    : 
 * @version : x.x
 */
@Repository
public class ItemQueryRepositoryV2 {

	private final JPAQueryFactory query;
	
	public ItemQueryRepositoryV2(EntityManager em) {
		this.query = new JPAQueryFactory(em);
	}
	
	public List<Item> findAll(ItemSearchCond cond) {
		Integer maxPrice = cond.getMaxPrice();
		String itemName = cond.getItemName();
		
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
