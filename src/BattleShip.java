
import java.util.Arrays;
import java.util.Locale;
import  java.util.Random;
import java.util.Scanner;

public class BattleShip {
    public static final int Board_Size = 5;
    public static final int Ship_size = 3;
    public static final int Max_Ships = 3;
    public static char [][]  board;
    public static Scanner ler = new Scanner(System.in);
    public static int row, col;


    public static void main(String[] args) {
        short jogarNovamente;

        do{
            setupBoard();
            setupShips();
            printBoard();
            startGame();
            System.out.println("Jogar nove mais vezes? (1 para sim, 0 para não)");
            jogarNovamente = ler.nextShort();
        }while(jogarNovamente == 1);
        System.out.println("Fim do jogo!");
    };

    public static void setupBoard(){
        System.out.println("O tabuleiro está sendo iniciado. Aguarde um instate...");
        board = new char[Board_Size][Board_Size];
        for(int i = 0; i < Board_Size; i++){
            Arrays.fill(board[i], '-');
        }
    }

    public static void  setupShips(){

        Random rand = new Random();

        for(int shipAmount = 0; shipAmount < Max_Ships;){

            int posI = rand.nextInt(Board_Size);

            int posJ = rand.nextInt(Board_Size);

            if(insertShip(posI, posJ)){
                shipAmount++;
            }

        }
    }

   public static boolean insertShip(int i, int j){
    if(i + Ship_size <= Board_Size){
        if(!hasShipInColumn(i, j)){
            putShip(i, j, false);
            return true;
        }
    }else if(j + Ship_size <= Board_Size){
        if(!hasShipInRow(i, j)){
            putShip(i, j, true);
            return true;
        }
    }

    return  false;
   }

    public static boolean hasShipInColumn(int i, int j){
        return countShipPartsInColumn(i, j, '#') != 0;
    }

    public static int countShipPartsInColumn(int i, int j, char symbol){
        int countShips = 0;
        for(int k = i; k < (i + Ship_size); k++){
            if(board[k][j] == symbol) countShips++;
        }

        return countShips;
    }

    public static void putShip(int i, int j, boolean isRow) {
        for (int k = 0; k < Ship_size; k++) {
            if (isRow) {
                if (j + k < Board_Size) {
                    board[i][j + k] = '#';
                }
            } else {
                if (i + k < Board_Size) {
                    board[i + k][j] = '#';
                }
            }
        }
    }
    

    public static boolean hasShipInRow(int i, int j){
        return countShipPartsInRow(i, j, '#') != 0;
    }

    public static int countShipPartsInRow(int i, int j, char symbol){
        int countShips = 0;
        for(int k = j; k < (j + Ship_size); k++){
            if(board[ i ][ k ] == symbol) countShips++;
        }

        return countShips;
    }

    public static void printBoard(){
        System.out.print("   ");
        for (int i = 1; i <= Board_Size; i++) {
            System.out.print(" " + i + " ");
        }
        System.out.println();
    
        for(int i = 0; i < Board_Size; i++){
            System.out.print((char)(i + 65) + " |");
            printRow(board[i]);
        }
        printBottomLineBoard();
    }
    
    public static void printRow(char[] tab){
        for (char c : tab) {
            if (c == '#') 
                System.out.print("- ");
            else 
                System.out.print(" " + c + " ");
        }
        System.out.println();
    }
    
    public static void printBottomLineBoard() {
        System.out.println();
    }
    
    public static void startGame(){
        int round = 0;
        int partShipSunk = 0;
        do { 
            readCoordinates();
            System.out.println("================================================================================================================");
            System.out.printf("Tentativa: %d \n ====================", round ++);
            throwBomb();
            if(board[row][col] == 'x') partShipSunk++;
            
        } while (partShipSunk < Max_Ships * Ship_size);

        System.out.println("Parabéns! Você derrotou o navio!");
        System.out.print("Você levou " + round + " tentativas para derrotar o navio!");
    }

    public static void readCoordinates() {
        boolean inputValid;
        do {
            System.out.println("Digite as coordenadas da sua jogada (Ex: A 2 ou A2):");
            String input = ler.nextLine().toUpperCase(Locale.ROOT).replace(" ", "").trim();
    
            if (input.length() == 2) {
                char rowInput = input.charAt(0);
                char colInput = input.charAt(1);
    
                if (Character.isLetter(rowInput) && Character.isDigit(colInput)) {
                    row = rowInput - 'A';
                    col = colInput - '1';
                    inputValid = !isCoordinatesInvalid();
                } else {
                    System.out.println("Coordenadas inválidas. Certifique-se de usar letras para as linhas e números para as colunas.");
                    inputValid = false;
                }
            } else {
                System.out.println("Formato inválido. Use algo como 'A2' ou 'A 2'.");
                inputValid = false;
            }
        } while (!inputValid);
    }
    

    public static boolean isCoordinatesInvalid() {
        if (row < 0 || row >= Board_Size || col < 0 || col >= Board_Size) {
            System.out.println("Coordenadas inválidas!");
            return true;
        }
        if (board[row][col] == '~' || board[row][col] == 'x') {
            System.out.println("Você já escolheu essa casa em outra jogada. Não é possível jogar duas bombas no mesmo lugar.");
            return true;
        }
        return false;
    }

    public static void throwBomb() {
    System.out.println("Lançando bomba...");
    if (board[row][col] == '-') {
        board[row][col] = '~';
    } else {
        board[row][col] = 'x';
    }
    printBoard();
}

    
    

}


