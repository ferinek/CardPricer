package com.kslodowicz.tools.mtg.dto;

public class CardDTO {
	private String category;
	private int amount;
	private String cardName;
	private String Expansion;
	private Double price;
	private String comment;
	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getExpansion() {
		return Expansion;
	}

	public void setExpansion(String expansion) {
		Expansion = expansion;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(amount);
		sb.append("x ");
		sb.append(cardName);
		sb.append(" (");
		sb.append(Expansion);
		if (getComment()!=null)
		{
			sb.append(", ");
			sb.append(getComment());
		}
		sb.append(") ");
		if (price != null && price != -1D) {
			sb.append(price);
			sb.append("zł/szt");
		}
		return sb.toString();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
