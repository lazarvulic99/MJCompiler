// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclOptions_multiple extends ConstDeclOptions {

    private ConstComma ConstComma;
    private ConstDeclOptions ConstDeclOptions;

    public ConstDeclOptions_multiple (ConstComma ConstComma, ConstDeclOptions ConstDeclOptions) {
        this.ConstComma=ConstComma;
        if(ConstComma!=null) ConstComma.setParent(this);
        this.ConstDeclOptions=ConstDeclOptions;
        if(ConstDeclOptions!=null) ConstDeclOptions.setParent(this);
    }

    public ConstComma getConstComma() {
        return ConstComma;
    }

    public void setConstComma(ConstComma ConstComma) {
        this.ConstComma=ConstComma;
    }

    public ConstDeclOptions getConstDeclOptions() {
        return ConstDeclOptions;
    }

    public void setConstDeclOptions(ConstDeclOptions ConstDeclOptions) {
        this.ConstDeclOptions=ConstDeclOptions;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstComma!=null) ConstComma.accept(visitor);
        if(ConstDeclOptions!=null) ConstDeclOptions.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstComma!=null) ConstComma.traverseTopDown(visitor);
        if(ConstDeclOptions!=null) ConstDeclOptions.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstComma!=null) ConstComma.traverseBottomUp(visitor);
        if(ConstDeclOptions!=null) ConstDeclOptions.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclOptions_multiple(\n");

        if(ConstComma!=null)
            buffer.append(ConstComma.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstDeclOptions!=null)
            buffer.append(ConstDeclOptions.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclOptions_multiple]");
        return buffer.toString();
    }
}
