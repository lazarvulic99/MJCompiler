// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class VariableDeclList implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private VariableDeclOptions VariableDeclOptions;

    public VariableDeclList (Type Type, VariableDeclOptions VariableDeclOptions) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VariableDeclOptions=VariableDeclOptions;
        if(VariableDeclOptions!=null) VariableDeclOptions.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VariableDeclOptions getVariableDeclOptions() {
        return VariableDeclOptions;
    }

    public void setVariableDeclOptions(VariableDeclOptions VariableDeclOptions) {
        this.VariableDeclOptions=VariableDeclOptions;
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
        if(Type!=null) Type.accept(visitor);
        if(VariableDeclOptions!=null) VariableDeclOptions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VariableDeclOptions!=null) VariableDeclOptions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VariableDeclOptions!=null) VariableDeclOptions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableDeclList(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VariableDeclOptions!=null)
            buffer.append(VariableDeclOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableDeclList]");
        return buffer.toString();
    }
}
