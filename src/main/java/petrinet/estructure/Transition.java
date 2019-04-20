package petrinet.estructure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import petrinet.estructure.arc.ArcPlace;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Transition {

    private List<ArcPlace> arcPlaceWithPlacesBeforeList = new ArrayList<>();

    private List<ArcPlace> arcPlaceWithPlacesToGoList = new ArrayList<>();

    public Transition() {
    }

}
