// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class ConstantDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private Konstanta Konstanta;

    public ConstantDecl (String I1, Konstanta Konstanta) {
        this.I1=I1;
        this.Konstanta=Konstanta;
        if(Konstanta!=null) Konstanta.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public Konstanta getKonstanta() {
        return Konstanta;
    }

    public void setKonstanta(Konstanta Konstanta) {
        this.Konstanta=Konstanta;
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
        if(Konstanta!=null) Konstanta.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Konstanta!=null) Konstanta.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Konstanta!=null) Konstanta.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstantDecl(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(Konstanta!=null)
            buffer.append(Konstanta.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstantDecl]");
        return buffer.toString();
    }
}
