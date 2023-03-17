package hello.itemservice.repository.mybatis;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.RequiredArgsConstructor;

/**
 * <pre>
 * hello.itemservice.repository.mybatis
 * MybatisItemRepository.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 17. 오후 6:00:17
 * @desc    : 
 * @version : x.x
 */
@Repository
@RequiredArgsConstructor
public class MybatisItemRepository implements ItemRepository {

	private final ItemMapper itemMapper;
	
	@Override
	public Item save(Item item) {
		itemMapper.save(item);
		return item; 
	}

	@Override
	public void update(Long itemId, ItemUpdateDto updateParam) {
		itemMapper.update(itemId, updateParam);		
	}

	@Override
	public Optional<Item> findById(Long id) {
		return itemMapper.findById(id);
	}

	@Override
	public List<Item> findAll(ItemSearchCond cond) {
		return itemMapper.findAll(cond);
	}


}
