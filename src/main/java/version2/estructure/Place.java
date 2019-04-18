package versao2.estructure;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import versao2.estructure.arc.ArcTransition;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class Place {

    public int tokenAmount;

    private List<ArcTransition> arcsWithTransitionsToGo;

    public Place(int tokenAmount, List<ArcTransition> arcsWithTransitionsToGo) {
        this.tokenAmount = tokenAmount;
        this.arcsWithTransitionsToGo = arcsWithTransitionsToGo;
    }

}
