package com.thelocalmarketplace.software.logic;

import com.jjjwelectronics.card.Card;
import com.thelocalmarketplace.software.AbstractLogicDependant;

/**
 * MembershipLogic class represents the logic for managing membership-related operations
 * within The Local Marketplace software. It extends the AbstractLogicDependant class.
 * This class includes functionality to handle membership numbers, account names,
 * and the creation of membership cards.
 *
 * @author Rhett Bramfield
 */

public class MembershipLogic extends AbstractLogicDependant {
	
	
	// Private member variables
	private String membershipNumber;
	private String accountName;
	private Card membershipCard;

	
	 /**
     * Constructor for MembershipLogic class.
     *
     * @param logic The instance of CentralStationLogic that this class depends on.
     * @throws NullPointerException If the provided logic is null.
     */
	public MembershipLogic(CentralStationLogic logic) throws NullPointerException {
		super(logic);
		// TODO Auto-generated constructor stub
	}
	
	/**
     * Gets the current membership number.
     *
     * @return The membership number.
     */
	public String getMembershipNumber() {
		return membershipNumber;
	}
	
	 /**
     * Sets the membership number after validating its format.
     *
     * @param cardNumber The membership number to set.
     */
	public void setMembershipNumber(String cardNumber) {
		if(isMembershipNumberValid(membershipNumber)) {
			this.membershipNumber = cardNumber;
		} else {
			System.out.println("Invalid membership number. Please enter your 6-digit number");
		}
		
	}
	 /**
     * Checks if the provided membership number is valid. Must be 6 digits long.
     *
     * @param membershipNumber The membership number to validate.
     * @return True if the membership number is valid, false otherwise.
     */
	public boolean isMembershipNumberValid(String membershipNumber) {
		if(membershipNumber != null && membershipNumber.matches("\\d{6}") ){
			return true;
		} else {
			return false;
		}
	}
	 /**
     * Gets the current account name.
     *
     * @return The account name.
     */
	public String getAccountName() {
		return accountName;
	}
	 /**
     * Sets the account name.
     *
     * @param accountName The account name to set.
     */
	public void setAccountame(String accountName) {
		this.accountName = accountName;
	}
	
	 /**
     * Creates a new membership card with the provided details.
     *
     * @param cardType      The type of the card. Type should be membership.
     * @param cardNumber    The card number.
     * @param cardholder    The cardholder's name.
     * @param cvv           The card's CVV. Null.
     * @param pin           The card's PIN. Null.
     * @param isTapEnabled  True if tap is enabled, false otherwise. False.
     * @param hasChip       True if the card has a chip, false otherwise. False.
     */
	public void createMembershipCard(String cardType, String cardNumber, String cardholder, String cvv, String pin,
			boolean isTapEnabled, boolean hasChip) {
		if (isMembershipNumberValid(membershipNumber) && accountName != null) {
			membershipCard = new Card(cardType, cardNumber, cardholder, cvv, pin, isTapEnabled, hasChip);
			this.membershipNumber = cardNumber;
			this.accountName = cardholder;
		} else {
			System.out.println("Invalid membership number or account name. Please set valid values before creating a card.");
		}
	}
	 /**
     * Gets the current membership card.
     *
     * @return The membership card.
     */
	public Card getMembershipCard() {
		return membershipCard;
	}
	
	

}
