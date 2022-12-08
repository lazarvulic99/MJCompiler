package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.concepts.Scope;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static final Struct booltype = new Struct(Struct.Bool);
	
	public static void napraviBool() {
		//Struct booltype = new Struct(Struct.Bool);
		Obj objectBool = Tab.insert(Obj.Type, "bool", booltype);
		objectBool.setLevel(-1);
		objectBool.setAdr(-1);
		//Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", booltype)); 
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(Compiler.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			// Yylex klasa se formira u prvoj fazi projekta, tj. u leksickoj analizi
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			// formiranje AST
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();
	        
	        Program prog = (Program)(s.value);
	        
			// ispis AST
	        
			log.info(prog.toString(""));
			log.info("=====================================================================");
			
			// Pre pocetka 3.faze, a posle 2. faze, uradicu inicijalizaciju tabele simbola
			// Odmah nakon toga formiracu strukturni i objektni cvor za Bool jer u implementaciji klase Tab.class to ne postoji
			// i namestimo mu ona dva polja na -1 kao sto je i njima u tipovima
			// I tu ce se desiti jedan mali bug pri ispisu, da se ime strukturnog cvora za bool ne ispisuje :/
			// isti bug mi postoji i ako umesto "bool" posaljem new String("bool") kao argument insert metode Tab klase
			
			Tab.init();	
			napraviBool();

			// Ovde ce da mi bude semanticka analiza tj. 3. faza projekta
			// Obilazak sintaksnog stabla sacuvanog u pokazivanu prog, po principu bottom up od strane semantickog analizatora
			SemanticAnalyzer semA = new SemanticAnalyzer();
			prog.traverseBottomUp(semA);
			
			log.info("---------------------------------------------------------------------");
			
			// Ovde mi dodje ispis tabele simbola nakon kraja 3. faze projekta, tj. semanticke analize
			
			Tab.dump();
			
			log.info("=====================================================================");
			
			if(!p.errorDetected){
				if(semA.successfullyPassed()) {
					// 4. faza - Generisanje koda
					File objFile = new File("test/program.obj");
					if(objFile.exists()) objFile.delete();
					
					CodeGenerator codeGen = new CodeGenerator();
					prog.traverseBottomUp(codeGen);
					Code.dataSize = semA.brojGlobalnih;
					//Code.dataSize = semA.brGlobalnihPromenljivihBrojano;
					Code.mainPc = codeGen.dohvatiMainPC();
					Code.write(new FileOutputStream(objFile));
					log.info("Parsiranje i generisanje uspesno zavrseno!");
				}else {
					log.error("Parsiranje i generisanje NIJE uspesno zavrseno!");
				}
			}else{
				log.error("Parsiranje NIJE uspesno zavrseno!");
			}
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
