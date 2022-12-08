// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class VariableDeclOptions_multiple extends VariableDeclOptions {

    private VariableComma VariableComma;
    private VariableDeclOptions VariableDeclOptions;

    public VariableDeclOptions_multiple (VariableComma VariableComma, VariableDeclOptions VariableDeclOptions) {
        this.VariableComma=VariableComma;
        if(VariableComma!=null) VariableComma.setParent(this);
        this.VariableDeclOptions=VariableDeclOptions;
        if(VariableDeclOptions!=null) VariableDeclOptions.setParent(this);
    }

    public VariableComma getVariableComma() {
        return VariableComma;
    }

    public void setVariableComma(VariableComma VariableComma) {
        this.VariableComma=VariableComma;
    }

    public VariableDeclOptions getVariableDeclOptions() {
        return VariableDeclOptions;
    }

    public void setVariableDeclOptions(VariableDeclOptions VariableDeclOptions) {
        this.VariableDeclOptions=VariableDeclOptions;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VariableComma!=null) VariableComma.accept(visitor);
        if(VariableDeclOptions!=null) VariableDeclOptions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VariableComma!=null) VariableComma.traverseTopDown(visitor);
        if(VariableDeclOptions!=null) VariableDeclOptions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VariableComma!=null) VariableComma.traverseBottomUp(visitor);
        if(VariableDeclOptions!=null) VariableDeclOptions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableDeclOptions_multiple(\n");

        if(VariableComma!=null)
            buffer.append(VariableComma.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VariableDeclOptions!=null)
            buffer.append(VariableDeclOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableDeclOptions_multiple]");
        return buffer.toString();
    }
}
