package versao2.estructure.arc;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import versao2.estructure.Transition;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class ArcTransition implements Arc {

    private List<Transition> transitionsToGo;

    public ArcTransition(List<Transition> transitionsToGo) {
        this.transitionsToGo = transitionsToGo;
    }
}
