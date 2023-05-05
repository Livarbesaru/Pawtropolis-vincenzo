package pawtropolis.game.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DoorBO implements BusinessObject{
    private boolean isOpen;
    private int hashKey;

    public boolean changeState(int hashKey){
        if(this.hashKey == hashKey){
            isOpen = true;
        }
        return isOpen;
    }
    public void setHashKey(int hashKey){
        this.hashKey = hashKey;
    }
}
