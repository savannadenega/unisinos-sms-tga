package petrinet.structure.arc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArcTransition extends Arc {

    private Integer transitionToGo;

    public ArcTransition(Integer transitionToGo) {
        this.transitionToGo = transitionToGo;
    }
}
