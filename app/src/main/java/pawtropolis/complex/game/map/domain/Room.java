package pawtropolis.complex.game.map.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import pawtropolis.complex.game.animals.domain.Animal;
import pawtropolis.complex.game.domain.Item;
import pawtropolis.complex.game.map.util.CardinalPoint;

import java.util.*;
import java.util.stream.Collectors;

@EqualsAndHashCode
@RequiredArgsConstructor
@ToString
@Slf4j
public class Room {
	@NonNull
	@Getter
	@Setter
	private String name;
	private final List<Item> items = new ArrayList<>();
	private final List<Animal> animals = new ArrayList<>();

	private final Map<CardinalPoint, Room> adjacentRooms = new EnumMap<>(CardinalPoint.class);

	public Room getAdjacentRoom(CardinalPoint cardinalPoint) {
		return this.adjacentRooms.get(cardinalPoint);
	}

	public List<Item> getItems(){
		return List.copyOf(this.items);
	}

	public void removeItem(Item item) {
		this.items.remove(item);
	}

	public void addItem(Item item) {
		this.items.add(item);
	}

	public void addAllItems(List<Item> items) {
		items.forEach(this::addItem);
	}

	public void addAnimal(Animal animal) {
		this.animals.add(animal);
	}

	public void addAllAnimals(List<Animal> animals) {
		animals.forEach(this::addAnimal);
	}

	public void removeAnimal(Animal animal) {
		animals.remove(animal);
	}

	public void linkRoom(CardinalPoint cardinalPoint, Room room){
		this.adjacentRooms.put(cardinalPoint, room);
		CardinalPoint opposite = cardinalPoint.getOpposite();
		Room oppositeRoom = room.getAdjacentRoom(opposite);
		if(oppositeRoom != this){
			room.linkRoom(opposite, this);
		}
	}

	public  List<Animal> getAnimals(){
		return List.copyOf(this.animals);
	}

	public Item findItemByName(String itemName) {
		return this.items.stream()
				.filter(i -> i.getName().equals(itemName))
				.findFirst()
				.orElse(null);
	}

	public List<String> getItemsName() {
		return this.items.stream()
				.map(Item::getName)
				.toList();
	}

	public Map<Class<? extends Animal>, List<String>> getAnimalsName() {
		return this.animals.stream()
				.collect(Collectors.groupingBy(
						Animal::getClass,
						Collectors.mapping(Animal::getName, Collectors.toList())
						));
	}
}
