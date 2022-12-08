// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class ProgDeclList_variable extends ProgDeclList {

    private ProgDeclList ProgDeclList;
    private VariableDeclList VariableDeclList;

    public ProgDeclList_variable (ProgDeclList ProgDeclList, VariableDeclList VariableDeclList) {
        this.ProgDeclList=ProgDeclList;
        if(ProgDeclList!=null) ProgDeclList.setParent(this);
        this.VariableDeclList=VariableDeclList;
        if(VariableDeclList!=null) VariableDeclList.setParent(this);
    }

    public ProgDeclList getProgDeclList() {
        return ProgDeclList;
    }

    public void setProgDeclList(ProgDeclList ProgDeclList) {
        this.ProgDeclList=ProgDeclList;
    }

    public VariableDeclList getVariableDeclList() {
        return VariableDeclList;
    }

    public void setVariableDeclList(VariableDeclList VariableDeclList) {
        this.VariableDeclList=VariableDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgDeclList!=null) ProgDeclList.accept(visitor);
        if(VariableDeclList!=null) VariableDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgDeclList!=null) ProgDeclList.traverseTopDown(visitor);
        if(VariableDeclList!=null) VariableDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgDeclList!=null) ProgDeclList.traverseBottomUp(visitor);
        if(VariableDeclList!=null) VariableDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgDeclList_variable(\n");

        if(ProgDeclList!=null)
            buffer.append(ProgDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VariableDeclList!=null)
            buffer.append(VariableDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgDeclList_variable]");
        return buffer.toString();
    }
}
