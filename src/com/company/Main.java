package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        String path;
        path = "file1.txt";
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();

            run(line);

            //  if (hadError) System.exit(1);
            //   scanner.close();
        }

    }

    private static void run(String source) {
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.scanTokens();

        // For now, just print the tokens.
          for (Token token : tokens) {
          System.out.println(token.toString());
          }
    }
}

