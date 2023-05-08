package pawtropolis.game.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DoorBO implements BusinessObject{
    private boolean isOpen;
    private ItemBO keyItem;

    public boolean changeState(ItemBO keyItem){
        if(this.keyItem.getHashKey() == keyItem.getHashKey()){
            isOpen = true;
        }
        return isOpen;
    }
    public void setKeyItem(ItemBO keyItem){
        this.keyItem = keyItem;
    }
}
