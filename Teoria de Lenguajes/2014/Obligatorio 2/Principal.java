import java_cup.runtime.*;
import java.lang.System;
import java.io.*;

public class Principal{

 public static void main(String arg[]) throws java.io.IOException
 {    // crea symbol para inicializar arbol de parsing





class Lexicografico implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 256;
	private final int YY_EOF = 257;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Lexicografico (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Lexicografico (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexicografico () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NOT_ACCEPT,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,258,
"39:8,38:3,39,38:2,39:18,38,39:6,33,31,32,25,22,30,23,36,24,35:10,39,29,26,2" +
"8,27,39:2,4,11,1,8,3,15,21,18,6,34:2,12,16,7,10,14,34,2,13,5,19,20,17,9,34:" +
"2,39:4,37,39,4,11,1,8,3,15,21,18,6,34:2,12,16,7,10,14,34,2,13,5,19,20,17,9," +
"34:2,39:133,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,122,
"0,1,2,1:4,3,4,1:5,5,6,1,7:4,1:4,7:21,5,8,1,9,10,11,12,13,14,15,16,17,18,19," +
"20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44," +
"45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,7,6" +
"9,70,71,72,73,74,75,7,76,77,78,79")[0];

	private int yy_nxt[][] = unpackFromString(80,40,
"1,2,117,118,47,106,49,72,92,117,50,119,117,73,117,93,117,107,117,120,121,11" +
"7,3,4,5,6,7,8,9,10,11,12,13,14,117,15,48:2,16,48,-1:41,117,108,117:19,-1:12" +
",117,109,-1,109,-1:29,21,22,-1:39,23,-1:12,46:21,-1:11,24,46:4,-1:37,15,-1:" +
"5,117:21,-1:12,117,109,-1,109,-1:3,117:6,51,117:5,17,117:8,-1:12,117,109,-1" +
",109,-1:3,117:6,74,117:5,18,117:8,-1:12,117,109,-1,109,-1:3,117,19,117:4,20" +
",117:14,-1:12,117,109,-1,109,-1:3,117:7,25,117:13,-1:12,117,109,-1,109,-1:3" +
",117:4,26,117:16,-1:12,117,109,-1,109,-1:3,117:4,27,117:6,100,117:9,-1:12,1" +
"17,109,-1,109,-1:3,117:2,103,117:6,28,117:11,-1:12,117,109,-1,109,-1:3,117:" +
"11,29,117:9,-1:12,117,109,-1,109,-1:3,117:13,30,117:7,-1:12,117,109,-1,109," +
"-1:3,117:2,31,117:18,-1:12,117,109,-1,109,-1:3,117:15,32,117:5,-1:12,117,10" +
"9,-1,109,-1:3,117:2,33,117:18,-1:12,117,109,-1,109,-1:3,117:8,34,117:12,-1:" +
"12,117,109,-1,109,-1:3,117:2,35,117:18,-1:12,117,109,-1,109,-1:3,117:2,36,1" +
"17:18,-1:12,117,109,-1,109,-1:3,117:12,37,117:8,-1:12,117,109,-1,109,-1:3,1" +
"17:4,38,117:16,-1:12,117,109,-1,109,-1:3,117:2,39,117:18,-1:12,117,109,-1,1" +
"09,-1:3,117:4,40,117:16,-1:12,117,109,-1,109,-1:3,117:2,41,117:18,-1:12,117" +
",109,-1,109,-1:3,117:12,42,117:8,-1:12,117,109,-1,109,-1:3,117,43,117:19,-1" +
":12,117,109,-1,109,-1:3,117:6,44,117:14,-1:12,117,109,-1,109,-1:3,117,45,11" +
"7:19,-1:12,117,109,-1,109,-1:3,117:9,52,117:8,75,117:2,-1:12,117,109,-1,109" +
",-1:3,117:2,53,117:18,-1:12,117,109,-1,109,-1:3,117:4,54,117:2,80,117:4,98," +
"117:8,-1:12,117,109,-1,109,-1:3,117:11,55,117:9,-1:12,117,109,-1,109,-1:3,1" +
"17:9,56,117:11,-1:12,117,109,-1,109,-1:3,117:4,57,117:16,-1:12,117,109,-1,1" +
"09,-1:3,117:9,58,117:11,-1:12,117,109,-1,109,-1:3,117:11,59,117:9,-1:12,117" +
",109,-1,109,-1:3,117:2,60,117:18,-1:12,117,109,-1,109,-1:3,117,61,117:19,-1" +
":12,117,109,-1,109,-1:3,117:4,62,117:16,-1:12,117,109,-1,109,-1:3,117:4,63," +
"117:16,-1:12,117,109,-1,109,-1:3,117,64,117:19,-1:12,117,109,-1,109,-1:3,11" +
"7:4,65,117:16,-1:12,117,109,-1,109,-1:3,66,117:20,-1:12,117,109,-1,109,-1:3" +
",117:4,67,117:16,-1:12,117,109,-1,109,-1:3,117:2,68,117:18,-1:12,117,109,-1" +
",109,-1:3,117:2,69,117:18,-1:12,117,109,-1,109,-1:3,117:2,70,117:18,-1:12,1" +
"17,109,-1,109,-1:3,117:3,71,117:17,-1:12,117,109,-1,109,-1:3,117,76,111,77," +
"117:17,-1:12,117,109,-1,109,-1:3,117,78,117:19,-1:12,117,109,-1,109,-1:3,11" +
"7:10,79,117:10,-1:12,117,109,-1,109,-1:3,117:2,81,117:18,-1:12,117,109,-1,1" +
"09,-1:3,117:3,82,117:17,-1:12,117,109,-1,109,-1:3,117:12,83,117:8,-1:12,117" +
",109,-1,109,-1:3,117:2,84,117:18,-1:12,117,109,-1,109,-1:3,117:2,85,117:18," +
"-1:12,117,109,-1,109,-1:3,117:2,86,117:18,-1:12,117,109,-1,109,-1:3,117:3,8" +
"7,117:17,-1:12,117,109,-1,109,-1:3,117:18,88,117:2,-1:12,117,109,-1,109,-1:" +
"3,117:20,89,-1:12,117,109,-1,109,-1:3,117:2,90,117:18,-1:12,117,109,-1,109," +
"-1:3,117:17,91,117:3,-1:12,117,109,-1,109,-1:3,117:3,94,117:17,-1:12,117,10" +
"9,-1,109,-1:3,117:17,95,117:3,-1:12,117,109,-1,109,-1:3,117:2,96,117:18,-1:" +
"12,117,109,-1,109,-1:3,117:5,97,117:15,-1:12,117,109,-1,109,-1:3,117:11,99," +
"117:9,-1:12,117,109,-1,109,-1:3,117:4,115,117:16,-1:12,117,109,-1,109,-1:3," +
"117:7,101,117:13,-1:12,117,109,-1,109,-1:3,117,116,117:9,102,117:9,-1:12,11" +
"7,109,-1,109,-1:3,117:16,104,117:4,-1:12,117,109,-1,109,-1:3,105,117:20,-1:" +
"12,117,109,-1,109,-1:3,117:8,110,117:12,-1:12,117,109,-1,109,-1:3,117:2,112" +
",117:18,-1:12,117,109,-1,109,-1:3,117:13,113,117:7,-1:12,117,109,-1,109,-1:" +
"3,117:3,114,117:17,-1:12,117,109,-1,109,-1:2");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

    return new Symbol(sym.EOF, null);
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{return (new Symbol(sym.ID));}
					case -3:
						break;
					case 3:
						{return (new Symbol(sym.MAS));}
					case -4:
						break;
					case 4:
						{return (new Symbol(sym.MENOS));}
					case -5:
						break;
					case 5:
						{return (new Symbol(sym.BARRA));}
					case -6:
						break;
					case 6:
						{return (new Symbol(sym.ASTERISCO));}
					case -7:
						break;
					case 7:
						{return (new Symbol(sym.MENOR));}
					case -8:
						break;
					case 8:
						{return (new Symbol(sym.MAYOR));}
					case -9:
						break;
					case 9:
						{return (new Symbol(sym.IGUAL));}
					case -10:
						break;
					case 10:
						{return (new Symbol(sym.PUNTOYCOMA));}
					case -11:
						break;
					case 11:
						{return (new Symbol(sym.COMA));}
					case -12:
						break;
					case 12:
						{return (new Symbol(sym.CURIZQ));}
					case -13:
						break;
					case 13:
						{return (new Symbol(sym.CURDER));}
					case -14:
						break;
					case 14:
						{/*System.out.println("linea:"+(yyline+1) +"  simbolo ilegal:  " +yytext());*/
                 		 System.out.println("simbolo ilegal: " +yytext());}
					case -15:
						break;
					case 15:
						{return (new Symbol(sym.NUMBER));}
					case -16:
						break;
					case 16:
						{/* ignoro */}
					case -17:
						break;
					case 17:
						{return (new Symbol(sym.AS));}
					case -18:
						break;
					case 18:
						{return (new Symbol(sym.IS));}
					case -19:
						break;
					case 19:
						{return (new Symbol(sym.OR));}
					case -20:
						break;
					case 20:
						{return (new Symbol(sym.ON));}
					case -21:
						break;
					case 21:
						{return (new Symbol(sym.DISTINTO));}
					case -22:
						break;
					case 22:
						{return (new Symbol(sym.MENORIGUAL));}
					case -23:
						break;
					case 23:
						{return (new Symbol(sym.MAYORIGUAL));}
					case -24:
						break;
					case 24:
						{return (new Symbol(sym.STRING));}
					case -25:
						break;
					case 25:
						{return (new Symbol(sym.AND));}
					case -26:
						break;
					case 26:
						{return (new Symbol(sym.NOT));}
					case -27:
						break;
					case 27:
						{return (new Symbol(sym.SET));}
					case -28:
						break;
					case 28:
						{return (new Symbol(sym.INTO));}
					case -29:
						break;
					case 29:
						{return (new Symbol(sym.NULL));}
					case -30:
						break;
					case 30:
						{return (new Symbol(sym.DROP));}
					case -31:
						break;
					case 31:
						{return (new Symbol(sym.DATE));}
					case -32:
						break;
					case 32:
						{return (new Symbol(sym.FROM));}
					case -33:
						break;
					case 33:
						{return (new Symbol(sym.TABLE));}
					case -34:
						break;
					case 34:
						{return (new Symbol(sym.INDEX));}
					case -35:
						break;
					case 35:
						{return (new Symbol(sym.WHERE));}
					case -36:
						break;
					case 36:
						{return (new Symbol(sym.CREATE));}
					case -37:
						break;
					case 37:
						{return (new Symbol(sym.EXISTS));}
					case -38:
						break;
					case 38:
						{return (new Symbol(sym.INSERT));}
					case -39:
						break;
					case 39:
						{return (new Symbol(sym.DELETE));}
					case -40:
						break;
					case 40:
						{return (new Symbol(sym.SELECT));}
					case -41:
						break;
					case 41:
						{return (new Symbol(sym.UPDATE));}
					case -42:
						break;
					case 42:
						{return (new Symbol(sym.VALUES));}
					case -43:
						break;
					case 43:
						{return (new Symbol(sym.INTEGER));}
					case -44:
						break;
					case 44:
						{return (new Symbol(sym.BETWEEN));}
					case -45:
						break;
					case 45:
						{return (new Symbol(sym.VARCHAR));}
					case -46:
						break;
					case 47:
						{return (new Symbol(sym.ID));}
					case -47:
						break;
					case 48:
						{/*System.out.println("linea:"+(yyline+1) +"  simbolo ilegal:  " +yytext());*/
                 		 System.out.println("simbolo ilegal: " +yytext());}
					case -48:
						break;
					case 49:
						{return (new Symbol(sym.ID));}
					case -49:
						break;
					case 50:
						{return (new Symbol(sym.ID));}
					case -50:
						break;
					case 51:
						{return (new Symbol(sym.ID));}
					case -51:
						break;
					case 52:
						{return (new Symbol(sym.ID));}
					case -52:
						break;
					case 53:
						{return (new Symbol(sym.ID));}
					case -53:
						break;
					case 54:
						{return (new Symbol(sym.ID));}
					case -54:
						break;
					case 55:
						{return (new Symbol(sym.ID));}
					case -55:
						break;
					case 56:
						{return (new Symbol(sym.ID));}
					case -56:
						break;
					case 57:
						{return (new Symbol(sym.ID));}
					case -57:
						break;
					case 58:
						{return (new Symbol(sym.ID));}
					case -58:
						break;
					case 59:
						{return (new Symbol(sym.ID));}
					case -59:
						break;
					case 60:
						{return (new Symbol(sym.ID));}
					case -60:
						break;
					case 61:
						{return (new Symbol(sym.ID));}
					case -61:
						break;
					case 62:
						{return (new Symbol(sym.ID));}
					case -62:
						break;
					case 63:
						{return (new Symbol(sym.ID));}
					case -63:
						break;
					case 64:
						{return (new Symbol(sym.ID));}
					case -64:
						break;
					case 65:
						{return (new Symbol(sym.ID));}
					case -65:
						break;
					case 66:
						{return (new Symbol(sym.ID));}
					case -66:
						break;
					case 67:
						{return (new Symbol(sym.ID));}
					case -67:
						break;
					case 68:
						{return (new Symbol(sym.ID));}
					case -68:
						break;
					case 69:
						{return (new Symbol(sym.ID));}
					case -69:
						break;
					case 70:
						{return (new Symbol(sym.ID));}
					case -70:
						break;
					case 71:
						{return (new Symbol(sym.ID));}
					case -71:
						break;
					case 72:
						{return (new Symbol(sym.ID));}
					case -72:
						break;
					case 73:
						{return (new Symbol(sym.ID));}
					case -73:
						break;
					case 74:
						{return (new Symbol(sym.ID));}
					case -74:
						break;
					case 75:
						{return (new Symbol(sym.ID));}
					case -75:
						break;
					case 76:
						{return (new Symbol(sym.ID));}
					case -76:
						break;
					case 77:
						{return (new Symbol(sym.ID));}
					case -77:
						break;
					case 78:
						{return (new Symbol(sym.ID));}
					case -78:
						break;
					case 79:
						{return (new Symbol(sym.ID));}
					case -79:
						break;
					case 80:
						{return (new Symbol(sym.ID));}
					case -80:
						break;
					case 81:
						{return (new Symbol(sym.ID));}
					case -81:
						break;
					case 82:
						{return (new Symbol(sym.ID));}
					case -82:
						break;
					case 83:
						{return (new Symbol(sym.ID));}
					case -83:
						break;
					case 84:
						{return (new Symbol(sym.ID));}
					case -84:
						break;
					case 85:
						{return (new Symbol(sym.ID));}
					case -85:
						break;
					case 86:
						{return (new Symbol(sym.ID));}
					case -86:
						break;
					case 87:
						{return (new Symbol(sym.ID));}
					case -87:
						break;
					case 88:
						{return (new Symbol(sym.ID));}
					case -88:
						break;
					case 89:
						{return (new Symbol(sym.ID));}
					case -89:
						break;
					case 90:
						{return (new Symbol(sym.ID));}
					case -90:
						break;
					case 91:
						{return (new Symbol(sym.ID));}
					case -91:
						break;
					case 92:
						{return (new Symbol(sym.ID));}
					case -92:
						break;
					case 93:
						{return (new Symbol(sym.ID));}
					case -93:
						break;
					case 94:
						{return (new Symbol(sym.ID));}
					case -94:
						break;
					case 95:
						{return (new Symbol(sym.ID));}
					case -95:
						break;
					case 96:
						{return (new Symbol(sym.ID));}
					case -96:
						break;
					case 97:
						{return (new Symbol(sym.ID));}
					case -97:
						break;
					case 98:
						{return (new Symbol(sym.ID));}
					case -98:
						break;
					case 99:
						{return (new Symbol(sym.ID));}
					case -99:
						break;
					case 100:
						{return (new Symbol(sym.ID));}
					case -100:
						break;
					case 101:
						{return (new Symbol(sym.ID));}
					case -101:
						break;
					case 102:
						{return (new Symbol(sym.ID));}
					case -102:
						break;
					case 103:
						{return (new Symbol(sym.ID));}
					case -103:
						break;
					case 104:
						{return (new Symbol(sym.ID));}
					case -104:
						break;
					case 105:
						{return (new Symbol(sym.ID));}
					case -105:
						break;
					case 106:
						{return (new Symbol(sym.ID));}
					case -106:
						break;
					case 107:
						{return (new Symbol(sym.ID));}
					case -107:
						break;
					case 108:
						{return (new Symbol(sym.ID));}
					case -108:
						break;
					case 109:
						{return (new Symbol(sym.ID));}
					case -109:
						break;
					case 110:
						{return (new Symbol(sym.ID));}
					case -110:
						break;
					case 111:
						{return (new Symbol(sym.ID));}
					case -111:
						break;
					case 112:
						{return (new Symbol(sym.ID));}
					case -112:
						break;
					case 113:
						{return (new Symbol(sym.ID));}
					case -113:
						break;
					case 114:
						{return (new Symbol(sym.ID));}
					case -114:
						break;
					case 115:
						{return (new Symbol(sym.ID));}
					case -115:
						break;
					case 116:
						{return (new Symbol(sym.ID));}
					case -116:
						break;
					case 117:
						{return (new Symbol(sym.ID));}
					case -117:
						break;
					case 118:
						{return (new Symbol(sym.ID));}
					case -118:
						break;
					case 119:
						{return (new Symbol(sym.ID));}
					case -119:
						break;
					case 120:
						{return (new Symbol(sym.ID));}
					case -120:
						break;
					case 121:
						{return (new Symbol(sym.ID));}
					case -121:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}




 

  try{

         // crea el objeto de lexicografico, leyendo de stdin
         Lexicografico l = new Lexicografico(System.in);

         // crea el objeto de parser, y le pasa el lexicografico
         parser parser_obj = new parser(l);

         // activa el parser
         Symbol p = parser_obj.parse();
    }
  catch (Exception e)
    {    // si hay error imprime este mensaje, no se manejan funcs de CUP.
System.out.println("Error Sintactico");

    }	
 }
};
