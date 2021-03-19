package com.company;

public class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;

    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal;
    }

    enum TokenType {


        ASSIGN, COMMA, SEMICOLON, DOT, OPEN_PAREN, CLOSE_PAREN, EOF,
        INTEGER, UNDEFINED, HEX_COLOR, O_BR, C_BR, FIGURE,

        IDENTIFIER,DEF,
    }
}
