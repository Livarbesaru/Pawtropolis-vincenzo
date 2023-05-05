package pawtropolis.game.map.initializer;

import org.springframework.stereotype.Component;
import pawtropolis.game.domain.RoomBO;
import pawtropolis.persistence.service.RoomService;

import java.util.ArrayList;
import java.util.List;

public class MapFromDB implements MapInitializer{

    private final RoomService roomService;

    public MapFromDB(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public RoomBO populateMap() {
        RoomBO roomBO = roomService.findById(1L);
        if(roomBO == null) return null;
        return getCopy(roomBO, new ArrayList<>());
    }
    private RoomBO getCopy(RoomBO roomBO, List<RoomBO> list){
        if(!list.contains(roomBO)){
            roomBO.setId(null);
            roomBO.getAnimals().forEach(animal -> animal.setId(null));
            list.add(roomBO);
            roomBO.getAdjacentRooms().forEach((key, value)->getCopy(value, list));
        }
        return roomBO;
    }
}
