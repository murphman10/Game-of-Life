/******************************
 * Asa Murphy
 * Assignment 4: Life Threads
 * Object Oriented Design 3354
 * 5/09/2019
 ******************************/

package LifeThreads;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadGrid
{
    int rows = 20;
    int cols = 20; //initialization for the rows and columns of the grid

    int gens; // Represents the number of generations to be viewed

    char[][] Gridread = new char[rows][cols]; //the grid being read the information from the file

    /*******************************************************************************************************************
     * Public ReadGrid
     *
     * Definition: This method reads a 20x20 grid from a text file, and stores it into a character array.
     ******************************************************************************************************************/

    public ReadGrid()
    {
        try {
                File fin = new File("C:\\Users\\asamu\\IdeaProjects\\Game Of Life\\src\\Grid.txt"); //File path

                Scanner s = new Scanner(fin);

                ArrayList<char[]> cellGrid= new ArrayList<>();

            do
            {
                if (s.hasNextInt())
                {
                    gens = s.nextInt();
                }
                else { cellGrid.add(s.nextLine().toCharArray()); }

            }while ((s.hasNext()));//While there is still data

            Gridread = cellGrid.toArray(new char[cellGrid.size()][]); //move grid to 2D array

            s.close(); //exit reading in the file

        } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    /*******************************************************************************************************************
     * Public Void showGrid
     * @param: char grid[][]
     *
     * Definition: Tis displays the grid after the text file has been read into it.
     ******************************************************************************************************************/

    public void display(char grid[][])
    {

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                System.out.print(grid[i][j]);
            }
            System.out.print("\n");
        }
    }


}








