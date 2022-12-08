package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;;

public class CodeGenerator extends VisitorAdaptor {

	private boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());
	private int main_PC;
	private List<String> imenaLabele = new ArrayList<>();
	private List<Integer> adreseLabela = new ArrayList<>();
	private Map<String, List<Integer>> toRepairAdresses = new HashMap<>();
	
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
	
	public int dohvatiMainPC() {
		return this.main_PC;
	}
	
	public boolean proveriJednakostTipova(Expr expr, Struct tipStruct) {
		if(expr.struct.equals(tipStruct) == false) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean proveraNaElem(Obj objNode) {
		if(objNode.getKind() != Obj.Elem) {
			return false;
		}else {
			return true;
		}
	}
	
	public boolean proveraNaChar(Obj objNode) {
		if(objNode.getType().equals(Tab.charType) == false) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public void visit(MethodName MethodName) {
		int adresaVoid = Code.pc;
		MethodName.obj.setAdr(adresaVoid);
		if(MethodName.getImeMetode().equalsIgnoreCase("main") == true) {
			this.main_PC = Code.pc;
		}
		int opCode = Code.enter;
		Code.put(opCode);
		int b1 = MethodName.obj.getLevel();
		Collection<Obj> b2Collection = MethodName.obj.getLocalSymbols();
		int b2 = b2Collection.size();
		// b1 ce mi za A fazu uvek biti 0, jer void main() metoda za A nivo ne moze da ima formalne parametre
		Code.put(b1);
		Code.put(b2);
	}
	
	// Svaka metoda se zavrsava sa exit i return
	// U zadnjem cvoru koji se obilazi, MethodDeclList, treba dodati exit i return u Code
	// Za A nivo, MethodDeclList, ce se uvek sastojati iz samo 1 metode
	// Exit brise aktivacioni zapis, return vraca povratnu adresu
	@Override
	public void visit(MethodDeclList MethodDeclList) {
		int opCodeExit = Code.exit;
		int opCodeReturn = Code.return_;
		Code.put(opCodeExit);
		Code.put(opCodeReturn);
	}
	
	@Override
	public void visit(SingleStatement_printStatementWithoutZarez SingleStatement_printStatementWithoutZarez) {
		int opCodePrint = Code.print;
		int opCode_bPrint = Code.bprint;
		Integer width = 0;
		Code.loadConst(width);
		Expr expr = SingleStatement_printStatementWithoutZarez.getExpr();
		if(proveriJednakostTipova(expr, Tab.charType) == false) {
			Code.put(opCodePrint);
		}else {
			Code.put(opCode_bPrint);
		}
	}
	
	@Override
	public void visit(SingleStatement_printStatementWithZarez SingleStatement_printStatementWithZarez) {
		int opCodePrint = Code.print;
		int opCode_bPrint = Code.bprint;
		Integer width = SingleStatement_printStatementWithZarez.getB2();
		Code.loadConst(width);
		Expr expr = SingleStatement_printStatementWithZarez.getExpr();
		if(proveriJednakostTipova(expr, Tab.charType) == false) {
			Code.put(opCodePrint);
		}else {
			Code.put(opCode_bPrint);
		}
	}
	
	@Override
	public void visit(FactorConst_broj FactorConst_broj) {
		Integer broj = FactorConst_broj.getB1();
		Code.loadConst(broj);
		// Ovo je print oblika: print(1), tj. print koji nema zarez, pa se na expr stack za argument width salje 0
		/*Integer width = 0;
		Code.loadConst(width);*/
		// Ovo mi je bila greska, sada mi je jasno i sto:
		// Jeste ovo pre printa smestano na expr stack, to je ok, ali ne treba pri obilasku FactorConst_broj cvora da se gura i width
		// svaki put, nego samo u obilasku print cvorova!
	}
	
	@Override
	public void visit(FactorConst_char FactorConst_char) {
		Character myChar = FactorConst_char.getC1();
		Code.loadConst(myChar);
	}
	
	@Override
	public void visit(FactorConst_boolean FactorConst_boolean) {
		Integer value = FactorConst_boolean.getB1();
		Code.loadConst(value);
	}
	
	@Override
	public void visit(Expr_ListOfTerms Expr_ListOfTerms) {
		if(Expr_ListOfTerms.getAddOperator() instanceof AddOperator_minus) {
			int opMinus = Code.sub;
			Code.put(opMinus);
		}else {
			int opPlus = Code.add;
			Code.put(opPlus);
		}
	}
	
	@Override
	public void visit(Expr_avgust Expr_avgust) {
		int opCodeDup2 = Code.dup2;
		int opCodePop = Code.pop;
		Code.put(opCodeDup2);
		Code.put(opCodePop);
		int nulaZaPoredjenje = 0;
		Code.loadConst(nulaZaPoredjenje);
		int opCodeEq = Code.eq;
		// falseJump ce da radi sa ne, jer radi sa obrnutim uslovom od onoga sto mu se prosledi
		// za uslovne skokove, adresa se postavlja na 0, kasnije cemo je saznati
		//int prepravka = Code.pc + 1;
		Code.putFalseJump(opCodeEq, 0);
		// JMPopCode 0 0 PC, PC - 2 ce da pokazuje na prvu 0, pocev odakle mi treba prepravka sa fixup
		int prepravka = Code.pc - 2;
		int opCodeDupX1 = Code.dup_x1;
		Code.put(opCodeDupX1);
		Code.put(opCodePop);
		Code.fixup(prepravka);
		Code.put(opCodePop);
		// Kada 2 != 0, izvrsi se samo pop ispod fixupa, a kad je prvi operand jednak nuli ide se u then granu, koja je redom dupX1, pop
		// i ponovo pop na dnu ispod fixupa, on se svakako izvrsava
	}
	
	@Override
	public void visit(Term_ListOfFactors Term_ListOfFactors) {
		if(Term_ListOfFactors.getMulOperator() instanceof MulOperator_podeljeno) {
			int opCodePodeljeno = Code.div;
			Code.put(opCodePodeljeno);
		}else if(Term_ListOfFactors.getMulOperator() instanceof MulOperator_puta) {
			int opCodePuta = Code.mul;
			Code.put(opCodePuta);
		}else {
			int opCodeProcenat = Code.rem;
			Code.put(opCodeProcenat);
		}
	}
	
	@Override
	public void visit(FactorZamena_designator FactorZamena_designator) {
		Designator dsg = FactorZamena_designator.getDesignator();
		Obj objNode = dsg.obj;
		// Ova metoda je napredna, zna da radi sa object Node-ovima
		Code.load(objNode);
	}
	
	@Override
	public void visit(VariableNameZaNiz VariableNameZaNiz) {
		// U semantickoj analizi smo cuvali objektni cvor za designator koji predstavlja niz
		// A cuvali smo strukturni cvor za designator koji ne predstavlja niz, nego obican identifikator
		// Svakako mi treba adresa niza koji se javio kao designator, bilo sa leve, bilo sa desne strane znaka =
		Obj objNode = VariableNameZaNiz.obj;
		Code.load(objNode);
	}
	
	@Override
	public void visit(DesignatorStatement_dodela DesignatorStatement_dodela) {
		// Vrednost sa ExprStack-a treba da se smesti u DesignatorStatement_dodela
		// Expr ce sigurno biti izracunato i na exprStack-u, jer je taj cvor obidjen pre nego sto se obilazi tekuca visit metoda
		// cvora DesignatorStatement_dodela
		Designator dsg = DesignatorStatement_dodela.getDesignator();
		Obj objNode = dsg.obj;
		Code.store(objNode);
	}
	
	@Override
	public void visit(Factor Factor) {
		//Da proverim da li treba da se doda - ispred
		FactorMoguciMinus fmm = (FactorMoguciMinus)Factor.getFactorMoguciMinus();
		if(fmm instanceof FactorMoguciMinus_nijeMinus) {
			// Ne radi nista, nije bio minus
		}else {
			// Ovo ce da mi skine sa steka poslednju sacuvanu vrednost a to je upravo FactorZamena
			// i da ga negira
			Code.put(Code.neg);
		}
	}
	
	@Override
	public void visit(FactorZamena_new FactorZamena_new) {
		int opCodeNewArray = Code.newarray;
		Code.put(opCodeNewArray);
		Type tipNiza = FactorZamena_new.getArrayDecl().getType();
		if(tipNiza.struct.equals(Tab.charType) == false) {
			Integer b = 1;
			Code.put(b);
		}else {
			Integer b = 0;
			Code.put(b);
		}
	}
	
	@Override
	public void visit(DesignatorStatement_plusPlus DesignatorStatement_plusPlus) {
		Designator dsg = DesignatorStatement_plusPlus.getDesignator();
		Obj objNode = dsg.obj;
		if(proveraNaElem(objNode) == true) {
			int opCodeDupliraj = Code.dup2;
			Code.put(opCodeDupliraj);
		}
		Code.load(objNode);
		Code.loadConst(1);
		int opCodeAdd = Code.add;
		Code.put(opCodeAdd);
		Code.store(dsg.obj);
	}
	
	@Override
	public void visit(DesignatorStatement_minusMinus DesignatorStatement_minusMinus) {
		Designator dsg = DesignatorStatement_minusMinus.getDesignator();
		Obj objNode = dsg.obj;
		if(proveraNaElem(objNode) == true) {
			int opCodeDupliraj = Code.dup2;
			Code.put(opCodeDupliraj);
		}
		Code.load(objNode);
		Code.loadConst(1);
		int opCodeAdd = Code.sub;
		Code.put(opCodeAdd);
		Code.store(dsg.obj);
	}
	
	@Override
	public void visit(SingleStatement_returnStatement SingleStatement_returnStatement) {
		int opCodeExit = Code.exit;
		int opCodeReturn = Code.return_;
		Code.put(opCodeExit);
		Code.put(opCodeReturn);
	}
	
	@Override
	public void visit(SingleStatement_readStatement SingleStatement_readStatement) {
		Designator dsg = SingleStatement_readStatement.getDesignator();
		Obj objNode = dsg.obj;
		if(proveraNaChar(objNode) == false) {
			int opCodeRead = Code.read;
			Code.put(opCodeRead);
		}else {
			int opCode_bRead = Code.bread;
			Code.put(opCode_bRead);
		}
		Code.store(dsg.obj);
	}
	
	@Override
	public void visit(Label Label) {
		String imeLabele = Label.getImeLabele();
		Integer adresa = Code.pc;
		imenaLabele.add(imeLabele);
		adreseLabela.add(adresa);
		boolean trebaPrepravka = toRepairAdresses.containsKey(imeLabele);
		if(trebaPrepravka == false) {
			// Nista ne radi, nikome ne treba prepravka
		}else {
			while(!toRepairAdresses.get(imeLabele).isEmpty()) {
				Integer adresaPrepravkeTekuca = toRepairAdresses.get(imeLabele).remove(0);
				Code.fixup(adresaPrepravkeTekuca);
			}
		}
	}
	
	//Goto
	@Override
	public void visit(SingleStatement_gotoStatement SingleStatement_gotoStatement) {
		String imeSkoka = SingleStatement_gotoStatement.getLabelIzaGoTo().getMestoSkoka();
		if(imenaLabele.contains(imeSkoka) == false) {
			int nulaZaPrepravku = 0;
			Code.putJump(nulaZaPrepravku);
			// nakon sto sam ubacio jump, moj pc se nalazi na sledecem mestu:
			// JMP 0 0 PC, dakle treba da ga umanjim za 2, da bih stigao do pozicije prve nule, a odatle treba da se vrsi prepravka
			// nalik prepravkama na SS za skokove
			int novaVrednost = Code.pc - 2;
			boolean postojiNekoVec = toRepairAdresses.containsKey(imeSkoka);
			if(postojiNekoVec) {
				//report_info("Nije prvi skok", SingleStatement_gotoStatement);
				List<Integer> lista = toRepairAdresses.get(imeSkoka);
				lista.add(novaVrednost);
			}else {
				//report_info("Prvi skok", SingleStatement_gotoStatement);
				List<Integer> lista = new ArrayList<>();
				toRepairAdresses.put(imeSkoka, lista);
				lista.add(novaVrednost);
			}
		}else {
			// Posto sam u semantickoj analizi onemogucio da se labela sa istim imenom definise na vise od jednog mesta
			// Znam da ce indeks labele iz niza imenaLabela, odgovarati 1na 1 indeksu adrese te labele iz niza adreseLabela
			int pozicija = imenaLabele.indexOf(imeSkoka);
			int absoluteAdressJump = adreseLabela.get(pozicija);
			Code.putJump(absoluteAdressJump);
		}
		
	}
	
}
