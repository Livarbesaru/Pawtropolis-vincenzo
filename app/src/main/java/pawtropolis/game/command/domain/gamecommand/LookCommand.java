package pawtropolis.game.command.domain.gamecommand;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import pawtropolis.game.domain.DoorBO;
import pawtropolis.game.domain.GameSessionBO;
import pawtropolis.game.domain.RoomBO;
import pawtropolis.game.domain.animals.domain.AnimalBO;

import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class LookCommand extends GameCommand {

    protected LookCommand(GameSessionBO gameSessionBO) {
        super(gameSessionBO);
    }

    @Override
    public void execute() {
        RoomBO currentRoom = gameSessionBO.getCurrentRoom();
        Map<String, Integer> itemsName = currentRoom.getItemsName();
        Map<Class<? extends AnimalBO>, List<String>> animals = currentRoom.getAnimalsName();

        StringBuilder builder = new StringBuilder("\nYou are in " + currentRoom.getName() + ".\nItems: ");

        for (Map.Entry<String, Integer> entry : itemsName.entrySet()) {
            builder.append(entry.getKey())
                    .append("(x").append(entry.getValue())
                    .append("), ");
        }
        if (builder.toString().endsWith(", ")) {
            builder.delete(builder.length()-2, builder.length()-1);
        }

        builder.append("\nNPC: ");

        for (Map.Entry<Class<? extends AnimalBO>, List<String>> entry : animals.entrySet()) {
            for (String animalName : entry.getValue()) {
                builder.append(animalName)
                        .append("(").append(entry.getKey().getSimpleName().replaceAll("BO$","")).append(")");
                builder.append(", ");
            }
        }
        if (builder.toString().endsWith(", ")) {
            builder.delete(builder.length()-2, builder.length()-1);
        }

        builder.append("\nRooms: ");

        currentRoom.getAdjacentRooms().forEach((key,value) ->{
            DoorBO doorBO = currentRoom.getDoor(key);
            builder.append("\n"+key.toString()+":")
                    .append("(");
                    if(!ObjectUtils.isEmpty(doorBO) && doorBO.isOpen()){
                        builder.append("door is open").append(")");
                    }else if(!ObjectUtils.isEmpty(doorBO) && !doorBO.isOpen()){
                        builder.append("door is not open").append(")");
                    }else{
                        builder.append("wall").append(")");
                    }
            builder.append(", ");
        });
        if (builder.toString().endsWith(", ")) {
            builder.delete(builder.length()-2, builder.length()-1);
        }
        log.info(builder + "\n");
    }
}
