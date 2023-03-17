package hello.itemservice.repository.jdbctemplate;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.StringUtils;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * hello.itemservice.repository.jdbctemplate
 * JdbcTemplateItemRepository.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 17. 오후 12:27:17
 * @desc    : NamedParamterJdbcTemplate
 * @version : x.x
 */
@Slf4j
public class JdbcTemplateItemRepositoryV2 implements ItemRepository{

	public final NamedParameterJdbcTemplate template;
	
	
	
	/**
	 * @param template
	 */
	public JdbcTemplateItemRepositoryV2(DataSource datasource) {
		this.template = new NamedParameterJdbcTemplate(datasource);
	}

	@Override
	public Item save(Item item) {
		String sql =  "insert into item(item_name, price, quantity)"
				+ " values (:item_name,:price,:quantity)";
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		KeyHolder holder = new GeneratedKeyHolder();
		template.update(sql, param, holder);
		
		long key = holder.getKey().longValue();
		item.setId(key);
		
		return item;
	}

	@Override
	public void update(Long itemId, ItemUpdateDto updateParam) {
		String sql = "update item set item_name=:itemName, price=:price, quantity=:quantity where id=:id";
		
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("itemName", updateParam.getItemName())
				.addValue("price", updateParam.getPrice())
				.addValue("quantity", updateParam.getQuantity())
				.addValue("id", itemId);
		template.update(sql, param);
		
	}

	@Override
	public Optional<Item> findById(Long id) {
		String sql  ="select id, item_name, price, quantity from item where id = :id" ;
		Map<String, Object> param = Map.of("id", id);
		try{
			Item item =  template.queryForObject(sql, param, itemRowMapper());
			return Optional.of(item);
		}catch(EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}


	@Override
	public List<Item> findAll(ItemSearchCond cond) {
		String itemName = cond.getItemName();
		Integer maxPrice = cond.getMaxPrice();
		
		String sql  = "select id, item_name, price, quantity from item";
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(cond);
		
		//동적쿼리
		//동적 쿼리
		 if (StringUtils.hasText(itemName) || maxPrice != null) {
		 sql += " where";
		 }
		 boolean andFlag = false;
		 if (StringUtils.hasText(itemName)) {
		 sql += " item_name like concat('%',:itemName,'%')";
		 andFlag = true;
		 }
		 if (maxPrice != null) {
		 if (andFlag) {
		 sql += " and";
		 }
		 sql += " price <= :maxPrice";
		 }
		 log.info("sql={}", sql);
		
		return template.query(sql, param, itemRowMapper());
	}
	
	private RowMapper<Item> itemRowMapper() {
		return BeanPropertyRowMapper.newInstance(Item.class);
	}

}
