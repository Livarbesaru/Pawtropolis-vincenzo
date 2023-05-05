package pawtropolis.game.domain;

import lombok.*;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class ItemBO implements BusinessObject {
	@Setter
	private Long id;

	private String name;

	private String description;

	private int slotsRequired;

	private int hashKey;

	public void generateHashKey(){
		hashKey = Objects.hash(name,description,slotsRequired);
	}
	public int getHashKey(){
		return hashKey;
	}
}
