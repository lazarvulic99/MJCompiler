// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class Designator_onlyIdent extends Designator {

    private String imeDesignatora;

    public Designator_onlyIdent (String imeDesignatora) {
        this.imeDesignatora=imeDesignatora;
    }

    public String getImeDesignatora() {
        return imeDesignatora;
    }

    public void setImeDesignatora(String imeDesignatora) {
        this.imeDesignatora=imeDesignatora;
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
        buffer.append("Designator_onlyIdent(\n");

        buffer.append(" "+tab+imeDesignatora);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designator_onlyIdent]");
        return buffer.toString();
    }
}
