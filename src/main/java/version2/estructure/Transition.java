package versao2.estructure;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import versao2.estructure.arc.ArcPlace;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class Transition {

    private List<ArcPlace> arcWithPlacesToGoAndPlacesBefore;

    public Transition(List<ArcPlace> arcWithPlacesToGoAndPlacesBefore) {
        this.arcWithPlacesToGoAndPlacesBefore = arcWithPlacesToGoAndPlacesBefore;
    }

}
