import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Public class for coin sorting
 *@author 26roederer
 *@version 03.30.2023
 */
public class CoinSorterMachine {
    private ArrayList<Coin> coins;
    private ArrayList<Coin> coinMap;
    private int[] coinCounts ={0,0,0,0,0,0};
    /**
     *  initializes coins ArrayList
     */
    public CoinSorterMachine(){
        coins= new ArrayList<>();
        coinMap= new ArrayList<>();
        coinMap.add(new Penny());
        coinMap.add(new Nickel());
        coinMap.add(new Dime());
        coinMap.add(new Quarter());
        coinMap.add(new HalfDollar());
        coinMap.add(new Dollar());
    }
    private Coin makeCoin(double value){
        for(Coin c: coinMap)    {
            if(c.getValue()== value)
                return c;
        }
        return null;
    }

    /**
     * use one or two Scanner objects, prompting user for the appropriate file
     name and importing the data from filename – MUST handle diabolic values!
     */
    public void depositCoins(){
        Scanner userIn=new Scanner(System.in);
        System.out.println("Enter the name of the data file for coin deposit: ");
        String filename= userIn.nextLine();
        userIn.close();
        System.out.println("Depositing coins...");
        try{
            Scanner in= new Scanner(new File("coins1.txt"));
            int temp;
                    while(in.hasNext()) {
                        temp = in.nextInt();
                        Coin c= makeCoin(temp/100.00);
                        if(c==null)
                            System.out.println("CoinValues"+ temp+ " not recognized");
                        else {
                            coins.add(c);
                            coinCounts[coinMap.indexOf(c)]++;
                        }
                    }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Prints deposit summary using a DecimalFormat object (see output section)
    */
    public void printDepositSummary(){
        DecimalFormat df= new DecimalFormat("$0.00");
        System.out.println("Summary of deposit: ");
        for (int i=0;i< coinCounts.length;i++) {
            System.out.print("\t"+ coinCounts[i] +" ");
            System.out.print(coinCounts[i]> 1? coinMap.get(i).getPluralName() : coinMap.get(i).getName() + " ");
            System.out.println(df.format(coinCounts[i]*coinMap.get(i).getValue()));
        }
        System.out.println("TOTAL DEPOSIT:"+ df.format(getTotalValue()));
    }

    /**
     * return the total value of all Coin objects stored in coins as a double
     */

    public double getTotalValue() {
        double total = 0;
        double[] vals={.01,.05,.10,.25,.5,1};
        for (int i = 0; i < coinCounts.length; i++) {
            total += coinCounts[i] * vals[i];
        }
        return total;
    }
    public static void main(String[] args){
        CoinSorterMachine app = new CoinSorterMachine();
        app.depositCoins();
        app.printDepositSummary();
    }
}
