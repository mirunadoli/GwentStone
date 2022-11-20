package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import fileio.CardInput;
import fileio.Input;
import fileio.StartGameInput;

import java.io.File;
import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();
        //TODO add here the entry point to your implementation


        // prescurtari
        ArrayList<ArrayList<CardInput>> decks1 = inputData.getPlayerOneDecks().getDecks();
        ArrayList<ArrayList<CardInput>> decks2 = inputData.getPlayerTwoDecks().getDecks();
        StartGameInput stInput;

        //instante necesare pt joc
        StartGameEngine startGame = new StartGameEngine();
        RunGame runGame = new RunGame();
        GameTable gameTable;
        Statistics stats = new Statistics();
        Player player1;
        Player player2;

        // pornirea jocurilor pe rand
        for (int i = 0; i < inputData.getGames().size(); i++) {
            stInput = inputData.getGames().get(i).getStartGame();
            player1 = new Player();
            player2 = new Player();
            gameTable = new GameTable();
            startGame.startNewGame(player1, decks1, player2, decks2, stInput);
            GameInfo info = new GameInfo(player1, player2, gameTable, inputData.getGames().get(i),
                    output, stats);
            runGame.playGame(info);
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
