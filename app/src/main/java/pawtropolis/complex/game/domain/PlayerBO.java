package pawtropolis.complex.game.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class PlayerBO{
	private Long id;

	private String name;

	private int lifePoints;

	private BagBO bag;

	private static final int DEFAULT_LIFE_POINTS = 100;

	private PlayerBO(Long id, String name, int lifePoints, BagBO bag) {
		this.id = id;
		this.name = name;
		this.lifePoints = (lifePoints != 0) ? lifePoints : DEFAULT_LIFE_POINTS;
		this.bag = (bag != null) ? bag : BagBO.builder().build();
	}

	public Map<String, Integer> getBagContent(){
		return this.bag.getItemsName();
	}

	public int getAvailableSlot(){
		return this.bag.getAvailableSlot();
	}

	public void collectItem(ItemBO item){
		this.bag.addItem(item);
	}

	public ItemBO removeItem(ItemBO item) {
		return this.bag.removeItem(item);
	}

	public ItemBO dropItemByName(String itemName){
		return this.bag.removeItemByName(itemName);
	}

	public boolean checkItemFitsInBag(ItemBO item) {
		return this.bag.checkItemFits(item);
	}

}
