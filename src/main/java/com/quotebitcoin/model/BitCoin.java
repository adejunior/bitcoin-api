package com.quotebitcoin.model;

import java.math.BigDecimal;
import java.util.Date;

public class BitCoin {
    
    private Date date;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal tid;
    private String type;
    
    public BitCoin() {}
    
    public BitCoin(Date date, BigDecimal price, BigDecimal amount, BigDecimal tid, String type) {
	super();
	this.date = date;
	this.price = price;
	this.amount = amount;
	this.tid = tid;
	this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTid() {
        return tid;
    }

    public void setTid(BigDecimal tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
        
    @Override
    public String toString() {
	return "{\"date\":" + date + ", \"price\":" + price + ", \"amount\":" + amount + ", \"tid\":" + tid + ", \"type\":" + "\"" + type + "\""
		+ "}";
    }

    @Override
    public boolean equals(Object obj) {
	if (getClass() != obj.getClass())
	    return false;
	BitCoin other = (BitCoin) obj;
	if (amount == null) {
	    if (other.amount != null)
		return false;
	} else if (!amount.equals(other.amount))
	    return false;
	if (date == null) {
	    if (other.date != null)
		return false;
	} else if (!date.equals(other.date))
	    return false;
	if (price == null) {
	    if (other.price != null)
		return false;
	} else if (!price.equals(other.price))
	    return false;
	if (tid == null) {
	    if (other.tid != null)
		return false;
	} else if (!tid.equals(other.tid))
	    return false;
	if (type == null) {
	    if (other.type != null)
		return false;
	} else if (!type.equals(other.type))
	    return false;
	return true;
    }   
    
    
    
}
