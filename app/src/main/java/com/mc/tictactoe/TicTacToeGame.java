package com.mc.tictactoe;

enum Character { X, O };
enum CellData { Null, X, O };
enum GameResult { Xwin, Owin, Tie, None };

public class TicTacToeGame {
    private Character currentCharacter;
    private CellData[][] field = new CellData[3][3];

    public TicTacToeGame(Character character) {
        currentCharacter = character;

        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                field[y][x] = CellData.Null;
    }

    void step(int y, int x)
    {
        if (x >= 3 || y >= 3)
            throw new ArrayIndexOutOfBoundsException();

        if (field[y][x] != CellData.Null) return;

        field[y][x] = currentCharacter == Character.X ? CellData.X : CellData.O;

        currentCharacter = currentCharacter == Character.X ? Character.O : Character.X;
    }

    GameResult cellDataToGameResult(CellData cellData) {
        switch (cellData) {
            case X: return GameResult.Xwin;
            case O: return GameResult.Owin;
            default: throw new IllegalArgumentException("cellData must be X or O");
        }
    }
    
    GameResult isGameOver()
    {
        GameResult result;
        for (int i = 0; i<3; i++)
        {
            if (field[i][0] != CellData.Null && field[i][0] == field[i][1] && field[i][0] == field[i][2]) {
                return cellDataToGameResult(field[i][0]);
            }
            if (field[0][i] != CellData.Null && field[0][i] == field[1][i] && field[0][i] == field[2][i]) {
                return cellDataToGameResult(field[0][i]);
            }
        }
        if (field[1][1] != CellData.Null &&
                ((field[1][1] == field[0][0] && field[1][1] == field[2][2])
                        || (field[1][1] == field[0][2] && field[1][1] == field[2][0]))
        ) {
            return cellDataToGameResult(field[1][1]);
        }

        boolean emptyCell = false;
        for (int y = 0; y < 3 && !emptyCell; y++)
            for (int x = 0; x < 3 && !emptyCell; x++)
                if (field[y][x] == CellData.Null)
                    emptyCell = true;

        if (!emptyCell) {
            return GameResult.Tie;
        }

        return GameResult.None;
    }

    void reset(Character character)
    {
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                field[y][x] = CellData.Null;

        currentCharacter = character;
    }

    Character getCurrentCharacter()
    {
        return currentCharacter;
    }

    CellData at(int y, int x)
    {
        return field[y][x];
    }

}
