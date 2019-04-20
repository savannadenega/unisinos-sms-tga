package petrinet.rules;

import org.junit.Assert;
import org.junit.Test;
import petrinet.rules.PetriNet;

public class PetriNetTest {

    PetriNet petriNet = new PetriNet();

    @Test
    public void bindLugaresDeEntradaNaTransicao_TestingReference() {

        String[] phrase = {"Lugares de entrada na transição", "T:1", "L:1"};

        petriNet.bindLugaresDeEntradaNaTransicao(phrase);

        Assert.assertNotNull(petriNet.getTransitionHashMap().get(1));
        Assert.assertNotNull(petriNet.getTransitionHashMap().get(1).getArcPlaceWithPlacesBeforeList().get(0));
        Assert.assertNotNull(petriNet.getTransitionHashMap().get(1).getArcPlaceWithPlacesBeforeList().get(0).getPlaceList().get(0));
        Assert.assertEquals(1, petriNet.getTransitionHashMap().get(1).getArcPlaceWithPlacesBeforeList().get(0).getPlaceList().get(0).getArcsWithTransitionsToGoList().get(0).getTransitionsToGoList().get(0).intValue());

    }
}
