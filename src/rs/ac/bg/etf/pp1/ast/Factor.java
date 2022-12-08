// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class Factor implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private FactorMoguciMinus FactorMoguciMinus;
    private FactorZamena FactorZamena;

    public Factor (FactorMoguciMinus FactorMoguciMinus, FactorZamena FactorZamena) {
        this.FactorMoguciMinus=FactorMoguciMinus;
        if(FactorMoguciMinus!=null) FactorMoguciMinus.setParent(this);
        this.FactorZamena=FactorZamena;
        if(FactorZamena!=null) FactorZamena.setParent(this);
    }

    public FactorMoguciMinus getFactorMoguciMinus() {
        return FactorMoguciMinus;
    }

    public void setFactorMoguciMinus(FactorMoguciMinus FactorMoguciMinus) {
        this.FactorMoguciMinus=FactorMoguciMinus;
    }

    public FactorZamena getFactorZamena() {
        return FactorZamena;
    }

    public void setFactorZamena(FactorZamena FactorZamena) {
        this.FactorZamena=FactorZamena;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FactorMoguciMinus!=null) FactorMoguciMinus.accept(visitor);
        if(FactorZamena!=null) FactorZamena.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FactorMoguciMinus!=null) FactorMoguciMinus.traverseTopDown(visitor);
        if(FactorZamena!=null) FactorZamena.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FactorMoguciMinus!=null) FactorMoguciMinus.traverseBottomUp(visitor);
        if(FactorZamena!=null) FactorZamena.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor(\n");

        if(FactorMoguciMinus!=null)
            buffer.append(FactorMoguciMinus.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorZamena!=null)
            buffer.append(FactorZamena.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor]");
        return buffer.toString();
    }
}
