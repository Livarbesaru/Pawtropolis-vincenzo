package pawtropolis.game.map.initializer;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import pawtropolis.game.domain.DoorBO;
import pawtropolis.game.domain.ItemBO;
import pawtropolis.game.domain.RoomBO;
import pawtropolis.game.domain.animals.domain.AnimalBO;
import pawtropolis.game.domain.animals.domain.EagleBO;
import pawtropolis.game.domain.animals.domain.LionBO;
import pawtropolis.game.domain.animals.domain.TigerBO;
import pawtropolis.game.map.util.CardinalPoint;

import java.time.LocalDate;
import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HardCodedMap implements MapInitializer {
    @Override
    public RoomBO populateMap() {
        ItemBO ball = ItemBO.builder()
                .name("ball").description("ball")
                .slotsRequired(6).build();
        ball.generateHashKey();

        ItemBO sword = ItemBO.builder()
                .name("sword").description("sharpened")
                .slotsRequired(10).build();
        sword.generateHashKey();

        ItemBO glasses = ItemBO.builder()
                .name("glasses").description("red")
                .slotsRequired(6).build();
        glasses.generateHashKey();

        ItemBO hat = ItemBO.builder()
                .name("hat").description("hat")
                .slotsRequired(7).build();
        hat.generateHashKey();

        ItemBO book = ItemBO.builder()
                .name("book").description("book")
                .slotsRequired(5).build();
        book.generateHashKey();

        ItemBO parruccaPlatinet = ItemBO.builder()
                .name("Parrucca di Platinet").description("parrucca")
                .slotsRequired(5).build();
        parruccaPlatinet.generateHashKey();

        AnimalBO eva = TigerBO.builder().name("Eva").favoriteFood("human")
                .age(4).joinDate(LocalDate.now()).weight(135.4)
                .height(1.55).tailLength(0.51).build();
        AnimalBO gina = TigerBO.builder().name("Gina").favoriteFood("lollipop")
                .age(4).joinDate(LocalDate.now()).weight(124.0)
                .height(1.45).tailLength(0.53).build();
        AnimalBO titina = TigerBO.builder().name("Titina").favoriteFood("hamburgher")
                .age(4).joinDate(LocalDate.now()).weight(121.0)
                .height(1.39).tailLength(0.5).build();
        AnimalBO lio = LionBO.builder().name("Lio").favoriteFood("confetti")
                .age(4).joinDate(LocalDate.now()).weight(130.0)
                .height(1.1).tailLength(0.49).build();
        AnimalBO beppe = LionBO.builder().name("Beppe").favoriteFood("gricia")
                .age(4).joinDate(LocalDate.now()).weight(129.0)
                .height(1.18).tailLength(0.43).build();
        AnimalBO tom = LionBO.builder().name("Tom").favoriteFood("human")
                .age(4).joinDate(LocalDate.now()).weight(124.0)
                .height(1.15).tailLength(0.39).build();
        AnimalBO pandora = EagleBO.builder().name("Pandora").favoriteFood("mouse")
                .age(4).joinDate(LocalDate.now()).weight(30.0)
                .height(1.05).wingspan(1.9).build();
        AnimalBO teodora = EagleBO.builder().name("Teodora").favoriteFood("rabbit")
                .age(4).joinDate(LocalDate.now()).weight(35.0)
                .height(0.89).wingspan(1.88).build();
        AnimalBO margareth = EagleBO.builder().name("Margareth").favoriteFood("rabbit")
                .age(4).joinDate(LocalDate.now()).weight(28.0)
                .height(0.92).wingspan(2.1).build();

        RoomBO starterRoom = RoomBO.builder().name("Starter Room").build();
        RoomBO roomSouth = RoomBO.builder().name("Room SOUTH").build();
        RoomBO roomNorth = RoomBO.builder().name("Room NORTH").build();
        RoomBO roomEast = RoomBO.builder().name("Room EAST").build();
        RoomBO roomWest = RoomBO.builder().name("Room WEST").build();

        DoorBO northDoor = DoorBO.builder().build();
        northDoor.setHashKey(ball.getHashKey());

        DoorBO southDoor = DoorBO.builder().build();
        southDoor.setHashKey(glasses.getHashKey());

        DoorBO eastDoor = DoorBO.builder().build();
        eastDoor.setHashKey(parruccaPlatinet.getHashKey());

        DoorBO westDoor = DoorBO.builder().build();
        westDoor.setHashKey(hat.getHashKey());

        starterRoom.addAllAnimals(List.of(eva, gina, teodora));
        roomSouth.addAllAnimals(List.of(lio, beppe));
        roomNorth.addAllAnimals(List.of(pandora, titina));
        roomEast.addAllAnimals(List.of(tom));
        roomWest.addAllAnimals(List.of(margareth));

        starterRoom.addAllItems(List.of(hat,parruccaPlatinet, glasses, ball));
        roomSouth.addItem(sword);
        roomNorth.addItem(hat);
        roomEast.addItem(book);
        roomWest.addItem(hat);

        starterRoom.linkRoom(CardinalPoint.SOUTH, roomSouth, southDoor);
        starterRoom.linkRoom(CardinalPoint.NORTH, roomNorth, northDoor);
        starterRoom.linkRoom(CardinalPoint.EAST, roomEast, eastDoor);
        starterRoom.linkRoom(CardinalPoint.WEST, roomWest, westDoor);

        return starterRoom;
    }
}
