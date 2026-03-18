//********************************************************************
//  Keyboard.java       Author: Lewis and Loftus
//
//  Facilitates keyboard input by abstracting details about input
//  parsing, conversions, and exception handling.
//********************************************************************

package keyboardinput;

import java.io.*;
import java.util.*;

/**
 * Facilita la lettura da tastiera astraendo i dettagli di parsing,
 * conversione e gestione delle eccezioni.
 */
public class Keyboard {

    // ************* Error Handling Section **************************

    /** Indica se gli errori di input devono essere stampati a schermo. */
    private static boolean printErrors = true;

    /** Contatore degli errori di input verificati. */
    private static int errorCount = 0;

    /**
     * Restituisce il numero corrente di errori di input.
     *
     * @return numero di errori
     */
    public static int getErrorCount() {
        return errorCount;
    }

    /**
     * Azzera il contatore degli errori di input.
     *
     * @param count parametro non utilizzato
     */
    public static void resetErrorCount(int count) {
        errorCount = 0;
    }

    /**
     * Indica se gli errori di input vengono attualmente stampati a schermo.
     *
     * @return true se la stampa degli errori è attiva
     */
    public static boolean getPrintErrors() {
        return printErrors;
    }

    /**
     * Imposta se gli errori di input devono essere stampati a schermo.
     *
     * @param flag true per abilitare la stampa degli errori
     */
    public static void setPrintErrors(boolean flag) {
        printErrors = flag;
    }

    /**
     * Incrementa il contatore degli errori e stampa il messaggio se appropriato.
     *
     * @param str messaggio di errore da stampare
     */
    private static void error(String str) {
        errorCount++;
        if (printErrors)
            System.out.println(str);
    }

    // ************* Tokenized Input Stream Section ******************

    /** Token corrente già letto ma non ancora consumato. */
    private static String current_token = null;

    /** Tokenizer per la riga di input corrente. */
    private static StringTokenizer reader;

    /** Stream di lettura da tastiera. */
    private static BufferedReader in = new BufferedReader(
            new InputStreamReader(System.in));

    /**
     * Restituisce il prossimo token assumendo che possa trovarsi su righe successive.
     *
     * @return prossimo token letto
     */
    private static String getNextToken() {
        return getNextToken(true);
    }

    /**
     * Restituisce il prossimo token, che potrebbe essere già stato letto.
     *
     * @param skip true per saltare i delimitatori e cercare sulle righe successive
     * @return     prossimo token letto
     */
    private static String getNextToken(boolean skip) {
        String token;

        if (current_token == null)
            token = getNextInputToken(skip);
        else {
            token = current_token;
            current_token = null;
        }

        return token;
    }

    /**
     * Legge il prossimo token dall'input, che può trovarsi sulla riga correnteo su una successiva.
	 *  Il parametro determina se usare le righe successive.
     *
     * @param skip true per saltare i delimitatori e proseguire sulle righe successive
     * @return     prossimo token letto, null in caso di errore
     */
    private static String getNextInputToken(boolean skip) {
        final String delimiters = " \t\n\r\f";
        String token = null;

        try {
            if (reader == null)
                reader = new StringTokenizer(in.readLine(), delimiters, true);

            while (token == null || ((delimiters.indexOf(token) >= 0) && skip)) {
                while (!reader.hasMoreTokens())
                    reader = new StringTokenizer(in.readLine(), delimiters, true);

                token = reader.nextToken();
            }
        } catch (Exception exception) {
            token = null;
        }

        return token;
    }

    /**
     * Restituisce true se non ci sono altri token da leggere sulla riga corrente.
     *
     * @return true se la riga corrente è esaurita
     */
    public static boolean endOfLine() {
        return !reader.hasMoreTokens();
    }

    // ************* Reading Section *********************************

    /**
     * Restituisce una stringa letta da tastiera.
     *
     * @return stringa letta, null in caso di errore
     */
    public static String readString() {
        String str;

        try {
            str = getNextToken(false);
            while (!endOfLine()) {
                str = str + getNextToken(false);
            }
        } catch (Exception exception) {
            error("Error reading String data, null value returned.");
            str = null;
        }
        return str;
    }

    /**
     * Restituisce una sottostringa delimitata da spazi (una parola) letta da tastiera.
     *
     * @return parola letta, null in caso di errore
     */
    public static String readWord() {
        String token;
        try {
            token = getNextToken();
        } catch (Exception exception) {
            error("Error reading String data, null value returned.");
            token = null;
        }
        return token;
    }

    /**
     * Restituisce un valore booleano letto da tastiera.
     *
     * @return valore booleano letto, false in caso di errore
     */
    public static boolean readBoolean() {
        String token = getNextToken();
        boolean bool;
        try {
            if (token.toLowerCase().equals("true"))
                bool = true;
            else if (token.toLowerCase().equals("false"))
                bool = false;
            else {
                error("Error reading boolean data, false value returned.");
                bool = false;
            }
        } catch (Exception exception) {
            error("Error reading boolean data, false value returned.");
            bool = false;
        }
        return bool;
    }

    /**
     * Restituisce un carattere letto da tastiera.
     *
     * @return carattere letto, Character.MIN_VALUE in caso di errore
     */
    public static char readChar() {
        String token = getNextToken(false);
        char value;
        try {
            if (token.length() > 1) {
                current_token = token.substring(1, token.length());
            } else
                current_token = null;
            value = token.charAt(0);
        } catch (Exception exception) {
            error("Error reading char data, MIN_VALUE value returned.");
            value = Character.MIN_VALUE;
        }

        return value;
    }

    /**
     * Restituisce un valore intero letto da tastiera.
     *
     * @return valore intero letto, Integer.MIN_VALUE in caso di errore
     */
    public static int readInt() {
        String token = getNextToken();
        int value;
        try {
            value = Integer.parseInt(token);
        } catch (Exception exception) {
            error("Error reading int data, MIN_VALUE value returned.");
            value = Integer.MIN_VALUE;
        }
        return value;
    }

    /**
     * Restituisce un valore long letto da tastiera.
     *
     * @return valore long letto, Long.MIN_VALUE in caso di errore
     */
    public static long readLong() {
        String token = getNextToken();
        long value;
        try {
            value = Long.parseLong(token);
        } catch (Exception exception) {
            error("Error reading long data, MIN_VALUE value returned.");
            value = Long.MIN_VALUE;
        }
        return value;
    }

    /**
     * Restituisce un valore float letto da tastiera.
     *
     * @return valore float letto, Float.NaN in caso di errore
     */
    public static float readFloat() {
        String token = getNextToken();
        float value;
        try {
            value = (new Float(token)).floatValue();
        } catch (Exception exception) {
            error("Error reading float data, NaN value returned.");
            value = Float.NaN;
        }
        return value;
    }

    /**
     * Restituisce un valore double letto da tastiera.
     *
     * @return valore double letto, Double.NaN in caso di errore
     */
    public static double readDouble() {
        String token = getNextToken();
        double value;
        try {
            value = (new Double(token)).doubleValue();
        } catch (Exception exception) {
            error("Error reading double data, NaN value returned.");
            value = Double.NaN;
        }
        return value;
    }
}