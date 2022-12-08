// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class Statement_singleWithLabel extends Statement {

    private SingleStatementLabel SingleStatementLabel;
    private SingleStatement SingleStatement;

    public Statement_singleWithLabel (SingleStatementLabel SingleStatementLabel, SingleStatement SingleStatement) {
        this.SingleStatementLabel=SingleStatementLabel;
        if(SingleStatementLabel!=null) SingleStatementLabel.setParent(this);
        this.SingleStatement=SingleStatement;
        if(SingleStatement!=null) SingleStatement.setParent(this);
    }

    public SingleStatementLabel getSingleStatementLabel() {
        return SingleStatementLabel;
    }

    public void setSingleStatementLabel(SingleStatementLabel SingleStatementLabel) {
        this.SingleStatementLabel=SingleStatementLabel;
    }

    public SingleStatement getSingleStatement() {
        return SingleStatement;
    }

    public void setSingleStatement(SingleStatement SingleStatement) {
        this.SingleStatement=SingleStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(SingleStatementLabel!=null) SingleStatementLabel.accept(visitor);
        if(SingleStatement!=null) SingleStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(SingleStatementLabel!=null) SingleStatementLabel.traverseTopDown(visitor);
        if(SingleStatement!=null) SingleStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(SingleStatementLabel!=null) SingleStatementLabel.traverseBottomUp(visitor);
        if(SingleStatement!=null) SingleStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement_singleWithLabel(\n");

        if(SingleStatementLabel!=null)
            buffer.append(SingleStatementLabel.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(SingleStatement!=null)
            buffer.append(SingleStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement_singleWithLabel]");
        return buffer.toString();
    }
}
