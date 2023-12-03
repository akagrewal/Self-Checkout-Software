package com.thelocalmarketplace.software.test.logic;

/**
 * Test cases for MembershipLogic
 *
 * Authors:
 * - Jaimie Marchuk - 30112841
 * - Wyatt Deichert - 30174611
 * - Jane Magai - 30180119
 * - Enzo Mutiso - 30182555
 * - Mauricio Murillo - 30180713
 * - Ahmed Ibrahim Mohamed Seifeldin Hassan - 30174024
 * - Aryaman Sandhu - 30017164
 * - Nikki Kim - 30189188
 * - Jayden Ma - 30184996
 * - Braden Beler - 30084941
 * - Danish Sharma - 30172600
 * - Angelina Rochon - 30087177
 * - Amira Wishah - 30182579
 * - Walija Ihsan - 30172565
 * - Hannah Pohl - 30173027
 * - Akashdeep Grewal - 30179657
 * - Rhett Bramfield - 30170520
 * - Arthur Huan - 30197354
 * - Jaden Myers - 30152504
 * - Jincheng Li - 30172907
 * - Anandita Mahika - 30097559
 *
 * Author of the tested class:
 * - Rhett Bramfield
 */

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.thelocalmarketplace.hardware.SelfCheckoutStationBronze;
import com.thelocalmarketplace.software.logic.CentralStationLogic;
import com.thelocalmarketplace.software.logic.MembershipLogic;

import powerutility.PowerGrid;

public class MembershipLogicTest {

    private MembershipLogic membershipLogic;
    SelfCheckoutStationBronze station;
    CentralStationLogic centralStationLogic;

    @Before
    public void setUp() throws Exception {
        station = new SelfCheckoutStationBronze();
        station.plugIn(PowerGrid.instance());
        station.turnOn();

        centralStationLogic = new CentralStationLogic(station);
        membershipLogic = new MembershipLogic(centralStationLogic);
    }

    /**
     * Test creating a membership card with valid data.
     * This test ensures that the membership card is created successfully,
     * and the membership number and account name are set correctly.
     */
    @Test
    public void testCreateMembershipCard() {
        String cardType = "membership";
        String cardNumber = "123456";
        String cardholder = "John Doe";
        String cvv = null;
        String pin = null;
        boolean isTapEnabled = false;
        boolean hasChip = false;

        membershipLogic.createMembershipCard(cardType, cardNumber, cardholder, cvv, pin, isTapEnabled, hasChip);

        assertNotNull(membershipLogic.getMembershipCard());
        assertEquals(cardNumber, membershipLogic.getMembershipNumber());
        assertEquals(cardholder, membershipLogic.getAccountName());
    }

    /**
     * Test creating a membership card with invalid membership number.
     * This test ensures that the membership card creation fails when
     * an invalid membership number is provided, and the membership number
     * validation is correctly checked.
     */
    @Test
    public void testCreateMembershipCardInvalidData() {
        String cardType = "membership";
        String cardNumber = "1234";
        String cardholder = "John Doe";
        String cvv = null;
        String pin = null;
        boolean isTapEnabled = false;
        boolean hasChip = false;

        membershipLogic.createMembershipCard(cardType, cardNumber, cardholder, cvv, pin, isTapEnabled, hasChip);

        assertNull(membershipLogic.getMembershipCard());
        assertFalse(membershipLogic.isMembershipNumberValid(cardNumber));
    }

    /**
     * Test setting and getting a valid membership number.
     * This test ensures that the membership number is set correctly
     * and can be retrieved using the getMembershipNumber method.
     */
    @Test
    public void testSetAndGetMembershipNumber() {
        membershipLogic.setMembershipNumber("123456");
        assertEquals("123456", membershipLogic.getMembershipNumber());
    }

    /**
     * Test getting the account name.
     * This test ensures that the account name can be retrieved
     * using the getAccountName method, and setting it works correctly.
     */
    @Test
    public void testGetAccountName() {
        assertNull(membershipLogic.getAccountName());
        membershipLogic.setAccountame("John Doe");
        assertEquals("John Doe", membershipLogic.getAccountName());
    }

    /**
     * Test setting an invalid membership number.
     * This test ensures that setting an invalid membership number
     * results in a null membership number, indicating failure.
     */
    @Test
    public void testSetInvalidMembershipNumber() {
        membershipLogic.setMembershipNumber("abc");
        assertNull(membershipLogic.getMembershipNumber());
    }
}
