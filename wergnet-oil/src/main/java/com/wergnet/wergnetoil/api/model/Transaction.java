package com.wergnet.wergnetoil.api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    public Long id;

    private String description;
    private BigDecimal balanceCard;
    
    private LocalDate dateCredit;
    private LocalDate dateDebit;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    public Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    public Bank bank;
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }

    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
    
    public BigDecimal getBalanceCard() {
    	return balanceCard;
    }
    
    public void setBalanceCard(BigDecimal balanceCard) {
    	this.balanceCard = balanceCard;
    }
    
    public LocalDate getDateCredit() {
		return dateCredit;
	}

	public void setDateCredit(LocalDate dateCredit) {
		this.dateCredit = dateCredit;
	}

	public LocalDate getDateDebit() {
		return dateDebit;
	}

	public void setDateDebit(LocalDate dateDebit) {
		this.dateDebit = dateDebit;
	}

	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transaction other = (Transaction) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
