package hello.itemservice.repository.jpa.vo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.itemservice.domain.Item;

/**
 * <pre>
 * hello.itemservice.repository.jpa.vo
 * ItemRepositoryV2.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 20. 오후 2:40:08
 * @desc    : 
 * @version : x.x
 */
@Repository
public interface ItemRepositoryV2 extends JpaRepository<Item, Long>{

}
