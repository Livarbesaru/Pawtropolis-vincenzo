package pawtropolis.game.command.domain.gamecommand.parameterizedcommand;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import pawtropolis.console.InputController;
import pawtropolis.game.command.domain.gamecommand.LookCommand;
import pawtropolis.game.domain.*;
import pawtropolis.game.map.util.CardinalPoint;
import pawtropolis.persistence.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GoCommand extends ParameterizedCommand {
    private LookCommand lookCommand;
    @Autowired
    protected GoCommand(GameSessionBO gameSessionBO, LookCommand lookCommand) {
        super(gameSessionBO);
        this.lookCommand = lookCommand;
    }
    @Override
    public void execute() {
        CardinalPoint direction = CardinalPoint.of(parameter);
        if(ObjectUtils.isEmpty(direction)){
            log.info("\nUnrecognized direction\nWhere do you want to go? "
                    + Arrays.stream(CardinalPoint.values())
                    .map(c ->c.getName() +" - ")
                    .collect(Collectors.joining())+ "\n");
        }
        RoomBO currentRoom = gameSessionBO.getCurrentRoom();
        RoomBO adjacentRoom = currentRoom.getAdjacentRoom(direction);
        if (ObjectUtils.isEmpty(adjacentRoom)) {
            log.info("\nNothing to show in this direction!\n");
        } else {
            DoorBO directionDoor = currentRoom.getAdjacentDoor(direction);
            if(!ObjectUtils.isEmpty(directionDoor) && directionDoor.isOpen()){
                gameSessionBO.setCurrentRoom(adjacentRoom);
            }else if(!ObjectUtils.isEmpty(directionDoor) && !directionDoor.isOpen()){
                String choice = InputController.readChoice("The door is locked: would you like to use an item to unlock it?", List.of("Y","N"));
                switch (choice){
                    case "Y" -> useItemToOpen(directionDoor,adjacentRoom);
                    case "N" -> {}
                }
            }
            lookCommand.execute();
        }
    }

    private void useItemToOpen(DoorBO doorBO, RoomBO adjacentRoom){
        PlayerBO player = this.gameSessionBO.getPlayer();
        if(player.getBag().getItems().size() > 0){
            Map<String, ItemBO>mapItems = new HashMap<>();
            player.getBag().getItems().forEach((item,qnt)-> mapItems.put(item.getName(),item));

            String chosenItemString = InputController.readChoice("Choose an item in your bag to use as key",mapItems.keySet().stream().toList());
            ItemBO chosenItemObj = mapItems.get(chosenItemString);

            if(!ObjectUtils.isEmpty(chosenItemObj) && doorBO.changeState(chosenItemObj)){
                player.removeItem(chosenItemObj);
                log.info("You unlocked the door!");
                gameSessionBO.setCurrentRoom(adjacentRoom);
            }else{
                log.info("This is not the right item");
            }
        }else{
            log.info("you don't have items in your beg to use as key");
        }
    }
}
