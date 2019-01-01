package codes;

import java.util.Scanner;

public class Mastermind {

    public static Scanner input = new Scanner(System.in);
    
    public static final int SIZE1 = 1296;
    public static final int SIZE2 = 4;
    public static final int SIZE3 = 7;
    
    public static int[][] manta = new int [SIZE1][SIZE2]; // all code possibilities
    public static boolean[] possible = new boolean [SIZE1]; // parallel array indicating whether or not the code is a possible solution
    public static int[] guess = new int[] {0,0,0,0}; // random guess from manta (possible)
    public static int[] duplicate = new int[] {0,0,0,0};
    
    public static int counter = 0, randnum = 0, correct = 0, misplaced = 0, response = 0, tryCorrect = 0, tryMisplaced = 0, position = 0;
    public static boolean check = true;
    
    public static int errorTrapRange(int min, int max)
    { // begin errorTrapRange
        
        response = 0;
        
        do
        {
            check = true;
            
            try
            {
                response = input.nextInt();
            }
            catch(Exception e)
            {
                input.next();
                
                check = false;
            }
            
            if (response < min || response > max || !check)
            {
                System.out.println("Invalid input. Try again.");
            }
        } while(response < min || response > max || !check);
        
        return response;
    } // end errorTrapRange
    
    public static void populateManta() // array: solution set
    { // begin populateManta
        
        counter = 0;
        
        for(int w = 1; w < SIZE3; w++)
            for(int x = 1; x < SIZE3; x++)
                for(int y = 1; y < SIZE3; y++)
                    for(int z = 1; z < SIZE3; z++)
                    {
                        manta[counter][0] = w;
                        manta[counter][1] = x;
                        manta[counter][2] = y;
                        manta[counter][3] = z;
                        
                        counter++;
                    }
    } // end populateManta
        
    public static void populateDuplicate(int counter) // array: duplicate of manta
    { // begin populateDuplicate
        
        duplicate = new int[] {manta[counter][0],manta[counter][1],manta[counter][2],manta[counter][3]};
    } // end populateDuplicate
    
    public static void populatePossible() // array: possible solutions in manta
    { // begin populatePossible
        
        counter = 0;
        
        for(int w = 1; w < SIZE3; w++)
            for(int x = 1; x < SIZE3; x++)
                for(int y = 1; y < SIZE3; y++)
                    for(int z = 1; z < SIZE3; z++)
                    {
                        possible[counter] = true;
                        
                        counter++;
                    }
    } // end populatePossible
    
    public static void guess() // generate a guess to input
    { // begin guess
        
        do
        {
            randnum = (int)(Math.random()*SIZE1);
        } while(possible[randnum] == false);
        
        guess = new int[] {manta[randnum][0],manta[randnum][1],manta[randnum][2],manta[randnum][3]};
    } // end guess
    
    public static void eliminateCorrectImpossibilities(int[] guess,int correct)
    { // begin eliminateCorrectImpossibilities
        
        for(counter = 0; counter < SIZE1; counter++)
        {
            tryCorrect = 0;
            
            if(correct != 4)
                possible[randnum] = false;
            
            if(possible[counter]) // if possible
            {    
                for(position = 0; position < 4; position++)
                    if(manta[counter][position] == guess[position])
                        tryCorrect++;
                
                if(tryCorrect != correct)
                    possible[counter] = false;
            }
            else // if impossible
                continue;
        }
    } // end eliminateCorrectImpossibilities
    
    public static void eliminateMisplacedImpossibilities(int[] guess, int misplaced)
    { // begin eliminateMisplacedImpossibilities
        
        for(counter = 0; counter < SIZE1; counter++)
        {
            tryMisplaced = 0;
            int[] dummy = guess;
            populateDuplicate(counter);
            
            if(possible[counter]) // if possible
            {
                // change matching before checking misplaced
                for(int a = 0; a < SIZE2; a++)
                    if(duplicate[a] == dummy[a])
                    {
                        duplicate[a] = -1;
                        dummy[a] = -1;
                    }
                
                // check misplaced
                for(int a = 0; a < SIZE2; a++)
                    for(int b = 0; b < SIZE2; b++)
                    {
                        if(duplicate[a] == dummy[b])
                        {
                            tryMisplaced++;
                            
                            duplicate[a] = -1;
                            dummy[b] = -2;
                        }
                    }
            }
            else
                continue;
        }
    } // end eliminateMisplacedImpossibilities
    
    public static void displayManta()
    { // begin displayManta
        
        System.out.println("\n");
        
        for(int counter = 0; counter < SIZE1; counter++)
        {
            if(possible[counter])
                System.out.println(manta[counter][0] + "" + manta[counter][1] + "" + manta[counter][2] + manta[counter][3]);                
            else 
                continue;
        }
        
        System.out.println("\n");
    } // end displayManta
    
    public static void main(String[] args) throws Exception{
        
        // generate all possibilities
        populateManta();
        
        //  possible vs impossible solutions
        populatePossible();
        
        do
        {
            // generate a sequence to guess
            guess();
            System.out.println("Try: " + guess[0] + "" + guess[1] + "" + guess[2] + "" + guess[3] + "\n");
        
            // determine how many correct & misplaced
            System.out.print("Indicate how many correct: ");
                correct = errorTrapRange(0,4);
                    if(correct == 4) // if the solution is found, terminate
                        break;        
            System.out.print("Indicate how many misplaced: ");
                misplaced = errorTrapRange(0,4);
            
            // eliminate impossibilities
            eliminateCorrectImpossibilities(guess,correct);
            eliminateMisplacedImpossibilities(guess,misplaced);
            
            // display possible solutions
            displayManta();
        } while (correct != 4);
        
        // print solution
        System.out.println("\nThe solution is: " + guess[0] + "" + guess[1] + "" + guess[2] + "" + guess[3]);
        
    } // end main

}