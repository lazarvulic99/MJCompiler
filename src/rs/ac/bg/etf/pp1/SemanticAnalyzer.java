package rs.ac.bg.etf.pp1;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class SemanticAnalyzer extends VisitorAdaptor {

	private boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());
	private Obj programNode;
	private Struct tekuciTipLinije;
	private Obj constObj;
	private int konstantaJeBrojCharBool;
	private int konstantaBroj;
	private int konstantaChar;
	private int konstantaBool;
	private Struct konstantaTip;
	private Obj variableObj;
	private boolean variablaJeNiz = false;
	private String imeMetode = " ";
	private boolean nasaoMain = false;
	//Inicijalizacija liste labela se radi kad udjemo u novu metodu, ne na ovom ovde mestu
	private ArrayList<String> sveLabele = null;
	private ArrayList<String> labeleStvarnihSkokova = null;
	int brojGlobalnih; // nije private, da bi moglo da se dohvati u klasi Compiler.java, tj. takozvano paketsko pravo pristupa
	int brGlobalnihPromenljivihBrojano = 0;
	int brLokalnih = 0;
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected  = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public void report_infoOLokalnim(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		log.info(msg.toString());
	}
	
	public boolean successfullyPassed() {
		return (errorDetected == true) ? false : true;
	}
	
	public boolean proveriJednakostNaNoObj(Obj objekat) {
		return (objekat == Tab.noObj)? true : false;
	}
	
	public boolean proveriPostojanjeDuplogSimbola(String imeCvora) {
		if(Tab.find(imeCvora) != Tab.noObj) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean proveriPostojanjeDuplogSimbolaLokalno(String imeCvora) {
		if(Tab.currentScope().findSymbol(imeCvora) == null || Tab.currentScope().findSymbol(imeCvora) == Tab.noObj) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean proveriPostojanjeSimbolaSviOpsezi(String imeCvora) {
		if(Tab.find(imeCvora) != Tab.noObj) {
			return true;
		}else {
			return false;
		}
	}
	
	public Obj vratiObjektniCvorSimbola(String imeCvora) {
		return Tab.find(imeCvora);
	}
	
	public boolean proveriAdekvatnostPromenljive(String imeCvora) {
		// Sluzi mi kod designatora kad je samo ident, za designator koji je nizovski treba dodati jos i da moze da bude niz
		Obj cvor = Tab.find(imeCvora);
		int vrstaSimbola = cvor.getKind();
		switch(vrstaSimbola) {
			case Obj.Var:
				return true;
			case Obj.Con:
				return true;
			default:
				return false;
		}
	}
	
	public int proveriImeNizaIspravnost(String imeZaNiz) {
		Obj cvorNiza = Tab.find(imeZaNiz);
		if(cvorNiza.getKind() == Obj.Var) {
			int strukturaCvora = cvorNiza.getType().getKind();
			if(strukturaCvora == Struct.Array) {
				return 1;
			}else {
				return -2;
			}
		}else {
			return -1;
		}
	}
	
	public boolean proveraPozicijeZaNiz(Expr expr) {
		if(expr.struct.equals(Tab.intType) == true) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean proveraIspravnostiTipaZaDesignator(Designator designator) {
		//report_info("Tip: " + designator.obj.getKind(), designator);
		if(designator.obj.getKind() != Obj.Elem && designator.obj.getKind() != Obj.Var) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean proveraIntTipa(Designator designator) {
		boolean retVal = true;
		if(designator.obj.getType().equals(Tab.intType) == false) {
			retVal = false;
		}
		return retVal;
	}
	
	public boolean proveraIspravnostiTipa(Designator designator, String iskaz) {
		boolean retVal = false;
		if(iskaz.compareToIgnoreCase("read") == 0) {
			Struct boolType = Tab.find("bool").getType();
			if(designator.obj.getType().equals(Tab.intType) == false && designator.obj.getType().equals(Tab.charType) == false && designator.obj.getType().equals(boolType) == false) {
				retVal = false;
			}else {
				retVal = true;
			}
		}
		return retVal;
	}
	
	public boolean proveraZaPrintTipovi(Expr expr) {
		boolean retVal = false;
		Struct struktura = expr.struct;
		Struct boolType = Tab.find("bool").getType();
		if(struktura.equals(Tab.intType) == false && struktura.equals(Tab.charType) == false && struktura.equals(boolType) == false) {
			retVal = false;
		}else {
			retVal = true;
		}
		return retVal;
	}
	
	//------Odavde mi krecu visit metode za semanticku analizu------
	@Override
	public void visit(ProgramIdent ProgramIdent) {
		// program nema tip, neka bude void, iz tog razloga je Tab.noType
		programNode = Tab.insert(Obj.Prog, ProgramIdent.getProgName(), Tab.noType);
		// otvorim novi opseg, to ce biti onaj globalni opseg nivoa 0
		Tab.openScope();
		// level i addr polje mogu da postavim i eksplicitno na -1, nisu bitni za nas program
	}
	
	@Override
	public void visit(Program Program) {
		// Prolazim kroz globalni scope i prebrojavam koliko ima globalnih Var kojima je level 0
		// da bih to upisao u dataSize u 4.fazi
		// Ovo je ujedno i najbolji trenutak, jer je to trenutak pred prelancavanje
		brojGlobalnih = Tab.currentScope().getnVars();
		//report_error("Ma ovo mi samo metoda da vidim kako sve funkcionise", Program);
		// Odradim prelancavanje, jer scope cvor za global se brise
		Tab.chainLocalSymbols(programNode);
		// Zatvaram prethodno otvoreni scope, tj. opseg. Ovo je jedino mesto gde se taj opseg i moze zatvoriti.
		Tab.closeScope();
		programNode = null;
		if(nasaoMain == false) {
			report_error("Greska(Program)! Metoda se mora zvati main!", Program);
		}
		report_infoOLokalnim("Lokalnih promenljivih: " + brLokalnih, Program);
		if(brLokalnih > 256) {
			report_error("Greska(Program)! Maksimalan broj lokalnih promenljivih koji se moze koristiti je 256!", Program);
		}
		if(brojGlobalnih > 65536) {
			report_error("Greska(Program)! Maksimalan broj globalnih promenljivih koji se moze koristiti je 65536!", Program);
		}
	}
	
	@Override
	public void visit(Type Type) {
		//tipULiniji = Type.getTip();
		// Za nivo A, tipovi su samo ugradjeni tipovi, int, char, bool
		// Ukoliko metoda find ne vrati nista, tj. vrati noObj, znamo da je doslo do greske, jer takav tip ne postoji u tabeli simbola
		Obj tipIdentifikator = Tab.find(Type.getTip());
		int greskaUTipu = 0;
		if(proveriJednakostNaNoObj(tipIdentifikator)) {
			report_error("Greska(Type)! Tip: " + Type.getTip() + " nije iz skupa ugradjenih tipova: int, char, bool!", Type);
			greskaUTipu = 1;
		}
		if(greskaUTipu == 0 && tipIdentifikator.getKind() != Obj.Type) {
			greskaUTipu = 2;
		}
		if(greskaUTipu == 1) {
			tekuciTipLinije = Tab.noType;
			Type.struct = tekuciTipLinije;
		}else if(greskaUTipu == 2) {
			report_error("Greska(Type)! Tip: " + Type.getTip() + " nije dobar tip podatka, jer objektni cvor sa tim imenom vec postoji!", Type);
			tekuciTipLinije = Tab.noType;
			Type.struct = tekuciTipLinije;
		}else {
			tekuciTipLinije = tipIdentifikator.getType();
			Type.struct = tekuciTipLinije;
		}
	}
	
	@Override
	public void visit(Konstanta_broj Konstanta_broj) {
		konstantaJeBrojCharBool = 1;
		konstantaBroj = Konstanta_broj.getB1();
		konstantaTip = Tab.intType;
	}
	
	@Override
	public void visit(Konstanta_char Konstanta_char) {
		konstantaJeBrojCharBool = 2;
		konstantaChar = Konstanta_char.getC1();
		konstantaTip = Tab.charType;
	}
	
	@Override
    public void visit(Konstanta_boolean Konstanta_boolean) {
    	konstantaJeBrojCharBool = 3;
    	konstantaBool = Konstanta_boolean.getB1();
    	Struct boolType = Tab.find("bool").getType();
    	konstantaTip = boolType;
    }
	
	@Override
	public void visit(ConstantDecl ConstantDecl) {
		if(proveriPostojanjeDuplogSimbola(ConstantDecl.getI1())) {
			report_error("Greska(ConstantDecl)! Dupla definicija simbola: " + ConstantDecl.getI1() + " !", ConstantDecl);
		}else {
			if(konstantaTip.assignableTo(tekuciTipLinije) == false) {
				report_error("Greska(ConstantDecl)! Tip konstante i tip dodeljene vrednosti nisu assignable!", ConstantDecl);
			}else {
				constObj = Tab.insert(Obj.Con, ConstantDecl.getI1(), tekuciTipLinije);
				//postaviAdrPolje(constObj, ConstantDecl.get);
				if(konstantaJeBrojCharBool == 1) {
					// Broj
					constObj.setAdr(konstantaBroj);
				}else if(konstantaJeBrojCharBool == 2) {
					// Char
					constObj.setAdr(konstantaChar);
				}else if(konstantaJeBrojCharBool == 3) {
					// Bool
					constObj.setAdr(konstantaBool);
				}
			}
		}
	}
	
	@Override
	public void visit(VariableOpcioneZagrade_bezZagrada VariableOpcioneZagrade_bezZagrada) {
		variablaJeNiz = false;
	}
	
	@Override
	public void visit(VariableOpcioneZagrade_uglasteZagrade VariableOpcioneZagrade_uglasteZagrade) {
		variablaJeNiz = true;
	}
	
	@Override
	public void visit(VariableDecl VariableDecl) {
		if(imeMetode.compareToIgnoreCase(" ") == 0) {
			if(proveriPostojanjeDuplogSimbola(VariableDecl.getI1())) {
				report_error("Greska(VariableDecl)! Dupla definicija simbola za promenljivu: " + VariableDecl.getI1() + " !", VariableDecl);
			}else {
				if(variablaJeNiz == false) {
					variableObj = Tab.insert(Obj.Var, VariableDecl.getI1(), tekuciTipLinije);
					//Ako ovo odlucim da zakomentarisem, onda u compiler odkomentarisati liniju 94
					if(variableObj.getLevel() == 0) {
						brGlobalnihPromenljivihBrojano++;
					}else if(variableObj.getLevel() == 1) {
						brLokalnih++;
					}
					// do ovde
				}else {
					variableObj = Tab.insert(Obj.Var, VariableDecl.getI1(), new Struct(Struct.Array, tekuciTipLinije));
					//Ako ovo odlucim da zakomentarisem, onda u compiler odkomentarisati liniju 94
					if(variableObj.getLevel() == 0) {
						brGlobalnihPromenljivihBrojano++;
					}else if(variableObj.getLevel() == 1) {
						brLokalnih++;
					}
					// do ovde
				}
			}
		}else {
			if(proveriPostojanjeDuplogSimbolaLokalno(VariableDecl.getI1()) == true) {
				report_error("Greska(VariableDecl)!!! Dupla definicija simbola za promenljivu: " + VariableDecl.getI1() + " !", VariableDecl);
			}else {
				if(variablaJeNiz == false) {
					variableObj = Tab.insert(Obj.Var, VariableDecl.getI1(), tekuciTipLinije);
					//Ako ovo odlucim da zakomentarisem, onda u compiler odkomentarisati liniju 94
					if(variableObj.getLevel() == 0) {
						brGlobalnihPromenljivihBrojano++;
					}else if(variableObj.getLevel() == 1) {
						brLokalnih++;
					}
					// do ovde
				}else {
					variableObj = Tab.insert(Obj.Var, VariableDecl.getI1(), new Struct(Struct.Array, tekuciTipLinije));
					//Ako ovo odlucim da zakomentarisem, onda u compiler odkomentarisati liniju 94
					if(variableObj.getLevel() == 0) {
						brGlobalnihPromenljivihBrojano++;
					}else if(variableObj.getLevel() == 1) {
						brLokalnih++;
					}
					// do ovde
				}
			}
		}
	}
	
	@Override
	public void visit(MethodName MethodName) {
		imeMetode = MethodName.getImeMetode();
		Obj objCvor = Tab.insert(Obj.Meth, imeMetode, Tab.noType);
		MethodName.obj = objCvor;
		// Otvaram novi cvor opsega za lokalne stvari same metode
		// To moram i da zatvorim na samom kraju metode
		Tab.openScope();
		sveLabele = new ArrayList<>();
		labeleStvarnihSkokova = new ArrayList<>();
	}
	
	@Override
	public void visit(MethodDeclList MethodDeclList) {
		Obj methodInProgram = Tab.find(imeMetode);
		// Ovo sam mozda trebao da uradim na isti nacin kao sto sam radio i za program
		if(methodInProgram.getKind() == 3 && methodInProgram.getType().compatibleWith(Tab.noType)) {
			//report_info("Evo me ovde", MethodDeclList);
			Tab.chainLocalSymbols(methodInProgram);
			Tab.closeScope();
			if(imeMetode.compareToIgnoreCase("main") == 0) {
				nasaoMain  = true;
			}
			if(sveLabele.containsAll(labeleStvarnihSkokova) == false) {
				report_error("GRESKA! Postoji skok na labelu koji nije u redu u metodi: " + imeMetode, MethodDeclList);
			}
			imeMetode = " ";
			sveLabele = null;
			labeleStvarnihSkokova = null;
		}
	}
	
	// Kontekstni uslovi A.4
	
	@Override
	public void visit(FactorConst_char FactorConst_char) {
		FactorConst_char.struct = Tab.charType;
	}
	
	@Override
	public void visit(FactorConst_boolean FactorConst_boolean) {
		Struct boolStruktura = Tab.find("bool").getType();
		FactorConst_boolean.struct = boolStruktura;
	}
	
	@Override
	public void visit(FactorConst_broj FactorConst_broj) {
		FactorConst_broj.struct = Tab.intType;
	}
	
	@Override
	public void visit(FactorZamena_const FactorZamena_const) {
		Struct strukturniCvorSina = FactorZamena_const.getFactorConst().struct;
		FactorZamena_const.struct = strukturniCvorSina;
	}
	
	@Override
	public void visit(Factor Factor) {
		if(Factor.getFactorMoguciMinus() instanceof FactorMoguciMinus_minus) {
			if(Factor.getFactorZamena().struct.equals(Tab.intType) == false) {
				report_error("Greska(Factor)!Minus moze da stoji samo uz int promenljive!", Factor);
				Factor.struct = Tab.noType;
			}else {
				Factor.struct = Tab.intType;
			}
		}else {
			Factor.struct = Factor.getFactorZamena().struct;
		}
	}
	
	@Override
	public void visit(FactorZamena_designator FactorZamena_designator) {
		Obj objectNode = FactorZamena_designator.getDesignator().obj;
		Struct structNode = objectNode.getType();
		FactorZamena_designator.struct = structNode;
	}
	
	@Override
	public void visit(Designator_onlyIdent Designator_onlyIdent) {
		String imeIdenta = Designator_onlyIdent.getImeDesignatora();
		if(proveriPostojanjeSimbolaSviOpsezi(imeIdenta) == true) {
			if(proveriAdekvatnostPromenljive(imeIdenta) == true) {
				Designator_onlyIdent.obj = vratiObjektniCvorSimbola(imeIdenta);
				int kind = Designator_onlyIdent.obj.getKind();
				int adres = Designator_onlyIdent.obj.getAdr();
				int level = Designator_onlyIdent.obj.getLevel();
				int fpos = Designator_onlyIdent.obj.getFpPos();
				String ispis = " [Kind: " + kind + ", adr: " + adres + ", level: " + level + ", fpos: " + fpos + "] ";
				if(Designator_onlyIdent.obj.getLevel() > 0) {
					report_info("Pristupa se lokalnoj promenljivi: " + imeIdenta + ispis, Designator_onlyIdent);
				}else {
					report_info("Pristupa se globalnoj promenljivi: " + imeIdenta + ispis, Designator_onlyIdent);
				}
			}else {
				Designator_onlyIdent.obj = Tab.noObj;
				report_error("Greska(Designator_onlyIdent)!Simbol: " + imeIdenta + " se ne moze koristiti, nije adekvatan!", Designator_onlyIdent);
			}
		}else {
			// Mora da se postavi obj. cvor na noObj cvor za greske ili void da mi ne puca null pointer exception 
			Designator_onlyIdent.obj = Tab.noObj;
			report_error("Greska(Designator_onlyIdent)!Simbol: " + imeIdenta + " ne postoji u tabeli simbola!", Designator_onlyIdent);
		}
	}
	
	@Override
	public void visit(VariableNameZaNiz VariableNameZaNiz) {
		String imeZaNiz = VariableNameZaNiz.getImeNiza();
		if(proveriPostojanjeSimbolaSviOpsezi(imeZaNiz) == true) {
			if(proveriImeNizaIspravnost(imeZaNiz) == 1) {
				VariableNameZaNiz.obj = vratiObjektniCvorSimbola(imeZaNiz);
			}else {
				// Mora da se postavi obj. cvor na noObj cvor za greske ili void da mi ne puca null pointer exception 
				VariableNameZaNiz.obj = Tab.noObj;
				report_error("Greska(VariableNameZaNiz)!Ime niza: " + imeZaNiz + " se ne moze koristiti, nije adekvatno!", VariableNameZaNiz);
			}
		}else {
			// Mora da se postavi obj. cvor na noObj cvor za greske ili void da mi ne puca null pointer exception 
			VariableNameZaNiz.obj = Tab.noObj;
			report_error("Greska(VariableNameZaNiz)!Ime niza: " + imeZaNiz + " ne postoji u tabeli simbola!", VariableNameZaNiz);
		}
	}
	
	@Override
	public void visit(FactorZamena_expr FactorZamena_expr) {
		// (1 + 2) oba u zagradi su int, pa je i zagrada int
		// (2 - 1) oba u zagradi su int, pa je i zagrada int
		// Dohvatim jednostavno tip Expr koji je sin nonterminala FactorZamena_expr, i njegovo struct polje prepisem u struct polje
		// ovog nonterminala FactorZamena_expr
		Struct exprStruct = FactorZamena_expr.getExpr().struct;
		FactorZamena_expr.struct = exprStruct;
	}
	
	@Override
	public void visit(ArrayDecl ArrayDecl) {
		if(proveraPozicijeZaNiz(ArrayDecl.getExpr()) == true) {
			Struct strukturaNiza = new Struct(Struct.Array, tekuciTipLinije);
			ArrayDecl.struct = strukturaNiza;
		}else {
			report_error("GRESKA(ArrayDecl)! Pri kreiranju niza niste koristili int za velicinu niza", ArrayDecl);
			Struct greskaStruktura = Tab.noType;
			ArrayDecl.struct = greskaStruktura;
		}
	}
	
	@Override
	public void visit(FactorZamena_new FactorZamena_new) {
		FactorZamena_new.struct = FactorZamena_new.getArrayDecl().struct;
	}
	
	@Override
	public void visit(Term Term) {
		Struct struktura = Term.getTermList().struct;
		Term.struct = struktura;
	}
	
	@Override
	public void visit(Term_SingleFactor Term_SingleFactor) {
		Struct singleTermStruct = Term_SingleFactor.getFactor().struct;
		Term_SingleFactor.struct = singleTermStruct;
	}
	
	@Override
	public void visit(Term_ListOfFactors Term_ListOfFactors) {
		Struct operand1 = Term_ListOfFactors.getTermList().struct;
		Struct operand2 = Term_ListOfFactors.getFactor().struct;
		if(operand2.getKind() != Struct.Int || operand1.getKind() != Struct.Int) {
			report_error("GRESKA(Term_ListOfFactors)! *, /, % mora biti izmedju int vrednosti", Term_ListOfFactors);
			Term_ListOfFactors.struct = Tab.noType;
		}else {
			Term_ListOfFactors.struct = Tab.intType;
		}
	}
	
	@Override
	public void visit(Expr Expr) {
		Struct struktura = Expr.getExprList().struct;
		Expr.struct = struktura;
	}
	
	@Override
	public void visit(Expr_avgust Expr_avgust) {
		Struct operand2 = Expr_avgust.getExprBasic().struct;
		Struct operand1 = Expr_avgust.getExprList().struct;
		if(operand2.getKind() != Struct.Int || operand1.getKind() != Struct.Int) {
			report_error("GRESKA(Expr_avgust)! ?? mora biti izmedju int vrednosti", Expr_avgust);
			Expr_avgust.struct = Tab.noType;
		}else {
			Expr_avgust.struct = Tab.intType;
		}
	}
	
	@Override
	public void visit(Expr_januar Expr_januar) {
		Struct struktura = Expr_januar.getExprBasic().struct;
		Expr_januar.struct = struktura;
	}
	
	@Override
	public void visit(Expr_SingleTerm Expr_SingleTerm) {
		Struct singleExprStruct = Expr_SingleTerm.getTerm().struct;
		Expr_SingleTerm.struct = singleExprStruct;
	}
	
	@Override
	public void visit(Expr_ListOfTerms Expr_ListOfTerms) {
		Struct operand1 = Expr_ListOfTerms.getExprBasic().struct;
		Struct operand2 = Expr_ListOfTerms.getTerm().struct;
		if(operand1.getKind() != Struct.Int || operand2.getKind() != Struct.Int) {
			report_error("GRESKA(Expr_ListOfTerms)! +,- mora biti izmedju int vrednosti", Expr_ListOfTerms);
			Expr_ListOfTerms.struct = Tab.noType;
		}else {
			Expr_ListOfTerms.struct = Tab.intType;
		}
	}
	
	@Override
	public void visit(Designator_element Designator_element) {
		// Moj levi sin je proverio da li niz postoji, da li se ime niza moze koristiti takvo kakvo jeste
		// Na meni je da proverim da li je sin(VariableNameZaNiz) postavio objektni cvor na Tab.noObj, tj. da li je nasao neku gresku
		VariableNameZaNiz sin = Designator_element.getVariableNameZaNiz();
		Obj sinObj = sin.obj;
		if(sinObj != Tab.noObj) {
			// Sve je OK, nije bila greska pri obilasku sinovljevog cvora pre mog cvora
			// Proveri da li je index niza, tj. pozicija odabranog elementa niza int
			if(proveraPozicijeZaNiz(Designator_element.getExpr()) == true) {
				String elementName = sinObj.getName() + "[???]";
				Struct elemType = sinObj.getType().getElemType();
				Obj noviObjektniCvor = new Obj(Obj.Elem, elementName, elemType);
				Designator_element.obj = noviObjektniCvor;
				int kind = noviObjektniCvor.getKind();
				int adres = noviObjektniCvor.getAdr();
				int level = noviObjektniCvor.getLevel();
				int fpos = noviObjektniCvor.getFpPos();
				String ispis = " [Kind: " + kind + ", adr: " + adres + ", level: " + level + ", fpos: " + fpos + "] ";
				report_info("Pristupilo se elementu niza: " + sinObj.getName() + ispis, Designator_element);
			}else {
				report_error("GRESKA(Designator_element)! Indeks elementa niza NIJE celobrojnog tipa", Designator_element);
				Designator_element.obj = Tab.noObj;
			}
		}else {
			// Sin je vec odradio report greske, ja ne moram ponavljati to!
			Designator_element.obj = Tab.noObj;
		}
	}
	
	@Override
	public void visit(DesignatorStatement_dodela DesignatorStatement_dodela) {
		if(DesignatorStatement_dodela.getDesignator().obj.getKind() != Obj.Elem && DesignatorStatement_dodela.getDesignator().obj.getKind() != Obj.Var) {
			report_error("GRESKA(DesignatorStatement_dodela)!Promenljiva: " + DesignatorStatement_dodela.getDesignator().obj.getName() + ", mora da bude promenljiva ili element niza.", DesignatorStatement_dodela);
		}else {
			Struct desnaStrana = DesignatorStatement_dodela.getExpr().struct;
			Struct levaStrana = DesignatorStatement_dodela.getDesignator().obj.getType();
			if(desnaStrana.assignableTo(levaStrana) == false) {
				report_info("Leva strana tip: " + levaStrana.getKind(), DesignatorStatement_dodela);
				report_info("Desna strana tip: " + desnaStrana.getKind(), DesignatorStatement_dodela);
				report_error("GRESKA(DesignatorStatement_dodela)!Desna strana izraza nije assignable za promenljivu: " + DesignatorStatement_dodela.getDesignator().obj.getName(), DesignatorStatement_dodela);
			}
		}
	}
	
	@Override
	public void visit(DesignatorStatement_plusPlus DesignatorStatement_plusPlus) {
		if(proveraIspravnostiTipaZaDesignator(DesignatorStatement_plusPlus.getDesignator()) == true) {
			if(proveraIntTipa(DesignatorStatement_plusPlus.getDesignator()) == true) {
				// Ne radi nista
				// samo provere za gresku, nista ako je ispravno
			}else {
				String imePromenljive = DesignatorStatement_plusPlus.getDesignator().obj.getName();
				report_error("GRESKA(DesignatorStatement_plusPlus)!Moze samo INT da se inkrementira, ovde to nije slucaj: " + imePromenljive, DesignatorStatement_plusPlus);
			}
		}else {
			String imePromenljive = DesignatorStatement_plusPlus.getDesignator().obj.getName();
			report_error("GRESKA(DesignatorStatement_plusPlus)!Inkrementiranje se moze raditi samo nad elementom niza ili promenljivom: " + imePromenljive, DesignatorStatement_plusPlus);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_minusMinus DesignatorStatement_minusMinus) {
		if(proveraIspravnostiTipaZaDesignator(DesignatorStatement_minusMinus.getDesignator()) == true) {
			if(proveraIntTipa(DesignatorStatement_minusMinus.getDesignator()) == true) {
				// Ne radi nista
				// samo provere za gresku, nista ako je ispravno
			}else {
				String imePromenljive = DesignatorStatement_minusMinus.getDesignator().obj.getName();
				report_error("GRESKA(DesignatorStatement_minusMinus)!Moze samo INT da se dekrementira, ovde to nije slucaj: " + imePromenljive, DesignatorStatement_minusMinus);
			}
		}else {
			String imePromenljive = DesignatorStatement_minusMinus.getDesignator().obj.getName();
			report_error("GRESKA(DesignatorStatement_minusMinus)!Dekrementiranje se moze raditi samo nad elementom niza ili promenljivom: " + imePromenljive, DesignatorStatement_minusMinus);
		}
	}
	
	@Override
	public void visit(SingleStatement_readStatement SingleStatement_readStatement) {
		if(proveraIspravnostiTipaZaDesignator(SingleStatement_readStatement.getDesignator()) == true) {
			if(proveraIspravnostiTipa(SingleStatement_readStatement.getDesignator(), "read") == false) {
				String imePromenljive = SingleStatement_readStatement.getDesignator().obj.getName();
				report_error("GRESKA(SingleStatement_readStatement)!READ se moze raditi samo nad int-char-boolean: " + imePromenljive, SingleStatement_readStatement);
			}
		}else {
			String imePromenljive = SingleStatement_readStatement.getDesignator().obj.getName();
			/*if(SingleStatement_readStatement.getDesignator().obj.getKind() != Obj.Con) {
				report_error("GRESKA(SingleStatement_readStatement)!READ se moze raditi samo nad elementom niza ili promenljivom: " + imePromenljive, SingleStatement_readStatement);
			}else {
				if(proveraIspravnostiTipa(SingleStatement_readStatement.getDesignator(), "read") == false) {
					report_error("GRESKA(SingleStatement_readStatement)!READ se moze raditi samo nad int-char-boolean: " + imePromenljive, SingleStatement_readStatement);
				}
			}*/
			report_error("GRESKA(SingleStatement_readStatement)!READ se moze raditi samo nad elementom niza ili promenljivom: " + imePromenljive, SingleStatement_readStatement);
		}
	}
	
	@Override
	public void visit(SingleStatement_printStatementWithoutZarez SingleStatement_printStatementWithoutZarez) {
		// Ovo prepravi da postoji metoda zajednicka za ovaj visit, i visit ispod koji se obradjuje
		if(proveraZaPrintTipovi(SingleStatement_printStatementWithoutZarez.getExpr()) == false) {
			report_error("GRESKA(SingleStatement_printStatementWithoutZarez)!Print operacija moguca samo nad int/char/bool izrazom, ovde to nije slucaj", SingleStatement_printStatementWithoutZarez);
		}
	}
	
	@Override
	public void visit(SingleStatement_printStatementWithZarez SingleStatement_printStatementWithZarez) {
		// Ovo prepravi da postoji metoda zajednicka za ovaj visit, i visit ispod koji se obradjuje
		if(proveraZaPrintTipovi(SingleStatement_printStatementWithZarez.getExpr()) == false) {
			report_error("GRESKA(SingleStatement_printStatementWithZarez)!Print operacija moguca samo nad int/char/bool izrazom, ovde to nije slucaj", SingleStatement_printStatementWithZarez);
		}
	}
	
	@Override
	public void visit(Label Label) {
		String imeLabele = Label.getImeLabele();
		if(imeLabele.equalsIgnoreCase(" ") == false) {
			if(sveLabele.contains(imeLabele) == false) {
				sveLabele.add(imeLabele);
			}else {
				report_error("GRESKA(Label)!Vec definisana labela sa takvim imenom: " + imeLabele, Label);
			}
		}else {
			report_error("GRESKA(Label)!Ime labele ne sme biti prazno", Label);
		}
	}
	
	@Override
	public void visit(SingleStatement_gotoStatement SingleStatement_gotoStatement) {
		String imeOdredista = SingleStatement_gotoStatement.getLabelIzaGoTo().getMestoSkoka();
		// Ovde ne moram da proveravam da li vec postoji u ovoj listi, jer se na istu labelu moze skociti vise puta :)
		labeleStvarnihSkokova.add(imeOdredista);
	}
}
