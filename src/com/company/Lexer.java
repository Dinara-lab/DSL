package com.company;

import java.util.*;

import static com.company.Token.TokenType.*;

public class Lexer {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private int start = 0;
    private int current = 0;
    private int line = 1;


    Lexer(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '=': addToken(ASSIGN); break;
            case ';': addToken(EOF); break;
            case '(': addToken(OPEN_PAREN); break;
            case ')': addToken(CLOSE_PAREN); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '[': addToken(O_BR); break;
            case ']': addToken(C_BR); break;
            case '\'':figure(); break;
            case ':': addToken(SEMICOLON); break;
            case '#': color(); break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                break;
            default:
                if (isInteger(c)) {
                    number();
                }
                else if (isAlpha(c)) {
                        identifier();
                }
        }
    }



    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(Token.TokenType type) {
        addToken(type, null);
    }

    private void addToken(Token.TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private void figure() {
        while (peek() != '\'' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        advance();
        String value = source.substring(start + 1, current - 1);
        addToken(FIGURE, value);
    }

    private boolean isColor(char c) {
        return (c >= 'a' && c <= 'z') ||
        (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9' );
    }


    private void color() {
        int count = 0;
        while( count!= 5 && isColor(peek()) && !isAtEnd()) {
            advance();
            count++;
        }

        advance();
        //String value = source.substring(start, current);
        addToken(HEX_COLOR);
    }


    private boolean isInteger(char c) {
        return c >= '0' && c <= '9';
    }
    private void number() {
        while (isInteger(peek())) advance();


        if (peek() == '.' && isInteger(peekNext())) {
            advance();
            while (isInteger(peek())) advance();
        }

        addToken(INTEGER,
                Double.parseDouble(source.substring(start, current)));
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();
        String text = source.substring(start, current);
        Token.TokenType type = keywords.get(text);
        if (type == null) type = IDENTIFIER;
        addToken(type);
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isInteger(c);
    }

    private static final Map<String, Token.TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("def",    DEF);
        keywords.put("create_canvas",  IDENTIFIER);
        keywords.put("create_pt",   IDENTIFIER);
        keywords.put("add_pt",  IDENTIFIER);
        keywords.put("canvas_color", IDENTIFIER);
        keywords.put("save",    IDENTIFIER);
        keywords.put("blur",     IDENTIFIER);
        keywords.put("create_point",    IDENTIFIER);
        keywords.put("lighten",     IDENTIFIER);
        keywords.put("darken",  IDENTIFIER);
        keywords.put("canvas_gradient", IDENTIFIER);
        keywords.put("b_w",  IDENTIFIER);

    }
}

