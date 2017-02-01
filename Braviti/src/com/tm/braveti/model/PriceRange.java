package com.tm.braveti.model;

/**
 * 
 * <code>Class contains price range for various product categories.
 * <br>Possible values of price range {'High','Mid','Low'}</code>
 *
 */
public class PriceRange {

	private String range;

	/**
	 * Getter method to get price range
	 * 
	 * @return price range
	 */
	public String getRange() {
		return range;
	}

	/**
	 * Setter method for setting price range value
	 * 
	 * @param range
	 */
	public void setRange(String range) {
		this.range = range;
	}

	@Override
	public String toString() {
		return "PriceRange [range=" + range + "]";
	}

}
