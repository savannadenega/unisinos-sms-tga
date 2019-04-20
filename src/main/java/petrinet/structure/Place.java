package petrinet.structure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import petrinet.structure.arc.ArcTransition;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Place {

    private int tokenAmount = 0;

    private List<ArcTransition> arcsWithTransitionsToGoList = new ArrayList<>();

    public Place() {
    }

}
