package petrinet.rules;

import lombok.Getter;
import petrinet.structure.Transition;
import petrinet.structure.arc.ArcTransition;
import petrinet.structure.arc.WeightArc;
import petrinet.structure.Place;
import petrinet.structure.arc.ArcPlace;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

@Getter
public class PetriNet {

    Map<Integer, Place> placeLinkedHashMap = new LinkedHashMap<>();
    LinkedHashMap<Integer, Transition> transitionLinkedHashMap = new LinkedHashMap<>();
    Map<String, ArcPlace> arcPlaceLinkedHashMap = new LinkedHashMap<>();
    Map<String, ArcTransition> arcTransitionLinkedHashMap = new LinkedHashMap<>();
    //TODO fazer classe padrão de hash para weightArcLinkedHashMap, padrão é L:T
    Map<String, WeightArc> weightArcLinkedHashMap = new LinkedHashMap<>();

    int positionTransition = 0;
    int cycle = 0;

    public PetriNet() {
    }

    public void buildPetriNetfromTxtFile(String path) {

        FileReader arq = null;
        BufferedReader read = null;
        String line = "";
        try {

            arq = new FileReader(path);
            read = new BufferedReader(arq);
            line = read.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (line != null) {

            String[] linhaAtualPartes = line.split("-");

            //compara o primeiro item da quebra
            //TODO fazer uma pattern factory, para cada montagem uma strategy

            //TODO extrair esses dois métodos de input para a classe input
            //TODO para o método de leitura do TXT, para cada método de bind dessa classe aqui, extrair o trecho de código split e fazer um método intermediador com o split, depois chamar o bind dessa classe aqui
            switch (linhaAtualPartes[0]) {

                case "Lugares de entrada na transição":
                    bindLugaresDeEntradaNaTransicao(linhaAtualPartes);
                    break;

                case "Lugares de saída da transição":
                    bindLugaresDeSaidaNaTransicao(linhaAtualPartes);
                    break;

                case "Quantidade marcas no lugar":
                    bindQuantidadeMarcasNoLugar(linhaAtualPartes);
                    break;

                case "Peso do arco da transição":
                    bindPesoDoArcoDaTransicao(linhaAtualPartes);
                    break;

                case "Peso do arco do lugar":
                    bindPesoDoArcoDoLugar(linhaAtualPartes);
                    break;

                default:
                    break;
            }

            try {
                line = read.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    protected void bindLugaresDeEntradaNaTransicao(String[] linhaAtualPartes) {

        String[] tArray;
        Integer tPosition;
        String[] lArray;
        Integer lPosition;

        tArray = linhaAtualPartes[1].split(":");
        tPosition = Integer.parseInt(tArray[1]);

        lArray = linhaAtualPartes[2].split(":");
        lPosition = Integer.parseInt(lArray[1]);

        Transition transition = transitionLinkedHashMap.get(tPosition);
        if (transition == null) {
            transition = new Transition();
            transitionLinkedHashMap.put(tPosition, transition);
        }

        Place place = placeLinkedHashMap.get(lPosition);
        if (place == null) {
            place = new Place();
            placeLinkedHashMap.put(lPosition, place);
        }

        ArcTransition arcTransition = new ArcTransition(tPosition);
        arcTransitionLinkedHashMap.put(lPosition + ":" + tPosition, arcTransition);

        place.getArcsWithTransitionsToGoList().add(arcTransitionLinkedHashMap.get(lPosition + ":" + tPosition));

        ArcPlace arcPlace = new ArcPlace(placeLinkedHashMap.get(lPosition));
        arcPlaceLinkedHashMap.put(lPosition + ":" + tPosition, arcPlace);

        transition.getArcPlaceWithPlacesBeforeList().add(arcPlaceLinkedHashMap.get(lPosition + ":" + tPosition));

    }

    private void bindLugaresDeSaidaNaTransicao(String[] linhaAtualPartes) {

        String[] tArray;
        Integer tPosition;
        String[] lArray;
        Integer lPosition;

        tArray = linhaAtualPartes[1].split(":");
        tPosition = Integer.parseInt(tArray[1]);

        lArray = linhaAtualPartes[2].split(":");
        lPosition = Integer.parseInt(lArray[1]);

        Transition transition = transitionLinkedHashMap.get(tPosition);
        if (transition == null) {
            transition = new Transition();
            transitionLinkedHashMap.put(tPosition, transition);
        }

        Place place = placeLinkedHashMap.get(lPosition);
        if (place == null) {
            place = new Place();
            placeLinkedHashMap.put(lPosition, place);
        }

        ArcPlace arcPlace = new ArcPlace(placeLinkedHashMap.get(lPosition));
        arcPlaceLinkedHashMap.put(tPosition + ":" + lPosition, arcPlace);

        transition.getArcPlaceWithPlacesToGoList().add(arcPlaceLinkedHashMap.get(tPosition + ":" + lPosition));

    }

    private void bindQuantidadeMarcasNoLugar(String[] linhaAtualPartes) {

        String[] lPositionArray = linhaAtualPartes[1].split(":");
        int lPosition = Integer.parseInt(lPositionArray[1]);

        int tokenAmount = Integer.parseInt(linhaAtualPartes[2]);

        Place place = placeLinkedHashMap.get(lPosition);
        if (place != null) {
            place.setTokenAmount(tokenAmount);
        }

    }

    private void bindPesoDoArcoDaTransicao(String[] linhaAtualPartes) {

        String[] tPositionArray = linhaAtualPartes[1].split(":");
        String tPosition = tPositionArray[1];

        String[] lPositionArray = linhaAtualPartes[3].split(":");
        String lPosition = lPositionArray[1];

        int arcWeight = Integer.parseInt(linhaAtualPartes[4]);

        WeightArc weightArc = weightArcLinkedHashMap.get(lPosition + ":" + tPosition);
        if (weightArc == null) {
            weightArc = new WeightArc(arcWeight);
            weightArcLinkedHashMap.put(lPosition + ":" + tPosition, weightArc);
        } else {
            weightArc.setWeight(arcWeight);
        }

        ArcPlace arcPlace = arcPlaceLinkedHashMap.get(tPosition + ":" + lPosition);
        arcPlace.setWeightArc(weightArc);

    }

    private void bindPesoDoArcoDoLugar(String[] linhaAtualPartes) {

        String[] lPositionArray = linhaAtualPartes[1].split(":");
        String lPosition = lPositionArray[1];

        String[] tPositionArray = linhaAtualPartes[3].split(":");
        String tPosition = tPositionArray[1];

        int arcWeight = Integer.parseInt(linhaAtualPartes[4]);

        WeightArc weightArc = weightArcLinkedHashMap.get(lPosition + ":" + tPosition);
        if (weightArc == null) {
            weightArc = new WeightArc(arcWeight);
            weightArcLinkedHashMap.put(lPosition + ":" + tPosition, weightArc);
        } else {
            weightArc.setWeight(arcWeight);
        }

        ArcPlace arcPlace = arcPlaceLinkedHashMap.get(lPosition + ":" + tPosition);
        arcPlace.setWeightArc(weightArc);

        ArcTransition arcTransition = arcTransitionLinkedHashMap.get(lPosition + ":" + tPosition);
        arcTransition.setWeightArc(weightArc);

    }

    public void fireOverRules() {

        if (cycle == 0) {
            printCurrentStatus(cycle);
        }
        cycle++;

        Transition transition = getElementByIndex(transitionLinkedHashMap, positionTransition);

        if (allPlacesHaveToken(transition)) {

            int indexPlus = 1;
            for (ArcPlace arcPlaceBefore : transition.getArcPlaceWithPlacesBeforeList()) {

                if (positionTransition == (transitionLinkedHashMap.size() - 1)) {
                    indexPlus = 0;
                }

                if (arcPlaceBefore.isEnabledToFire() == false) {
                    transition = getElementByIndex(transitionLinkedHashMap, positionTransition + indexPlus);
                    break;
                }
            }

            for (ArcPlace arcPlaceBefore : transition.getArcPlaceWithPlacesBeforeList()) {

                if (arcPlaceBefore.isEnabledToFire()) {

                    Place placeBefore = arcPlaceBefore.getPlace();

                    int weightAmountArcs = 0;
                    for (ArcTransition arcTransition : placeBefore.getArcsWithTransitionsToGoList()) {
                        weightAmountArcs += arcTransition.getWeightArc().getWeight();
                    }

                    //regra para verificar se a quantidade de tokens no place é o bastante para a quantidade de transições, se não sortear qual receber
                    if (placeBefore.getTokenAmount() < weightAmountArcs) {

                        for (int i = 0; i < placeBefore.getTokenAmount(); i++) {
                            int random = (int) (Math.random() * (placeBefore.getArcsWithTransitionsToGoList().size()) + 0);
                            Transition transitionRandomToBeFired = getElementByIndex(transitionLinkedHashMap, random);
                            fire(transitionRandomToBeFired, arcPlaceBefore);
                        }

                    } else {
                        fire(transition, arcPlaceBefore);
                    }
                }
                arcPlaceBefore.setEnabledToFire(false);

            }
        }

        for (ArcPlace arcPlaceToGo : transition.getArcPlaceWithPlacesToGoList()) {
            arcPlaceToGo.setFired(false);
        }

        printCurrentStatus(cycle);

        if (positionTransition >= transitionLinkedHashMap.size() - 1) {
            positionTransition = 0;
        }
        positionTransition++;

    }

    private boolean allPlacesHaveToken(Transition transition) {

        isTransitionAble(transition);

        for (ArcPlace arcPlaceBefore : transition.getArcPlaceWithPlacesBeforeList()) {
            arcPlaceBefore.setEnabledToFire(true);
        }

        return true;
    }

    private boolean isTransitionAble(Transition transition) {

        //regra para verificar se todos os places antes da transição atual tem token, para fazer a transição
        for (ArcPlace arcPlaceBefore : transition.getArcPlaceWithPlacesBeforeList()) {
            if (arcPlaceBefore.getPlace().getTokenAmount() < 1) {
                return false;
            }
        }

        return true;
    }

    private void fire(Transition transition, ArcPlace arcPlaceBefore) {

        Place placeBefore = arcPlaceBefore.getPlace();

        int tokenAmount = placeBefore.getTokenAmount();
        int arcWeightPlaceBefore = arcPlaceBefore.getWeightArc().getWeight();

        if (tokenAmount >= arcWeightPlaceBefore) {

            placeBefore.setTokenAmount(
                placeBefore.getTokenAmount() - arcWeightPlaceBefore
            );


            for (ArcPlace arcPlaceToGo : transition.getArcPlaceWithPlacesToGoList()) {

                if (arcPlaceToGo.isFired()) {
                    break;
                }

                Place placeToGo = arcPlaceToGo.getPlace();

                int arcWeightPlaceToGo = arcPlaceToGo.getWeightArc().getWeight();

                placeToGo.setTokenAmount(
                    placeToGo.getTokenAmount() + arcWeightPlaceToGo
                );

                arcPlaceToGo.setFired(true);

            }
        }


    }

    public Transition getElementByIndex(LinkedHashMap map, int index) {
        return (Transition) map.get((map.keySet().toArray())[index]);
    }

    public void printCurrentStatus(int cycle) {




        String lugar, marcas, transicao, habilitada;

        System.out.println("===============================");
        System.out.println("CICLO " + cycle);
        // Print places info
        System.out.println("\nLugares");

        lugar = "Lugar  ";
        marcas = "Marcas ";

        for (Map.Entry<Integer, Place> place : placeLinkedHashMap.entrySet()) {
            lugar += " | " + place.getKey();
            marcas += " | " + place.getValue().getTokenAmount();
        }

        System.out.println(lugar);
        System.out.println(marcas);

        System.out.println("\nTransições");
        transicao = "Transição   ";
        habilitada = "Habilitada? ";


        for (Map.Entry<Integer, Transition> transition : transitionLinkedHashMap.entrySet()) {

            transicao += " | " + transition.getKey();
            habilitada += " | " + (isTransitionAble(transition.getValue()) ? "S" : "N");

        }

        System.out.println(transicao);
        System.out.println(habilitada);

        int transitionsEnable = 0;
        for (Map.Entry<Integer, Transition> transition : transitionLinkedHashMap.entrySet()) {

            if (!isTransitionAble(transition.getValue())) {
                transitionsEnable++;
            }

        }


        if (transitionsEnable == transitionLinkedHashMap.size()) {
            System.out.println("\n\n===============================");
            System.out.println("\n **** Final da execução, deseja salvar um arquivo com o resultado? ****");
            System.out.println(" Digite:\n");
            System.out.println(" S + ENTER - Para salvar e sair");
            System.out.println(" ENTER - Para sair");

            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("S")) {

                writePetriNetFile(System.getProperty("user.dir") + "/src/main/resources/petrinetresult/PetriNetResult.txt");

            }

            System.exit(0);
        }

    }

    public void writePetriNetFile(String path) {

        FileWriter write = null;
        try {
            write = new FileWriter(path, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter printLine = new PrintWriter(write);

        String keyLugaresEntrada = "";
        for (Map.Entry<Integer, Transition> transition : transitionLinkedHashMap.entrySet()) {

            for (ArcPlace arcPlaceBeforeThisTransition : transition.getValue().getArcPlaceWithPlacesBeforeList()) {

                for (Map.Entry<String, ArcPlace> arcPlaceLinkedHashMap : arcPlaceLinkedHashMap.entrySet()) {
                    if (arcPlaceBeforeThisTransition.equals(arcPlaceLinkedHashMap.getValue())) {
                        keyLugaresEntrada = arcPlaceLinkedHashMap.getKey();
                        break;
                    }
                }

                String[] names = keyLugaresEntrada.split(":");

                printLine.println("Lugares de entrada na transição-T:" + transition.getKey() + "-L:" + names[0]);

            }
        }

        String keyLugaresSaida = "";
        for (Map.Entry<Integer, Transition> transition : transitionLinkedHashMap.entrySet()) {

            for (ArcPlace arcPlaceToGoThisTransition : transition.getValue().getArcPlaceWithPlacesToGoList()) {

                for (Map.Entry<String, ArcPlace> arcPlaceLinkedHashMap : arcPlaceLinkedHashMap.entrySet()) {
                    if (arcPlaceToGoThisTransition.equals(arcPlaceLinkedHashMap.getValue())) {
                        keyLugaresSaida = arcPlaceLinkedHashMap.getKey();
                        break;
                    }
                }

                String[] names = keyLugaresSaida.split(":");

                printLine.println("Lugares de saída da transição-T:" + transition.getKey() + "-L:" + names[1]);

            }
        }

        for (Map.Entry<Integer, Place> place : placeLinkedHashMap.entrySet()) {

            if(place.getValue().getTokenAmount() > 1){
                printLine.println("Quantidade marcas no lugar-L:" + place.getKey() + "-" + place.getValue().getTokenAmount());
            }

        }

        Integer placeIndex = 0;
        for (Map.Entry<Integer, Transition> transition : transitionLinkedHashMap.entrySet()) {

            for (ArcPlace arcPlaceToGoThisTransition : transition.getValue().getArcPlaceWithPlacesToGoList()) {

                for (Map.Entry<Integer, Place> placeLinked : placeLinkedHashMap.entrySet()) {
                    if (placeLinked.getValue().equals(arcPlaceToGoThisTransition.getPlace())) {
                        placeIndex = placeLinked.getKey();
                        break;
                    }
                }

                if(arcPlaceToGoThisTransition.getWeightArc().getWeight() > 1) {
                    printLine.println("Peso do arco da transição-T:" + transition.getKey() + "-para o lugar-L:" + placeIndex + "-" + arcPlaceToGoThisTransition.getWeightArc().getWeight());
                }
            }
        }

        for (Map.Entry<Integer, Transition> transition : transitionLinkedHashMap.entrySet()) {

            for (ArcPlace arcPlaceBeforeThisTransition : transition.getValue().getArcPlaceWithPlacesBeforeList()) {

                for (Map.Entry<Integer, Place> placeLinked : placeLinkedHashMap.entrySet()) {
                    if (placeLinked.getValue().equals(arcPlaceBeforeThisTransition.getPlace())) {
                        placeIndex = placeLinked.getKey();
                        break;
                    }
                }
                if(arcPlaceBeforeThisTransition.getWeightArc().getWeight() > 1) {
                    printLine.println("Peso do arco do lugar-L:" + placeIndex + "-para a transição-T:" + transition.getKey() + "-" + arcPlaceBeforeThisTransition.getWeightArc().getWeight());
                }
            }
        }

        printLine.close();

    }


}
