package petrinet.estructure.arc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import petrinet.estructure.Place;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ArcPlace extends Arc {

    private Integer transitionName;

    private List<Place> placeList = new ArrayList<>();

    public ArcPlace(Integer transitionName) {
        this.transitionName = transitionName;
    }

}
