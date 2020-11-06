package entities.fifthTask;

import entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_accounts")
@DiscriminatorValue("BankAccount")
public class BankAccount extends BillingDetail {
    private String bankName;
    private String swiftCode;

    protected BankAccount(int number, String owner) {
        super(number, owner);
    }

    public BankAccount(int number, String owner, String bankName, String swiftCode) {
        super(number, owner);
        this.bankName = bankName;
        this.swiftCode = swiftCode;
    }

    @Column(name = "bank_name", nullable = false)
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Column(name = "swift_code", nullable = false)
    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}
