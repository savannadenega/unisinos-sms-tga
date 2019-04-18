package versao2.rules;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ArcPlaceSimple {

    int placeToGo;
    int placeBefore;

}
