package petrinet.rules;

import org.junit.Assert;
import org.junit.Test;

public class PetriNetTest {

    PetriNet petriNet = new PetriNet();

    @Test
    public void bindLugaresDeEntradaNaTransicao_TestingReference() {

        String[] phrase = {"Lugares de entrada na transição", "T:1", "L:1"};

        petriNet.bindLugaresDeEntradaNaTransicao(phrase);

        Assert.assertNotNull(petriNet.getTransitionLinkedHashMap().get(1));
        Assert.assertNotNull(petriNet.getTransitionLinkedHashMap().get(1).getArcPlaceWithPlacesBeforeList().get(0));
        Assert.assertNotNull(petriNet.getTransitionLinkedHashMap().get(1).getArcPlaceWithPlacesBeforeList().get(0).getPlace());

    }
}
