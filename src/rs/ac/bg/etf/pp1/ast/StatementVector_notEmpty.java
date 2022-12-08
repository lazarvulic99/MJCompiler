// generated with ast extension for cup
// version 0.8
// 22/7/2022 19:5:44


package rs.ac.bg.etf.pp1.ast;

public class StatementVector_notEmpty extends StatementVector {

    private StatementVector StatementVector;
    private Statement Statement;

    public StatementVector_notEmpty (StatementVector StatementVector, Statement Statement) {
        this.StatementVector=StatementVector;
        if(StatementVector!=null) StatementVector.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public StatementVector getStatementVector() {
        return StatementVector;
    }

    public void setStatementVector(StatementVector StatementVector) {
        this.StatementVector=StatementVector;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StatementVector!=null) StatementVector.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StatementVector!=null) StatementVector.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StatementVector!=null) StatementVector.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementVector_notEmpty(\n");

        if(StatementVector!=null)
            buffer.append(StatementVector.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementVector_notEmpty]");
        return buffer.toString();
    }
}
