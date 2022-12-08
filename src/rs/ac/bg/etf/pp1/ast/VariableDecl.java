// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class VariableDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private VariableOpcioneZagrade VariableOpcioneZagrade;

    public VariableDecl (String I1, VariableOpcioneZagrade VariableOpcioneZagrade) {
        this.I1=I1;
        this.VariableOpcioneZagrade=VariableOpcioneZagrade;
        if(VariableOpcioneZagrade!=null) VariableOpcioneZagrade.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public VariableOpcioneZagrade getVariableOpcioneZagrade() {
        return VariableOpcioneZagrade;
    }

    public void setVariableOpcioneZagrade(VariableOpcioneZagrade VariableOpcioneZagrade) {
        this.VariableOpcioneZagrade=VariableOpcioneZagrade;
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
        if(VariableOpcioneZagrade!=null) VariableOpcioneZagrade.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VariableOpcioneZagrade!=null) VariableOpcioneZagrade.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VariableOpcioneZagrade!=null) VariableOpcioneZagrade.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableDecl(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(VariableOpcioneZagrade!=null)
            buffer.append(VariableOpcioneZagrade.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableDecl]");
        return buffer.toString();
    }
}
