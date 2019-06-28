/******************************
 * Asa Murphy
 * Assignment 4: Life Threads
 * Object Oriented Design 3354
 * 5/09/2019
 ******************************/

package LifeThreads;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;


public class Cell implements Runnable
{
    int rows = 20;
    int cols = 20;
    char[][] Grid1 = new char[rows][cols];
    char[][] Grid2 = new char[rows][cols];

    public ReadGrid readGrid; //instantiation, giving cell access to methods from the ReadGrid class

    /*******************************************************************************************************************
     * Public Cell
     *
     * Definition: This method works as a constructor for how each cell in the grid will operate for the program. It
     * also transfers the data from the array that the file was read into, to a new array that we will use to give the
     * cells life.
     ******************************************************************************************************************/

    public Cell() //constructor
    {
        readGrid = new ReadGrid();
        for (int a = 0; a < rows; a++)
        {
            for (int b = 0; b < cols; b++)
            {
                Grid1[a][b] = readGrid.Gridread[a][b];
                Grid2[a][b] = readGrid.Gridread[a][b];//read data into both arrays from the array storing the data from
                                                      // the file.
            }
        }
    }

    /*******************************************************************************************************************
     * Public void run
     *
     * Definition: This method iterates through the generations of cells over time and displays the current state of the
     * cells on the grid.
     ******************************************************************************************************************/
    @Override
    public void run()
    {
        for (int i = 0; i <= readGrid.gens; i++)
        {
            System.out.println("Number of Generations " + i + ": ");

            readGrid.display(Grid2);

            for (int a = 0; a < rows; a++)

                for (int b = 0; b < cols; b++)
                {
                    Grid1[a][b] = Grid2[a][b];
                }
            RunCells();

            try {
                sleep(1000);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Cell.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /*******************************************************************************************************************
     * Public int CheckSurroundings
     * @param tempgrid
     * @param row
     * @param col
     * @return: neighbors
     *
     * Definition: This method determines the neighbors the cell may have an affiliation with at any given time
     * throughout the cycle.
     ******************************************************************************************************************/

    public int CheckSurroundings(char[][] tempgrid, int row, int col)
    {
        int area = 0;//represents the current cell checking its surroundings

        for (int i = -1; i <= 1; i++) //it's negative 1 because it needs to be read from far left to right
        {
            for (int j = -1; j <= 1; j++)
            {
                if (i == 0 && j == 0)
                {
                    continue;
                }

                if (row + i < 0 || row + i >= rows)
                {
                    continue;
                }

                if (col + j < 0 || col + j >= cols)
                {
                    continue;
                }

                if (status(tempgrid[row+i][col+j]))
                {
                    area++;
                }

            }

        }
        return area;

    }

    /*******************************************************************************************************************
     * Public Void Evoke
     * @param tempData
     * @param rows
     * @param cols
     *
     * Definition: Evoke is the foundation for the determine method. It acts as a constructor that takes in the data
     * that checks whether the cell is alive or dead.
     ******************************************************************************************************************/

    public void Evoke(char tempData, int rows, int cols){ Grid2[rows][cols] = tempData; }

    /*******************************************************************************************************************
     * Public void Determine
     * @param tempGrid2
     * @param row
     * @param cols
     *
     * Definition: This method determines whether a cell survives or dies given the conditions, Each cell with one or no
     * neighbors dies, as if by solitude,each cell with four or more neighbors dies, as if by overpopulation and
     * each cell with two or three neighbors survives. It then passes that data to
     ******************************************************************************************************************/

    public void Determine(char[][] tempGrid2, int row, int cols)
    {
        char temp = tempGrid2[row][cols];

        int localCell = CheckSurroundings(tempGrid2, row, cols);

        if (temp == 'X' && (localCell == 2 || localCell == 3))
        {
            Evoke('X', row, cols);

        } else if (temp == 'O' && localCell == 3)
          {
              Evoke('X', row, cols);
          }else
              Evoke('O', row, cols);

    }

    /*******************************************************************************************************************
     * public void EvaluateCells
     *
     * Definition: evaluateCells acts as an override to the run method gives each cell in the 20x20 grid the
     * functionality of knowing its state given the information from the determine class, and making the necessary
     * changes to each cell.
     ******************************************************************************************************************/

    public void RunCells()
    {
        ArrayList<Thread> arr = new ArrayList<>();

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                int thisRow = i;
                int thisCol = j;

                Thread thread = new Thread(() ->
                {
                    CheckSurroundings(Grid1, thisRow, thisCol);

                    Determine(Grid1, thisRow, thisCol);
                });

                thread.start();

                arr.add(thread);
            }

        }

        for (Thread thread : arr)
        {
            try
            {
                thread.join();
            } catch (Exception e)
            {
                System.out.println(e.getStackTrace());
            }

        }

    }

    /*******************************************************************************************************************
     * Public Boolean status
     * @param state
     * @return
     *
     * Definition: status takes the state of the cell in as a parameter and checks it using the condition that indicates
     * if the cell is alive or dead.
     ******************************************************************************************************************/
    public Boolean status(char state)
    {
        if (state == 'X')
        {
            return true; //its alive
        }
        else
        {
            return false; //its dead
        }

    }
}




