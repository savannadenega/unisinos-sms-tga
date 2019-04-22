package petrinet;

import petrinet.rules.PetriNet;

import java.util.Scanner;

public class App {

    private App() {
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PetriNet petriNet = new PetriNet();

        petriNet.buildPetriNetfromTxtFile(System.getProperty("user.dir") + "/src/test/resources/fileReadExamplePetriNetComplex.txt");
        System.out.println("\n**** Leitura do arquivo realizada com sucesso ****\n");

        //esperando clicar ENTER para rodar a transicao
        //cada vez que rodar a transição, passar tokens (de acordo com o peso de cada arco) de cada place para transitions (em todos os places)
        System.out.println("**** Clique em [ENTER] para executar cada ciclo ****");

        while (true) {

            String input = scanner.nextLine();

            if (input.equals("")) {
                petriNet.fireOverRules();
            }

        }

    }



}
