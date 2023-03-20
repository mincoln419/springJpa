package hello.itemservice.repository.jpa;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * hello.itemservice.repository.jpa
 * JpaItemRepository.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 20. 오전 9:15:46
 * @desc    : 
 * @version : x.x
 */
@Slf4j
@Repository
@Transactional
@AllArgsConstructor
public class JpaItemRepositoryV2 implements ItemRepository {
	
	private final SpringDatajpaItemRepository repository;
	
	@Override
	public Item save(Item item) {
		return repository.save(item);
	}

	@Override
	public void update(Long itemId, ItemUpdateDto updateParam) {
		Item findItem = repository.findById(itemId).orElseThrow();
		findItem.setItemName(updateParam.getItemName());
		findItem.setPrice(updateParam.getPrice());
		findItem.setQuantity(updateParam.getQuantity());
	}

	@Override
	public Optional<Item> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Item> findAll(ItemSearchCond cond) {
		
		Integer maxPrice = cond.getMaxPrice();
		String itemName = cond.getItemName();
		if (StringUtils.hasText(itemName) && maxPrice != null) {
			return repository.findItems("%" + itemName + "%" , maxPrice);
		}else if (StringUtils.hasText(itemName)) {
			return repository.findByItemNameLike("%" + itemName + "%");
		}else if (maxPrice != null) {
			return repository.findByPriceLessThanEqual(maxPrice);
		}else {
			return repository.findAll();
		}
	}

}
