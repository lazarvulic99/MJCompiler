
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"   { return new_symbol(sym.PROG, yytext());}
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"const"   	{ return new_symbol(sym.CONST, yytext());}
"new"   	{ return new_symbol(sym.NEW, yytext());}
"read"   	{ return new_symbol(sym.READ, yytext());}
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"goto"   	{ return new_symbol(sym.GOTO, yytext());}

"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"++" 		{ return new_symbol(sym.PLUSPLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"--" 		{ return new_symbol(sym.MINUSMINUS, yytext()); }
"*" 		{ return new_symbol(sym.PUTA, yytext()); }
"/" 		{ return new_symbol(sym.PODELJENO, yytext()); }
"%" 		{ return new_symbol(sym.PROCENAT, yytext()); }
"=" 		{ return new_symbol(sym.JEDNAKO, yytext()); }
";" 		{ return new_symbol(sym.TACKAZAREZ, yytext()); }
":" 		{ return new_symbol(sym.DVETACKE, yytext()); }
"," 		{ return new_symbol(sym.ZAREZ, yytext()); }
"." 		{ return new_symbol(sym.TACKA, yytext()); }
"(" 		{ return new_symbol(sym.LEVAZAGRADA, yytext()); }
")" 		{ return new_symbol(sym.DESNAZAGRADA, yytext()); }
"[" 		{ return new_symbol(sym.LEVAUGLASTA, yytext()); }
"]"			{ return new_symbol(sym.DESNAUGLASTA, yytext()); }
"{" 		{ return new_symbol(sym.LEVAVITICASTA, yytext()); }
"}"			{ return new_symbol(sym.DESNAVITICASTA, yytext()); }
"??"		{ return new_symbol(sym.TERNARNI, yytext()); }

"//" 				{ yybegin(COMMENT);}
<COMMENT> . 		{ yybegin(COMMENT);}
<COMMENT> "\r\n" 	{ yybegin(YYINITIAL);}

[0-9]+							{ return new_symbol(sym.BROJ, new Integer (yytext())); }
"'"."'"  						{ return new_symbol(sym.CHAR, new Character (yytext().charAt(1))); }
("true"|"false")  				{ return new_symbol(sym.BOOLEAN, yytext().equals("false")? 0 : 1); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{ return new_symbol(sym.IDENTIFIKATOR, yytext()); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1) + ", u koloni " + (yycolumn + 1) + "\n"); }
