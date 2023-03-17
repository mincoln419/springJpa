package hello.itemservice.repository.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;

/**
 * <pre>
 * hello.itemservice.repository.mybatis
 * ItemMapper.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 17. 오후 5:36:05
 * @desc    : 
 * @version : x.x
 */
@Mapper
public interface ItemMapper {

	void save(Item item);
	
	void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);
	
	List<Item> findAll(ItemSearchCond itemSearch);
	
	Optional<Item> findById(Long id);
}
