public class TobiasAI
{
    public static String makeGuess(char[][] guesses){
        boolean[] ships = {true, true, true, true, true};   
        checkSunkShips(ships, guesses);
        int[][] likelyhood = new int[guesses.length][guesses.length];
        setLikelyhood(guesses, ships, likelyhood);
        huntMode(guesses, likelyhood);
        return getLocation(likelyhood);
    }
    public static int[][] huntMode(char[][] guesses, int[][] likelyhood){
        for(int row = 0; row < guesses.length; row ++){
            for(int col = 0; col < guesses[row].length; col ++){
                if(guesses[row][col] == 'X'){
                    likelyhood[row][col] = -99;
                    if(row > 0 && guesses[row - 1][col] == '.')
                        likelyhood[row - 1][col] += 25;
                    if(row < guesses.length - 1 && guesses[row + 1][col] == '.')
                        likelyhood[row + 1][col] += 25;
                    if(col > 0 && guesses[row][col - 1] == '.')
                        likelyhood[row][col - 1] += 25;
                    if(col < guesses[row].length - 1 && guesses[row][col + 1] == '.')
                        likelyhood[row][col + 1] += 25;
                    if(row > 0 && guesses[row - 1][col] == 'X'){
                        if(row > 1 && guesses[row - 2][col] == '.'){
                            likelyhood[row - 2][col] *= 10;
                        }
                        if(row < guesses.length - 1 && guesses[row + 1][col] == '.'){
                            likelyhood[row + 1][col] *= 10;
                        }
                    }
                    if(col > 0 && guesses[row][col - 1] == 'X'){
                        if(col > 1 && guesses[row][col - 2] == '.'){
                            likelyhood[row][col - 2] *= 10;
                        }
                        if(col < guesses[row].length - 1 && guesses[row][col + 1] == '.'){
                            likelyhood[row][col + 1] *= 10;
                        }
                    }
                }
            }
        }
        return likelyhood;
    }   
    public static String getLocation(int[][] likelyhood){
        int col = 0, row = 0, max = 0;
        String ans = "";
        for(int r = 0; r < likelyhood.length; r ++){
            for(int c = 0; c < likelyhood.length; c ++){
                if(likelyhood[r][c] > max){
                    max = likelyhood[r][c];
                    col = c;
                    row = r;
                }
            }
        }
        ans = Character.toString((char)(row + 'A'));
        ans += Integer.toString(col + 1);
        return ans;
    }
    public static int[][] setLikelyhood(char[][] guesses, boolean[] ships, int[][] likelyhood){
        int max = guesses.length;
        for(int row = 0; row < max; row ++){
            for(int col = 0; col < max; col ++){
                if(guesses[row][col] == '.' || guesses[row][col] == 'X'){
                    boolean col1 = (col + 1 < max && (guesses[row][col + 1] == '.' || guesses[row][col + 1] == 'X'));
                    boolean col2 = (col + 2 < max && (guesses[row][col + 2] == '.' || guesses[row][col + 2] == 'X'));
                    boolean col3 = (col + 3 < max && (guesses[row][col + 3] == '.' || guesses[row][col + 3] == 'X'));
                    boolean col4 = (col + 4 < max && (guesses[row][col + 4] == '.' || guesses[row][col + 4] == 'X'));
                    boolean row1 = (row + 1 < max && (guesses[row + 1][col] == '.' || guesses[row + 1][col] == 'X'));
                    boolean row2 = (row + 2 < max && (guesses[row + 2][col] == '.' || guesses[row + 1][col] == 'X'));
                    boolean row3 = (row + 3 < max && (guesses[row + 3][col] == '.' || guesses[row + 1][col] == 'X'));
                    boolean row4 = (row + 4 < max && (guesses[row + 4][col] == '.' || guesses[row + 1][col] == 'X'));
                    if(ships[0]){
                        if(col1){
                            likelyhood[row][col] ++;
                            likelyhood[row][col + 1] ++;
                        }
                        if(row1){
                            likelyhood[row][col] ++;
                            likelyhood[row + 1][col] ++;
                        }
                    }
                    if(ships[1]){
                        if(col1 && col2){
                            likelyhood[row][col] ++;
                            likelyhood[row][col + 1] ++;
                            likelyhood[row][col + 2] ++;
                        }
                        if(row1 && row2){
                            likelyhood[row][col] ++;
                            likelyhood[row + 1][col] ++;
                            likelyhood[row + 2][col] ++;
                        }
                    }
                    if(ships[2]){
                        if(col1 && col2){
                            likelyhood[row][col] ++;
                            likelyhood[row][col + 1] ++;
                            likelyhood[row][col + 2] ++;
                        }
                        if(row1 && row2){
                            likelyhood[row][col] ++;
                            likelyhood[row + 1][col] ++;
                            likelyhood[row + 2][col] ++;
                        }
                    }
                    if(ships[3]){
                        if(col1 && col2 && col3){
                            likelyhood[row][col] ++;
                            likelyhood[row][col + 1] ++;
                            likelyhood[row][col + 2] ++;
                            likelyhood[row][col + 3] ++;
                        }
                        if(row1 && row2 & row3){
                            likelyhood[row][col] ++;
                            likelyhood[row + 1][col] ++;
                            likelyhood[row + 2][col] ++;
                            likelyhood[row + 3][col] ++;
                        }
                    }
                    if(ships[4]){
                        if(col1 && col2 && col3 && col4){
                            likelyhood[row][col] ++;
                            likelyhood[row][col + 1] ++;
                            likelyhood[row][col + 2] ++;
                            likelyhood[row][col + 3] ++;
                            likelyhood[row][col + 4] ++;
                        }
                        if(row1 && row2 && row3 && row4){
                            likelyhood[row][col] ++;
                            likelyhood[row + 1][col] ++;
                            likelyhood[row + 2][col] ++;
                            likelyhood[row + 3][col] ++;
                            likelyhood[row + 4][col] ++;                   
                        }
                    }
                }
            }
        }
        return likelyhood;
    }
    public static void checkSunkShips(boolean[] ships, char[][] guesses){
        for(int row = 0; row < guesses.length; row ++){
            for(int col = 0; col < guesses.length; col ++){
                if(guesses[row][col] == '1'){
                    ships[0] = false;
                }else if(guesses[row][col] == '2'){
                    ships[1] = false;
                }else if(guesses[row][col] == '3'){
                    ships[2] = false;
                }else if(guesses[row][col] == '4'){
                    ships[3] = false;
                }else if(guesses[row][col] == '5'){
                    ships[4] = false;
                }
            }
        }
    }
}