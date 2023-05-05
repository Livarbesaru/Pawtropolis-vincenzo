package pawtropolis.persistence.marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pawtropolis.game.domain.RoomBO;
import pawtropolis.game.map.util.CardinalPoint;
import pawtropolis.persistence.entity.Room;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RoomMarshaller implements Marshaller<Room, RoomBO> {

    private final ItemMarshaller itemConverter;

    private final AnimalMarshaller animalMarshaller;

    @Autowired
    private RoomMarshaller(ItemMarshaller itemConverter, AnimalMarshaller animalMarshaller) {
        this.itemConverter = itemConverter;
        this.animalMarshaller = animalMarshaller;
    }

    @Override
    public Room marshall(RoomBO roomBO) {
        return marshallAllLinkedRoomBO(roomBO);
    }

    private Room marshallAllLinkedRoomBO(RoomBO roomBO) {
        Map<RoomBO, Room> convertedRooms = marshallAllLinkedRoomBO(roomBO, new HashMap<>());
        return convertedRooms.get(roomBO);
    }

    private Map<RoomBO, Room> marshallAllLinkedRoomBO(RoomBO roomBO, Map<RoomBO, Room> convertedRooms) {
        if (!convertedRooms.containsKey(roomBO)) {
            Room room = marshallSingleRoomBO(roomBO);
            convertedRooms.put(roomBO, room);
            roomBO.getAdjacentRooms().forEach((cardinal, adjacentRoomBO) -> {
                marshallAllLinkedRoomBO(adjacentRoomBO, convertedRooms);
                Room adjacentRoom = convertedRooms.get(adjacentRoomBO);
                linkRooms(room, cardinal, adjacentRoom);
            });
        }
        return convertedRooms;
    }

    private void linkRooms(Room room1, CardinalPoint cardinalPoint, Room room2) {
        room1.getAdjacentRooms().put(cardinalPoint, room2);
    }

    private Room marshallSingleRoomBO(RoomBO roomBO) {
        return Room.builder()
                .id(roomBO.getId())
                .name(roomBO.getName())
                .items(roomBO.getItems().entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> this.itemConverter.marshall(entry.getKey()),
                                Map.Entry::getValue
                        )))
                .adjacentRooms(new EnumMap<>(CardinalPoint.class))
                .animals(this.animalMarshaller.marshallToSet(roomBO.getAnimals()))
                .build();
    }

    @Override
    public RoomBO unmarshall(Room room) {
        return unmarshallAllLinkedRooms(room);
    }

    private RoomBO unmarshallAllLinkedRooms(Room room) {
        Map<Room, RoomBO> convertedRooms = unmarshallAllLinkedRooms(room, new HashMap<>());
        return convertedRooms.get(room);
    }

    private Map<Room, RoomBO> unmarshallAllLinkedRooms(Room room, Map<Room, RoomBO> convertedRooms) {
        if (!convertedRooms.containsKey(room)) {
            RoomBO roomBO = unmarshallSingleRoom(room);
            convertedRooms.put(room, roomBO);
            room.getAdjacentRooms().forEach(((cardinal, adjacentRoom) -> {
                unmarshallAllLinkedRooms(adjacentRoom, convertedRooms);
                RoomBO adjacentRoomBO = convertedRooms.get(adjacentRoom);
                roomBO.linkRoom(cardinal, adjacentRoomBO,roomBO.getAdjacentDoors().get(cardinal));
            }));
        }
        return convertedRooms;
    }


    private RoomBO unmarshallSingleRoom(Room room) {
        return RoomBO.builder()
                .id(room.getId())
                .name(room.getName())
                .items(room.getItems().entrySet().stream()
                        .collect(Collectors.toMap(
                                entry -> this.itemConverter.unmarshall(entry.getKey()),
                                Map.Entry::getValue
                        )))
                .animals(this.animalMarshaller.unmarshallFromSet(room.getAnimals()))
                .adjacentRooms(new EnumMap<>(CardinalPoint.class))
                .build();
    }

    @Override
    public Class<Room> getEntityClass() {
        return Room.class;
    }

    @Override
    public Class<RoomBO> getBoClass() {
        return RoomBO.class;
    }
}
