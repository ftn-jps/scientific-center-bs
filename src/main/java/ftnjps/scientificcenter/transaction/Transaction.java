package ftnjps.scientificcenter.transaction;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import ftnjps.scientificcenter.users.ApplicationUser;

@Entity
public class Transaction {

	@Id
	@GeneratedValue
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	@JsonIgnore
	private String successToken = UUID.randomUUID().toString();
	@JsonIgnore
	private String errorToken = UUID.randomUUID().toString();

	@Positive
	private double amount;

	@NotBlank
	private String merchantId;

	@Size(min = 6, max = 100)
	private String merchantPassword;

	private int merchantOrderId;

	@JsonProperty(access = Access.READ_ONLY)
	private long merchantOrderTimestamp;

	private String successUrl;
	private String failUrl;
	private String errorUrl;

	@NotNull
	@JsonIgnore
	@ManyToOne
	private ApplicationUser payer;
	@JsonIgnore
	boolean isFinalized = false;
	@JsonIgnore
	Long validUntilTimestamp;

	public Transaction() {}

	public Transaction(double amount,
			String merchantId,
			String merchantPassword,
			int merchantOrderId,
			ApplicationUser payer) {
		this.amount = amount;
		this.merchantId = merchantId;
		this.merchantPassword = merchantPassword;
		this.merchantOrderId = merchantOrderId;
		this.merchantOrderTimestamp = new Date().getTime();
		this.payer = payer;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSuccessToken() {
		return successToken;
	}

	public void setSuccessToken(String successToken) {
		this.successToken = successToken;
	}

	public String getErrorToken() {
		return errorToken;
	}

	public void setErrorToken(String errorToken) {
		this.errorToken = errorToken;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantPassword() {
		return merchantPassword;
	}
	public void setMerchantPassword(String merchantPassword) {
		this.merchantPassword = merchantPassword;
	}
	public int getMerchantOrderId() {
		return merchantOrderId;
	}
	public void setMerchantOrderId(int merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}
	public long getMerchantOrderTimestamp() {
		return merchantOrderTimestamp;
	}
	public void setMerchantOrderTimestamp(long merchantOrderTimestamp) {
		this.merchantOrderTimestamp = merchantOrderTimestamp;
	}
	public String getSuccessUrl() {
		return successUrl;
	}
	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}
	public String getFailUrl() {
		return failUrl;
	}
	public void setFailUrl(String failUrl) {
		this.failUrl = failUrl;
	}
	public String getErrorUrl() {
		return errorUrl;
	}
	public void setErrorUrl(String errorUrl) {
		this.errorUrl = errorUrl;
	}

	public ApplicationUser getPayer() {
		return payer;
	}

	public void setPayer(ApplicationUser payer) {
		this.payer = payer;
	}

	public boolean isFinalized() {
		return isFinalized;
	}

	public void setFinalized(boolean isFinalized) {
		this.isFinalized = isFinalized;
	}

	public Long getValidUntilTimestamp() {
		return validUntilTimestamp;
	}

	public void setValidUntilTimestamp(Long validUntilTimestamp) {
		this.validUntilTimestamp = validUntilTimestamp;
	}

}
