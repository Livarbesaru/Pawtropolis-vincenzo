package pawtropolis.complex.game.command.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pawtropolis.complex.game.GameController;
import pawtropolis.complex.game.animals.domain.Animal;
import pawtropolis.complex.game.map.domain.Room;

import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class LookCommand extends Command {
    protected LookCommand(GameController gameController) {
        super(gameController);
    }

    @Override
    public void execute() {
        Room currentRoom = gameController.getCurrentRoom();
        Map<String, Integer> itemsName = currentRoom.getItemsName();
        Map<Class<? extends Animal>, List<String>> animals = currentRoom.getAnimalsName();

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

        for (Map.Entry<Class<? extends Animal>, List<String>> entry : animals.entrySet()) {
            for (String animalName : entry.getValue()) {
                builder.append(animalName)
                        .append("(").append(entry.getKey().getSimpleName()).append(")");
                builder.append(", ");
            }
        }
        if (builder.toString().endsWith(", ")) {
            builder.delete(builder.length()-2, builder.length()-1);
        }
        log.info(builder + "\n");
    }
}
