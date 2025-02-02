package pawtropolis.complex.game.command.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pawtropolis.complex.game.GameController;
import pawtropolis.complex.game.domain.Player;

import java.util.Map;

@Component
@Slf4j
public class BagCommand extends Command {

    protected BagCommand(GameController gameController) {
        super(gameController);
    }

    @Override
    public void execute() {
        Player player = this.gameController.getPlayer();
        Map<String, Integer> items = player.getBagContent();

        StringBuilder builder = new StringBuilder("\nIn bag: ");

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            builder.append(entry.getKey())
                    .append("(x").append(entry.getValue())
                    .append("), ");
        }
        if (builder.toString().endsWith(", ")) {
            builder.delete(builder.length()-2, builder.length()-1);
        }

        builder.append("   [Available Slot: ")
                .append(player.getAvailableSlot())
                .append("]");
        log.info(builder + "\n");
    }
}
