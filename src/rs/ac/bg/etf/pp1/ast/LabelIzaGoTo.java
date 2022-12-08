// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class LabelIzaGoTo implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String mestoSkoka;

    public LabelIzaGoTo (String mestoSkoka) {
        this.mestoSkoka=mestoSkoka;
    }

    public String getMestoSkoka() {
        return mestoSkoka;
    }

    public void setMestoSkoka(String mestoSkoka) {
        this.mestoSkoka=mestoSkoka;
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
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LabelIzaGoTo(\n");

        buffer.append(" "+tab+mestoSkoka);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LabelIzaGoTo]");
        return buffer.toString();
    }
}
