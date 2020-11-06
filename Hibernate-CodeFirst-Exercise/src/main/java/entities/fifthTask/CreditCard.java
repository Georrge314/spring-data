package entities.fifthTask;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "credit_cards")
@DiscriminatorValue("CreditCard")
public class CreditCard extends BillingDetail {
    private String cardType;
    private int expirationMonth;
    private int expirationYear;

    protected CreditCard(int number, String owner) {
        super(number, owner);
    }

    public CreditCard(int number, String owner, String cardType, int expirationMonth, int expirationYear) {
        super(number, owner);
        this.cardType = cardType;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    @Column(name = "card_type", nullable = false)
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Column(name = "exipration_month", nullable = false)
    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year", nullable = false)
    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }
}
