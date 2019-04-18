package versao2.estructure.arc;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import versao2.estructure.Place;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class ArcPlace implements Arc {

    private List<Place> placesToGo;
    private List<Place> placesBefore;

    public ArcPlace(List<Place> placesToGo, List<Place> placesBefore) {
        this.placesToGo = placesToGo;
        this.placesBefore = placesBefore;
    }
}
