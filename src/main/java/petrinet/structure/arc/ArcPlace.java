package petrinet.structure.arc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import petrinet.structure.Place;

@Getter
@Setter
@ToString
public class ArcPlace extends Arc {

    private Place place;

    public ArcPlace(Place place) {
        this.place = place;
    }
}
